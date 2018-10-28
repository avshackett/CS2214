//
//  Instruction.cpp
//  CompOrgProject
//
//  Created by Alec Shackett on 2/27/18.
//  Copyright © 2018 Alec Shackett. All rights reserved.
//

#include "Instruction.hpp"

static std::map<std::string, int> opcodes;


Instruction::
Instruction( std::string instr, int pos, Label* labPos )  : instr( instr ), pos( pos ), labPos( labPos ) {
    split();
    generateOpcodes();
}

//-----------------------------------------------------------------------------
//Utility function to make a string lowercased.
std::string Instruction::
makeLower( std::string str ) {
    for( int x = 0; x < str.size(); ++x ) str[x] = tolower( str[x] );
    return str;
}

//-----------------------------------------------------------------------------
//Utility function to convert string register to int number
int Instruction::
convertR( std::string str ) {
    int f = str.find( 'r' );
    str.erase( f, f + 1 );
    return std::stoi( str );
}

//-----------------------------------------------------------------------------
int Instruction::
findDis( std::string str ) {
    int dis = 0;
    for( int x = 0; x < sizeof( labPos ); ++x ){
        if( str == labPos[x].getLabel() ) {
            dis = ( labPos[x].getPos() * 4 ) - ( ( pos * 4 ) + 4 );
        }
    }
    return dis;
}

//-----------------------------------------------------------------------------
//Utility function to convert string register with displacement to int number
void Instruction::
convertRDis( std::string str ) {
    if( str[0] != 'r' ) {
        char* cStr = (char*) malloc( sizeof( str ) + 1 );
        std::strcpy( cStr, str.c_str() );
        
        std::string save[2];
        int x = 0;
        char* cur;
        cur = std::strtok( cStr, "#()" );
        while( cur != NULL ) {
            save[x++] = cur;
            cur = strtok( NULL, "#()" );
        }
        
        if( save[1][0] == '-' ) {
            save[1].erase( save[1].begin() );
            imm -= std::stoi( save[0] );
        }
        else if( save[1][0] == '+' ) {
            save[1].erase( save[1].begin() );
            imm += std::stoi( save[0] );
        }
        else{
            imm += std::stoi( save[0] );
        }
        
        rs1 = convertR( save[1] );
        delete[] cStr;
    }
    else {
        rs1 = convertR( str );
    }
}

//-----------------------------------------------------------------------------
//Utility function to convert string immediate to int number
int Instruction::
convertIMM( std::string str ) {
    int f = str.find( '#' );
    if( f != -1) str.erase( f, f + 1 );
    return std::stoi( str );
}

//-----------------------------------------------------------------------------
//Tokenizes the instruction
void Instruction::
split() {
    char* str = (char*) malloc( sizeof( instr ) + 1 );
    std::strcpy( str, instr.c_str() );
    char* cur;
    
    cur = strtok( str, " ," );
    while( cur != NULL ) {
        parts.push_back( makeLower( cur ) );
        cur = strtok( NULL, " ," );
    }
    
    //Look for labels.
    if( parts[0].find( ':' ) != -1 ) {
        label = strtok( (char*) parts[0].c_str(), ":" );
        labPos[pos] = Label( label, pos );
        parts.erase( parts.begin() );
    }
}

//-----------------------------------------------------------------------------
//
void Instruction::
process() {
    //Get the opcode.
    opcode = parts[0];
    parts.erase( parts.begin() );
    op = opcodes.at( opcode );
    
    //Count the number of registers (if any).
    int count = 0;
    for( int x = 0; x < parts.size(); ++x ) {
        if( parts[x].find( 'r' ) != -1 && isnumber( parts[x][1] ) ) ++count;
    }
    nReg = count;
    
    //Decide what type of instruction this is.
    if( nReg == 0 ) type = jType;
    else if( nReg == 1 || nReg == 2 ) type = iType;
    else if( nReg == 3 ) type = rType;

    //Extract op, rs1, rs2, rd, imm, func, offset from the remaining parts
    //for each type respectively
    if( type == jType ) {
        offset = findDis( parts[0] );
    }
    else if( type == iType ) {
        if( nReg == 1 ) {
            
            //Load High Immediate:
            //LHI
            if(op == 15){
                rs2 = convertR( parts[0] );
                imm = convertIMM( parts[1] );
            }
            //Branches:
            //BEQZ and BNEZ
            else if(op == 4 || op == 5){
                rs1 = convertR( parts[0] );
                imm = findDis( parts[1] );
            }
            //Jump to reg:
            //JR and JALR
            else if(op == 18 || op == 19){
                rs1 = convertR( parts[0] );
            }
            
        }
        else if( nReg == 2 ) {
            //Loads:
            //LB, LH, LW, LBU, LHU (34 is nothing)
            if(op >= 32 && op <= 37){
                rs2 = convertR( parts[0] );
                convertRDis( parts[1] );
            }
            //Stores:
            //SB, SH, SW (42 is nothing)
            else if(op >= 40 && op <= 43){
                convertRDis( parts[0] );
                rs2 = convertR( parts[1] );
            }
            //Math immediates:
            //ADDI, ADDUI, SUBI, SUBUI, ANDI, ORI, XORI
            //SEQI, SNEI, SLTI, SGTI, SLEI, SGEI
            //SEQUI, SNEUI, SLTUI, SGTUI, SLEUI, SGEUI, SLLI, SRLI, SRAI
            else if( (op >= 8 && op <= 14) || (op >= 24 && op <= 29) || (op >= 48 && op <= 56) ){
                rs2 = convertR( parts[0] );
                rs1 = convertR( parts[1] );
                imm = convertIMM( parts[2] );
            }
        }
    }
    else if( type == rType ) {
        rd = convertR( parts[0] );
        rs1 = convertR( parts[1] );
        rs2 = convertR( parts[2] );
        func = op;
        op = 0;
    }
}



//-----------------------------------------------------------------------------
//Prints out the Instruction class.
std::ostream& Instruction::
print( std::ostream& out ) {
    out <<"Instruction: " <<instr <<std::endl;
    if( label != "" ) out <<"Label is " <<label <<std::endl;
    out <<std::setw( 9 ) <<"Type: " <<std::setw( 5 ) <<types[type] <<std::endl
    <<std::setw( 8 ) <<"Op:" <<std::setw( 5 ) <<op <<std::endl;
    
    if( type == jType ) {
        out <<std::setw( 8 ) <<"OFFSET:" <<std::setw( 5 ) <<offset <<std::endl;
    }
    if( type == iType ) {
        out <<std::setw( 8 ) <<"RS1:" <<std::setw( 5 ) <<rs1 <<std::endl
        <<std::setw( 8 ) <<"RS2:" <<std::setw( 5 ) <<rs2 <<std::endl
        <<std::setw( 8 ) <<"IMM:" <<std::setw( 5 ) <<imm <<std::endl;
    }
    if( type == rType ) {
        out <<std::setw( 8 ) <<"RS1:" <<std::setw( 5 ) <<rs1 <<std::endl
        <<std::setw( 8 ) <<"RS2:" <<std::setw( 5 ) <<rs2 <<std::endl
        <<std::setw( 8 ) <<"RD:" <<std::setw( 5 ) <<rd <<std::endl
        <<std::setw( 8 ) <<"FUNC:" <<std::setw( 5 ) <<func <<std::endl;
    }
    out <<"---------------------------------------------------------------\n";
    return out;
}

//-----------------------------------------------------------------------------
//Populates the map with opcodes.
void Instruction::
generateOpcodes() {
    opcodes["j"] = 2; opcodes["jal"] = 3; opcodes["beqz"] = 4;
    opcodes["bnez"] = 5; opcodes["addi"] = 8; opcodes["addui"] = 9;
    opcodes["subi"] = 10; opcodes["subui"] = 11; opcodes["andi"] = 12;
    opcodes["ori"] = 13; opcodes["xori"] = 14; opcodes["lhi"] = 15;
    opcodes["jr"] = 18; opcodes["jalr"] = 19; opcodes["seqi"] = 24;
    opcodes["snei"] = 25; opcodes["slti"] = 26; opcodes["sgti"] = 27;
    opcodes["slei"] = 28; opcodes["sgei"] = 29; opcodes["lb"] = 32;
    opcodes["lh"] = 33; opcodes["lw"] = 35; opcodes["lbu"] = 36;
    opcodes["lhu"] = 37; opcodes["sb"] = 40; opcodes["sh"] = 41;
    opcodes["sw"] = 43; opcodes["sequi"] = 48; opcodes["sneui"] = 49;
    opcodes["sltui"] = 50; opcodes["sgtui"] = 51; opcodes["sleui"] = 52;
    opcodes["sgeui"] = 53; opcodes["slli"] = 54; opcodes["srli"] = 55;
    opcodes["srai"] = 56; opcodes["sll"] = 4; opcodes["srl"] = 6;
    opcodes["sra"] = 7; opcodes["sltu"] = 18; opcodes["sgtu"] = 19;
    opcodes["sleu"] = 20; opcodes["sgeu"] = 21; opcodes["add"] = 32;
    opcodes["addu"] = 33; opcodes["sub"] = 34; opcodes["subu"] = 35;
    opcodes["and"] = 36; opcodes["or"] = 37; opcodes["xor"] = 38;
    opcodes["seq"] = 40; opcodes["sne"] = 41; opcodes["slt"] = 42;
    opcodes["sgt"] = 43; opcodes["sle"] = 44; opcodes["sge"] = 45;
}
