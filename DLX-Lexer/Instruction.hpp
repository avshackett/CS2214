//
//  Instruction.hpp
//  CompOrgProject
//
//  Created by Alec Shackett on 2/27/18.
//  Copyright © 2018 Alec Shackett. All rights reserved.
//

#ifndef Instruction_hpp
#define Instruction_hpp

#include <iostream>
#include <vector>
#include <sstream>
#include <iomanip>
#include <map>

#include "Enums.hpp"
#include "Label.hpp"

extern std::string types[3];

class Instruction {
private:
    std::string instr;
    std::string opcode;
    std::vector<std::string> parts;
    std::string label = "";
    int pos;
    Label* labPos = nullptr;
    int nReg = 0;
    Type type;
    int op = 0;
    int rs1 = 0;
    int rs2 = 0;
    int rd = 0;
    int imm = 0;
    int func = 0;
    int offset = 0;
    //Functions
    std::string makeLower( std::string str );
    int convertR( std::string str );
    int convertIMM( std::string str );
    void convertRDis( std::string str );
    void findRS1( std::string str );
    void findRS2( std::string str );
    int findDis( std::string str );
    void split();
    void generateOpcodes();
public:
    Instruction( std::string instr, int pos, Label* labPos );
    ~Instruction() {  }
    void process();
    std::ostream& print( std::ostream& out );
    std::string getInstr() { return instr; }
    int getPos() { return pos; }
    std::string getLabel() { return label; }
    Type getType() { return type; }
    int getOP() { return op; }
    int getRS1() { return rs1; }
    int getRS2() { return rs2; }
    int getRD() { return rd; }
    int getIMM() { return imm; }
    int getFUNC() { return func; }
    int getOFFSET() { return offset; }
    int getRNUM() { return nReg; }
};

inline std::ostream& operator <<(std::ostream& out, Instruction& i){return i.print(out);}

#endif /* Instruction_hpp */

