package dlxsimulationgui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

class LoadH extends StateControl{
    protected mLoadH m;
    public LoadH(AnchorPane aP, int instr, TextField instrField, Button next, Polygon ALU, Rectangle... args){
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
        m = new mLoadH(dlx, "lh r7,#6(r10)");
        instrField.setText("lh r7,#6(r10)");
    }
    
    EventHandler<ActionEvent> step6 = (ActionEvent s6) -> {
        next.setDisable(true);
        next.setOnAction(null);
        m.step6();
        clear();
        on(C, DMX, RegFile);
        setPos(d3, posC);
        show(d3);
        seqT.getChildren().addAll(ptCToRegFile);
        seqT.play();
    };
    
    EventHandler<ActionEvent> adjustStep5a = (ActionEvent as5a) -> {
        setPos(d3, posALU);
    };
    
    EventHandler<ActionEvent> step5 = (ActionEvent s5) -> {
        next.setDisable(true);
        m.step5();
        clear();
        on(MDR, ALU, C);
        setPos(d3, posMDRoeS2);
        show(d3);
        ptMDRoeS2.setOnFinished(adjustStep5a);
        seqT.getChildren().addAll(ptMDRoeS2, ptALUToC);
        seqT.play();
        next.setOnAction(step6);
    };
    
    EventHandler<ActionEvent> adjustStep4a = (ActionEvent as4a) -> {
        m.step4b();
        setPos(d3, posMem);
        changeNode(t3, rec3, m.getLoad());
    };
    
    EventHandler<ActionEvent> step4 = (ActionEvent s4) -> {
        next.setDisable(true);
        m.step4a();
        clear();
        on(MAR, PCMARMux, Mem, MDR, MDRMux);
        setPos(d3, posMAR);
        show(d3);
        ptMARToMem.setOnFinished(adjustStep4a);
        seqT.getChildren().addAll(ptMARToMem, ptMemToMDR);
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

    //lh r7,#6(r10)  85470006
class mLoadH extends Math{
    mLoadH(DLXControlViewController dlx, String instruction){
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
        instr = Integer.toUnsignedLong(0x85470006);
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
        PCMARselect = 1;
        MDRload = 1;
        MemRead = 1;
        MemOP = 1;
        load = rand.nextInt(20);
        dlx.on(dlx.csPCMARselect, dlx.csMDRload, dlx.csMemRead, dlx.csMemOP);
        update();
    }
    
    protected void step4b(){
        MDR = load;
        update();
    }
    
    protected void step5(){
        flagReset();
        MDRoeS2 = 1;
        S2op = 1;
        ALUop = 3;
        Cload = 1;
        C = MDR;
        dlx.on(dlx.csMDRoeS2, dlx.csS2op, dlx.csALUop, dlx.csCload);
        update();
    }
    
    protected void step6(){
        flagReset();
        RegFile[rs2] = C;
        REGload = 1;
        REGselect = 1;
        dlx.on(dlx.csREGload, dlx.csREGselect);
        update();
    }
}