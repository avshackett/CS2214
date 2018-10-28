package dlxsimulationgui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

class BEQZ extends StateControl{
    protected mBEQZ m;
    public BEQZ(AnchorPane aP, int instr, TextField instrField, Button next, Polygon ALU, Rectangle... args){
        super(aP, instr, instrField, next, ALU, args);
        int x = 0;
        this.next.setOnAction(step1);
        this.PC = args[x++];
        this.PCMARMux = args[x++];
        this.Mem = args[x++];
        this.IR = args[x++];
        RegFile = args[x++];
        AMux = args[x++];
        BMux = args[x++];
        A = args[x++];
        B = args[x++];
        C = args[x++];
        DMX = args[x++];
        m = new mBEQZ(dlx, "beqz r8, test2");
        instrField.setText("beqz r8, test2");
    }
    
    EventHandler<ActionEvent> adjustStep3Zflag1 = (ActionEvent s3z1a) -> {
        m.update();
        hide(d2);
        setPos(d3, posALU);
        changeNode(t3, rec3, m.getPostALU());
    };
    
    EventHandler<ActionEvent> step3Zflag1 = (ActionEvent s3z1) -> {
        next.setDisable(true);
        next.setOnAction(null);
        m.step3z1();
        clear();
        setPos(d2, posIRoeS2);
        setPos(d3, posPCoeS1);
        changeNode(t2, rec2, m.getImm());
        changeNode(t3, rec3, m.getPC());
        on(PC, IR, ALU);
        show(d2, d3);
        parT.getChildren().addAll(ptPCoeS1, ptIRoeS2);
        seqT.getChildren().addAll(parT, ptALUToPC);
        seqT.play();
    };
    
    EventHandler<ActionEvent> step3 = (ActionEvent s3) -> {
        next.setDisable(true);
        m.step3();
        clear();
        setPos(d1, posA);
        on(A, ALU);
        show(d1);
        seqT.getChildren().addAll(ptAoe);
        seqT.play();
        if(m.getZflag() == 1) next.setOnAction(step3Zflag1);
        else next.setOnAction(null);
    };
    
    protected EventHandler<ActionEvent> adjustStep1 = (ActionEvent aS1) -> {
        m.step1b();
        setPos(d3, posMem);
        changeNode(t3, rec3, m.getInstr());
    };
    protected EventHandler<ActionEvent> adjustStep2a = (ActionEvent aS2a) -> {
        m.step2b();
        setPos(d3, posALU);
        changeNode(t3, rec3, m.getPC());
    };
    protected EventHandler<ActionEvent> adjustStep2b = (ActionEvent aS2b) -> {
        m.step2c();
        off(ALU, PC);
        on(A, B, AMux, BMux, RegFile);
        changeNode(t1, rec1, m.getRs1());
        changeNode(t2, rec2, m.getRs2());
        show(d1, d2);
        hide(d3);
        setPos(d1, posRegA);
        setPos(d2, posRegB);
    };
    
    protected EventHandler<ActionEvent> step2 = (ActionEvent s2) -> {
        next.setDisable(true);
        m.step2a();
        clear();
        on(PC, ALU);
        setPos(d3, posPCoeS1);
        changeNode(t3, rec3, m.getPC());
        show(d3);
        ptPCoeS1.setOnFinished(adjustStep2a);
        ptALUToPC.setOnFinished(adjustStep2b);
        parT.getChildren().addAll(ptRegFileOutA, ptRegFileOutB);
        seqT.getChildren().addAll(ptPCoeS1, ptALUToPC, parT);
        seqT.play();
        this.next.setOnAction(step3);
    };
    
    protected EventHandler<ActionEvent> step1 = (ActionEvent s1) -> {
        next.setDisable(true);
        m.step1a();
        clear();
        on(PC, PCMARMux, Mem, IR);
        setPos(d3, posPC);
        changeNode(t3, rec3, m.getPC());
        show(d3);
        seqT.getChildren().addAll(ptPCToMem, ptMemToIR);
        ptPCToMem.setOnFinished(adjustStep1);
        seqT.play();
        next.setOnAction(step2);
    };
}

    //beqz r8, test2 1100fff8
class mBEQZ extends Math{
    mBEQZ(DLXControlViewController dlx, String instruction){
        super(dlx, instruction);
        for(int x = 0; x < RegFile.length; ++x) RegFile[x] = rand.nextInt(20);
        flagReset();
        update();
    }
    
    protected void step1a(){
        flagReset();
        PC = 0;
        PCMARselect = 0;
        IRload = 1;
        MemRead = 1;
        MemOP = 0;
        dlx.on(dlx.csPCMARselect, dlx.csIRload, dlx.csMemRead, dlx.csMemOP);
        update();
        
    }
    protected void step1b(){
        instr = Integer.toUnsignedLong(0x1100fff8);
        IR = instr;
        update();
    }
    
    protected void step2a(){
        flagReset();
        iType();
        PCoeS1 = 1;
        S2op = 7;
        ALUop = 0;
        PCload = 1;
        Aload = 1;
        Bload = 1;
        dlx.on(dlx.csPCoeS1, dlx.csS2op, dlx.csALUop, dlx.csPCload, dlx.csAload, dlx.csBload);
        update();
    }
    
    protected void step2b(){
        PC += 4;
        update();
    }
    
    protected void step2c(){
        A = RegFile[rs1];
        B = RegFile[rs2];
        update();
    }
    
    protected void step3(){
        flagReset();
        Aoe = 1;
        ALUop = 2;
        S2op = 0;
        if(A == 0) Zflag = 1;
        else Zflag = 0;
        dlx.on(dlx.csAoe, dlx.csALUop, dlx.csS2op);
        update();
    }
    
    protected void step3z1(){
        flagReset();
        PCoeS1 = 1;
        IRoeS2 = 1;
        S2op = 3;
        ALUop = 0;
        dlx.on(dlx.csPCoeS1, dlx.csIRoeS2, dlx.csS2op, dlx.csALUop);
        update();
    }
}