package dlxsimulationgui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

class StoreB extends StateControl{
    protected mStoreB m;
    public StoreB(AnchorPane aP, int instr, TextField instrField, Button next, Polygon ALU, Rectangle... args){
        super(aP, instr, instrField, next, ALU, args);
        int x = 0;
        next.setOnAction(step1);
        PC = args[x++];
        PCMARMux = args[x++];
        Mem = args[x++];
        IR = args[x++];
        RegFile = args[x++];
        AMux = args[x++];
        BMux = args[x++];
        A = args[x++];
        B = args[x++];
        C = args[x++];
        DMX = args[x++];
        MAR = args[x++];
        MDR = args[x++];
        MDRMux = args[x++];
        m = new mStoreB(dlx, "sb #7(r5),r4");
        instrField.setText("sb #7(r5),r4");
    }
    
    EventHandler<ActionEvent> step5 = (ActionEvent s5) -> {
        next.setDisable(true);
        next.setOnAction(null);
        m.step5();
        clear();
        on(MDR, MDRMux, MAR, PCMARMux, Mem);
        setPos(d1, posMAR);
        setPos(d2, posMDR);
        ptMARToMem.setNode(d1);
        ptMDRToMem.setNode(d2);
        show(d1, d2);
        parT.getChildren().addAll(ptMARToMem, ptMDRToMem);
        seqT.getChildren().addAll(parT);
        seqT.play();
    };
    
    EventHandler<ActionEvent> adjustStep4a = (ActionEvent as4a) -> {
        ptALUToMDR.setNode(d2);
        setPos(d2, posALU);
    };
    
    EventHandler<ActionEvent> step4 = (ActionEvent s4) -> {
        next.setDisable(true);
        m.step4a();
        clear();
        on(B, ALU, MDR, MDRMux);
        setPos(d2, posB);
        changeNode(t2, rec2, m.getRs2());
        show(d2);
        ptBoe.setOnFinished(adjustStep4a);
        seqT.getChildren().addAll(ptBoe, ptALUToMDR);
        seqT.play();
        next.setOnAction(step5);
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
        setPos(d2, posIRoeS2);
        on(A, IR, ALU, MAR);
        show(d1, d2);
        changeNode(t2, rec2, m.getImm());
        parT.getChildren().addAll(ptAoe, ptIRoeS2);
        parT.setOnFinished(adjustStep3a);
        seqT.getChildren().addAll(parT, ptALUToMAR);
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

    //sb #7(r5),r4  a0a40007
class mStoreB extends Math{
    mStoreB(DLXControlViewController dlx, String instruction){
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
        MemOP = 1;
        dlx.on(dlx.csPCMARselect, dlx.csIRload, dlx.csMemRead, dlx.csMemOP);
        update();
        
    }
    protected void step1b(){
        instr = Integer.toUnsignedLong(0xa0a40007);
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
        IRoeS2 = 1;
        MARload = 1;
        ALUop = 0;
        S2op = 3;
        postALU = A + imm;
        MAR = postALU;
        dlx.on(dlx.csAoe, dlx.csIRoeS2, dlx.csMARload, dlx.csALUop, dlx.csS2op);
        update();
    }
    
    protected void step4a(){
        flagReset();
        Boe = 1;
        ALUop = 3;
        S2op = 0;
        MDRload = 1;
        dlx.on(dlx.csBoe, dlx.csMDRload, dlx.csALUop, dlx.csS2op);
        update();
    }
    
    protected void step4b(){
        MDR = RegFile[rs2];
        update();
    }
    
    protected void step5(){
        flagReset();
        PCMARselect = 1;
        MemWrite = 1;
        MemOP = 2;
        dlx.on(dlx.csPCMARselect, dlx.csMemWrite, dlx.csMemOP);
        update();
    }
}