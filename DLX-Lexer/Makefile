# Compiler used:
CXX = g++

# Compiler flags:
CXXFLAGS = -std=c++11 -Wall

# Objects:
OBJ = main.o Controller.o Instruction.o Label.o Enums.o

# Libraries to include
# LIBS =

# This is how to get an object file by compiling a source file.
# Note that the second line MUST start with a tab character. Spaces won't work.
sweep: $(OBJ)
	$(CXX) $(OBJ)
	rm -f $(OBJ)

# Executing "make clean" will remove all the object files and the executable
# Note that the second line MUST start with a tab character. Spaces won't work.
clean:
	rm -f $(OBJ) encoder

# This is the dependency declaration list. In each declaration, the object file on
#	the left depends on all the source files that follow it. If a file on the right
#	is not up-to-date, the object on the left will be recompiled.
# This list was generated and added to the makefile by executing the command
#	g++ -MM *.cpp >>Makefile

Enums.o: Opcode.hpp
Label.o:
Opcode.o:
Instruction.o:
Controller.o: Instruction.hpp Instruction.cpp Opcode.hpp
main.o: main.cpp Controller.hpp Controller.cpp