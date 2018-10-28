//
//  main.cpp
//  CompOrgProject
//
//  Created by Alec Shackett on 2/27/18.
//  Copyright © 2018 Alec Shackett. All rights reserved.
//

#include "Controller.hpp"


int main( int argc, const char * argv[] ) {
    if( argc != 2 ){
        std::cerr <<"Usage: " <<argv[0] <<" file.txt" <<std::endl;
        exit( 0 );
    }
    
    Controller c( argv[1] );
    c.run();
}
