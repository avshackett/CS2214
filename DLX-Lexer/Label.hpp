//
//  Label.hpp
//  CompOrgProject
//
//  Created by Alec Shackett on 2/27/18.
//  Copyright © 2018 Alec Shackett. All rights reserved.
//

#ifndef Label_hpp
#define Label_hpp

#include <iostream>

class Label {
private:
    std::string label = "";
    int pos = -1;
public:
    Label() = default;
    Label( std::string label, int pos ) : label( label ), pos( pos ) { }
    ~Label() { }
    std::string getLabel() { return label; }
    int getPos() { return pos; }
    std::ostream& print( std::ostream& out );
};

inline std::ostream& operator <<( std::ostream& out, Label& l ) {return l.print( out );}

#endif /* Label_hpp */
