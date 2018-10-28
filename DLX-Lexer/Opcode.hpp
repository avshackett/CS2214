//
//  Opcode.hpp
//  CompOrgProject
//
//  Created by Alec Shackett on 2/27/18.
//  Copyright © 2018 Alec Shackett. All rights reserved.
//

#ifndef Opcode_hpp
#define Opcode_hpp

#include <iostream>

class Opcode {
private:
    std::string opcode;
    int id;
public:
    Opcode( std::string op, int num ) : opcode( op ), id( num ) { }
    ~Opcode() { }
    std::string getOpcode() { return opcode; }
    int getID() { return id; }
};

#endif /* Opcode_hpp */
