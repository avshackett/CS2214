//
//  Controller.cpp
//  CompOrgProject
//
//  Created by Alec Shackett on 2/27/18.
//  Copyright © 2018 Alec Shackett. All rights reserved.
//

#include "Controller.hpp"

//-----------------------------------------------------------------------------
//Open and check input file stream for reading.
//Open and check output file stream for outputting.
Controller::
Controller( std::string inFile ) : inStr( inFile ), outStr( OUTF ) {
    if( !inStr.is_open() ) {
        std::cerr <<inFile <<" failed to open for input." <<std::endl;
        exit( 0 );
    }
    
    if( !outStr.is_open() ) {
        std::cerr <<OUTF <<" failed to open for output." <<std::endl;
        exit( 0 );
    }
}

//-----------------------------------------------------------------------------
//Runs the encoding process.
void Controller::
run() {
    readInstructions();
    process();
    writeInstructions();
}

//-----------------------------------------------------------------------------
//Utility function to make a string lowercased.
std::string Controller::
makeLower( std::string str ) {
    for( int x = 0; x < str.size(); ++x ) str[x] = tolower( str[x] );
    return str;
}

//-----------------------------------------------------------------------------
//Reads in the instructions to encode.
void Controller::
readInstructions() {
    std::vector<std::string> temp;
    int pos = 0;
    for(;;) {
        std::string line;
        getline( inStr, line );
        if( inStr.eof() ) break;
        temp.push_back( makeLower( line ) );
        pos++;
    }
    
    labPos = new Label[pos];
    for( int x = 0; x < temp.size(); ++x ) {
        instructions.push_back( Instruction( temp.at(x), x, labPos ) );
    }
    
    inStr.close();
}

//-----------------------------------------------------------------------------
//Runs the encoding of instructions.
void Controller::
process() {
    for( int x = 0; x < instructions.size(); ++x ) {
        instructions[x].process();
        std::cout <<instructions[x] <<std::endl;
    }
    
    for( int x = 0; x < instructions.size(); ++x ) {
        int encoded = 0;
        int op = instructions[x].getOP();
        int rs1 = instructions[x].getRS1();
        int rs2 = instructions[x].getRS2();
        int rd = instructions[x].getRD();
        int imm = instructions[x].getIMM();
        int func = instructions[x].getFUNC();
        int offset = instructions[x].getOFFSET();
        
        //Do bitwise operations to encode the instruction.
        if( instructions[x].getType() == jType ) {
            encoded = offset;
            if( offset < 0) encoded = encoded ^ FLIPMASK26;
            encoded |= op << 26;
        }
        else if( instructions[x].getType() == iType ) {
            if( instructions[x].getRNUM() == 1 ) {
                encoded = imm;
                if( imm < 0 ) encoded = encoded ^ FLIPMASK16;
                encoded |= rs2 << 16;
                encoded |= rs1 << 21;
                encoded |= op << 26;
            }
            else if( instructions[x].getRNUM() == 2 ) {
                encoded = imm;
                if( imm < 0 ) encoded = encoded ^ FLIPMASK16;
                encoded |= rs2 << 16;
                encoded |= rs1 << 21;
                encoded |= op << 26;
            }
        }
        else if( instructions[x].getType() == rType ) {
            encoded = func;
            encoded |= rd << 11;
            encoded |= rs2 << 16;
            encoded |= rs1 << 21;
            encoded |= 0 << 26;
        }
        encodedInstructions.push_back( encoded );
    }
}

//-----------------------------------------------------------------------------
//Writes encoded instructions in hexadecimal to an output file.
void Controller::
writeInstructions() {
    for( int x = 0; x < encodedInstructions.size(); ++x ) {
        std::cout <<std::hex <<std::setw(8) <<std::setfill('0') <<encodedInstructions[x] <<std::dec <<std::endl;
        outStr <<std::hex <<std::setw(8) <<std::setfill('0') <<encodedInstructions[x] <<std::dec <<std::endl;
    }
    outStr.close();
}
