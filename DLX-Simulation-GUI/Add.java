package dlxsimulationgui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

class Add extends StateControl{
    protected mAdd m;
    public Add(AnchorPane aP, int instr, TextField instrField, Button next, Polygon ALU, Rectangle... args){
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
        m = new mAdd(dlx, "add r3,r7,r1");
        instrField.setText("add r3,r7,r1");
    }
    
    EventHandler<ActionEvent> step4 = (ActionEvent s4) -> {
        next.setDisable(true);
        next.setOnAction(null);
        m.step4();
        clear();
        on(C, DMX, RegFile);
        setPos(d3, posC);
        show(d3);
        seqT.getChildren().addAll(ptCToRegFile);
        seqT.play();
    };
    
    EventHandler<ActionEvent> adjustStep3a = (ActionEvent s3) -> {
        m.update();
        hide(d1, d2);
        setPos(d3, posALU);
        changeNode(t3, rec3, m.getPostALU());
        show(d3);
    };
    
    EventHandler<ActionEvent> step3 = (ActionEvent s3) -> {
        next.setDisable(true);
        m.step3();
        clear();
        setPos(d1, posA);
        setPos(d2, posB);
        on(A, B, ALU, C);
        show(d1, d2);
        parT.getChildren().addAll(ptAoe, ptBoe);
        parT.setOnFinished(adjustStep3a);
        seqT.getChildren().addAll(parT, ptALUToC);
        seqT.play();
        next.setOnAction(step4);
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

    //add r3,r7,r1 00e11820 0000 0000 1110 0001 0001 1000 0010 0000 
class mAdd extends Math{
    mAdd(DLXControlViewController dlx, String instruction){
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
        instr = Integer.toUnsignedLong(0x00e11820);
        IR = instr;
        update();
    }
    
    protected void step2a(){
        flagReset();
        rType();
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
        Boe = 1;
        Cload = 1;
        ALUop = 0;
        S2op = 0;
        postALU = A + B;
        C = postALU;
        dlx.on(dlx.csAoe, dlx.csBoe, dlx.csCload, dlx.csALUop, dlx.csS2op);
        update();
    }
    
    protected void step4(){
        flagReset();
        RegFile[rd] = C;
        REGload = 1;
        REGselect = 0;
        dlx.on(dlx.csREGload, dlx.csREGselect);
        update();
    }
}