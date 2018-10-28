package dlxsimulationgui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DLXView implements Initializable {
    @FXML
    AnchorPane aP;
    @FXML
    Rectangle DMX, RegFile, Mem, IR, PC, MAR, MDR, AMux, BMux, A, B, C, PCMARMux, MDRMux;
    @FXML
    Polygon ALU;
    @FXML
    Button next, quit;
    @FXML
    TextField instrDisplay;
    
    private int
        addInstr, addiInstr, subInstr, subiInstr, loadwInstr, loadhInstr, loadbInstr, storewInstr, 
        storehInstr, storebInstr,andInstr, orInstr, oriInstr, sllInstr, srlInstr, jumpInstr, beqzInstr, bnezInstr;
    
    private Add add;
    private Addi addi;
    private Sub sub;
    private Subi subi;
    private LoadW loadw;
    private LoadH loadh;
    private LoadB loadb;
    private StoreW storew;
    private StoreH storeh;
    private StoreB storeb;
    private BEQZ beqz;
    private BNEZ bnez;
    private Jump jump;
    private And and;
    private Or or;
    private Ori ori;
    private SLL sll;
    private SRL srl;
    
    public void clearAll(){
        try{ add.clear(); add.hideStage(); add = null; } catch (NullPointerException np) {  }
        try{ addi.clear(); addi.hideStage(); addi = null; } catch (NullPointerException np) {  }
        try{ sub.clear(); sub.hideStage(); sub = null; } catch (NullPointerException np) {  }
        try{ subi.clear(); subi.hideStage(); subi = null; } catch (NullPointerException np) {  }
        try{ loadw.clear(); loadw.hideStage(); loadw = null; } catch (NullPointerException np) {  }
        try{ loadh.clear(); loadh.hideStage(); loadh = null; } catch (NullPointerException np) {  }
        try{ loadb.clear(); loadb.hideStage(); loadb = null; } catch (NullPointerException np) {  }
        try{ storew.clear(); storew.hideStage(); storew = null; } catch (NullPointerException np) {  }
        try{ storeh.clear(); storeh.hideStage(); storeh = null; } catch (NullPointerException np) {  }
        try{ storeb.clear(); storeb.hideStage(); storeb = null; } catch (NullPointerException np) {  }
        try{ beqz.clear(); beqz.hideStage(); beqz = null; } catch (NullPointerException np) {  }
        try{ bnez.clear(); bnez.hideStage(); bnez = null; } catch (NullPointerException np) {  }
        try{ jump.clear(); jump.hideStage(); jump = null; } catch (NullPointerException np) {  }
        try{ and.clear(); and.hideStage(); and = null; } catch (NullPointerException np) {  }
        try{ or.clear(); or.hideStage(); or = null; } catch (NullPointerException np) {  }
        try{ ori.clear(); ori.hideStage(); ori = null; } catch (NullPointerException np) {  }
        try{ sll.clear(); sll.hideStage(); sll = null; } catch (NullPointerException np) {  }
        try{ srl.clear(); srl.hideStage(); srl = null; } catch (NullPointerException np) {  }
    }
    
    public void add(){ 
        clearAll();
        add = new Add(aP, addInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX);
    }
    
    public void addi(){
        clearAll();
        addi = new Addi(aP, addiInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX);
    }
    
    public void sub(){
        clearAll();
        sub = new Sub(aP, subInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX);
    }
    
    public void subi(){
        clearAll();
        subi = new Subi(aP, subiInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX);
    }
    
    public void loadw(){
        clearAll();
        loadw = new LoadW(aP, loadwInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void loadh(){
        clearAll();
        loadh = new LoadH(aP, loadhInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void loadb(){
        clearAll();
        loadb = new LoadB(aP, loadbInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void storew(){
        clearAll();
        storew = new StoreW(aP, storewInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void storeh(){
        clearAll();
        storeh = new StoreH(aP, storehInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void storeb(){
        clearAll();
        storeb = new StoreB(aP, storebInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void and(){
        clearAll();
        and = new And(aP, andInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX);
    }
    
    public void or(){
        clearAll();
        or = new Or(aP, orInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX);
    }
    
    public void ori(){
        clearAll();
        ori = new Ori(aP, oriInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX);
    }
    
    public void sll(){
        clearAll();
        sll = new SLL(aP, sllInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void srl(){
        clearAll();
        srl = new SRL(aP, srlInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void jump(){
        clearAll();
        jump = new Jump(aP, jumpInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void beqz(){
        clearAll();
        beqz = new BEQZ(aP, beqzInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void bnez(){
        clearAll();
        bnez = new BNEZ(aP, bnezInstr, instrDisplay, next, ALU, PC, PCMARMux, Mem, IR, RegFile, AMux, BMux, A, B, C, DMX, MAR, MDR, MDRMux);
    }
    
    public void quit(){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}