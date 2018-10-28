//
//  Label.cpp
//  CompOrgProject
//
//  Created by Alec Shackett on 2/27/18.
//  Copyright © 2018 Alec Shackett. All rights reserved.
//

#include "Label.hpp"

std::ostream& Label::print( std::ostream& out ) {
    out <<label <<" " <<pos;
    return out;
}
