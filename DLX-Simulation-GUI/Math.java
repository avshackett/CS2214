package dlxsimulationgui;

import java.util.Random;

public class Math {
    Math(DLXControlViewController dlx, String instruction){
        this.dlx = dlx;
        this.instruction = instruction;
        for(int x = 0; x < 32; ++x) RegFile[x] = 0;
    }

    protected DLXControlViewController dlx;
    protected final int IMMMASK = 0x0000FFFF;
    protected final int RDMASK = 0x0000F800;
    protected final int RS1MASK = 0x03E00000;
    protected final int RS2MASK = 0x001F0000;
    protected final int OPCODEMASK = 0xFC000000;
    protected final int FUNCMASK = 0x000001FF;
    protected final int OFFSETMASK = 0x03FFFFFF;
    protected final int IMMFLIPMASK = 0x00008000;
    protected final int INTMASK = 0x0000003F;
        
    protected long instr;
    private String instruction;
    //RegFile
    protected Random rand = new Random();
    protected int[]
        RegFile = new int[32];
        //R0, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19, R20, R21,
        //R22, R23, R24, R25, R26, R27, R28, R29, R30, R31;
    //Other Registers
    protected int
        MAR = 0, MDR = 0, PC = 0, A = 0, B = 0, C = 0;
    protected long IR = 0;
    //Control Signals
    protected int
        S2op, Zflag, ALUop, Cload, REGload, Aload, Aoe, Bload, Boe,
        REGselect, IRload, IRoeS1, IRoeS2, opcode, opcodeALU, Reset,
        PCload, PCoeS1, PCMARselect, MARload, MemRead, MDRload, MDRoeS2,
        MemWrite, MemOP, MemWait;
    protected int
        rs1 = 0, rs2 = 0, rd = 0, instrOpcode = 0, postALU = 0, imm = 0, offset = 0, load = 0;
    
    protected void update(){
        dlx.update(IR, RegFile, S2op, Zflag, ALUop, Cload, REGload, Aload, Aoe, Bload, Boe,
        REGselect, IRload, IRoeS1, IRoeS2, opcode, opcodeALU, Reset,
        PCload, PCoeS1, PCMARselect, MARload, MemRead, MDRload, MDRoeS2,
        MemWrite, MemOP, MemWait, MAR, MDR, PC, A, B, C);
    }
    protected int getRs1(){
        return this.RegFile[rs1];
    }
    protected int getRs2(){
        return this.RegFile[rs2];
    }
    protected int getRd(){
        return this.rd;
    }
    protected long getInstr(){
        return this.instr;
    }
    protected int getOpcode(){
        return this.instrOpcode;
    }
    protected int getPostALU(){
        return this.postALU;
    }
    protected int getPC(){
        return this.PC;
    }
    protected int getImm(){
        return this.imm;
    }
    protected int getLoad(){
        return this.load;
    }
    protected int getZflag(){
        return this.Zflag;
    }
    protected void flagReset(){
        S2op = -1; Zflag = 0; ALUop = -1; Cload = 0; REGload = 0; Aload = 0; Aoe = 0; Bload = 0; Boe = 0;
        REGselect = -1; IRload = 0; IRoeS1 = 0; IRoeS2 = 0; opcode = -1; opcodeALU = -1; Reset = 0;
        PCload = 0; PCoeS1 = 0; PCMARselect = -1; MARload = 0; MemRead = 0; MDRload = 0; MDRoeS2 = 0;
        MemWrite = 0; MemOP = -1; MemWait = 0;
        dlx.allOff();
    }
    protected void rType(){
        rs1 = (int)(instr & RS1MASK) >> 21;
        rs2 = (int)(instr & RS2MASK) >> 16;
        rd = (int)(instr & RDMASK) >> 11;
        opcode = (int)(instr & FUNCMASK);
    }
    protected void iType(){
        opcode = (int)(instr & OPCODEMASK) >> 26;
        if(opcode < 0) opcode = -((opcode ^ INTMASK) + 1);
        rs1 = (int)(instr & RS1MASK) >> 21;
        rs2 = (int)(instr & RS2MASK) >> 16;
        if(instruction.contains("-")){
            imm = (int)(instr & IMMMASK);
            imm = -((imm ^ IMMMASK) + 1);
        }
        else imm = (int)(instr & IMMMASK);
        if((imm & 0x8000) == imm) imm = -((imm ^ IMMFLIPMASK) + 1 );
    }
    protected void jType(){
        opcode = (int)(instr & OPCODEMASK) >> 26;
        offset = (int)(instr & OFFSETMASK);
    }
}