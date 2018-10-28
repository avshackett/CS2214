//
//  Controller.hpp
//  CompOrgProject
//
//  Created by Alec Shackett on 2/27/18.
//  Copyright © 2018 Alec Shackett. All rights reserved.
//

#ifndef Controller_hpp
#define Controller_hpp

#define OUTF "output.txt"
#define FLIPMASK16 0xFFFF0000
#define FLIPMASK26 0xFC000000

#include <iostream>
#include <fstream>
#include <vector>

#include "Opcode.hpp"
#include "Instruction.hpp"

class Controller {
private:
    //Data members
    std::ifstream inStr;
    std::ofstream outStr;
    std::vector<Instruction> instructions;
    Label* labPos = nullptr;
    std::vector<int> encodedInstructions;
    //Functions
    std::string makeLower( std::string str );
public:
    Controller( std::string inFile );
    ~Controller() { delete[] labPos; }
    void run();
    void readInstructions();
    void process();
    void writeInstructions();
};

#endif /* Controller_hpp */
