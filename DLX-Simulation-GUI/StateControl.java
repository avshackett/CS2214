package dlxsimulationgui;

import javafx.animation.PathTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextBuilder;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.animation.ParallelTransition;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StateControl {
    //Constructor
    public StateControl(AnchorPane aP, int instr, TextField instrField, Button next, Polygon ALU, Rectangle... args){
        int x = 0;
        this.aP = aP;
        this.next = next;
        this.ALU = ALU;
        this.instrField = instrField;
        seqT.setOnFinished(cleanUp);
        aP.getChildren().addAll(d1,d2,d3);
        rec1.setStroke(Color.BLACK);
        rec2.setStroke(Color.BLACK);
        rec3.setStroke(Color.BLACK);
        this.next.setDisable(false);
        d1.setVisible(false);
        d2.setVisible(false);
        d3.setVisible(false);
        try{
            loader.load();
            dlx = loader.getController();
            root = loader.getRoot();
            scene = new Scene(root, 450, 700);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } 
        catch (IOException e){
            System.err.println("FXML file not loaded.");
        }
    }
    //Control Signals
    protected Stage stage = new Stage();
    protected Scene scene;
    protected Parent root;
    //Loaders
    protected FXMLLoader loader = new FXMLLoader(getClass().getResource("DLXControlView.fxml"));
    protected DLXControlViewController dlx;
    //Misc
    protected final AnchorPane aP;
    private final MoveTo m = new MoveTo(0,0);
    private final LineTo ALUToBus = new LineTo(200,0);
    protected Button next;
    protected TextField instrField;
    protected int instr;
    //Actors
    protected Polygon ALU;
    protected Rectangle
        A, B, AMux, BMux, C, DMX, RegFile, Mem, IR, PC, MAR, MDR, PCMARMux, MDRMux; 
    protected Rectangle
        rec1 = new Rectangle(30,20, Color.WHITE),
        rec2 = new Rectangle(30,20, Color.WHITE),
        rec3 = new Rectangle(30,20, Color.WHITE);
    protected Text
        t1 = new Text("t1"),
        t2 = new Text("t2"),
        t3 = new Text("t3");
    protected StackPane
        d1 = new StackPane(rec1, t1),
        d2 = new StackPane(rec2, t2),
        d3 = new StackPane(rec3, t3);
    //Positions
    protected final Positions
        posA = new Positions(230,125),
        posB = new Positions(230,185),
        posIRoeS1 = new Positions(323,275),
        posIRoeS2 = new Positions(325,285),
        posPCoeS1 = new Positions(325,360),
        posMDRoeS2 = new Positions(325,520),
        posALU = new Positions(420,30),
        posMDR = new Positions(450,525),
        posPC = new Positions(450,365),
        posMAR = new Positions(450,435),
        posMem = new Positions(450,610),
        posRegA = new Positions(350,125),
        posRegB = new Positions(350,185),
        posC = new Positions(550,155);
    //Paths
    protected final Path
        //To ALU
        AToALU = new Path(m, new LineTo(-80,0), new LineTo(-80,-81), new LineTo(150,-81)),
        BToALU = new Path(m, new LineTo(-50,0), new LineTo(-50,-171), new LineTo(150,-171)),
        IRoeS1ToALU = new Path(m, new LineTo(-175,0), new LineTo(-175,-231), new LineTo(55,-231)),
        IRoeS2ToALU = new Path(m, new LineTo(-145,0), new LineTo(-145,-271), new LineTo(55,-271)),
        PCoeS1ToALU = new Path(m, new LineTo(-175,0), new LineTo(-175,-316), new LineTo(55,-316)),
        MDRoeS2ToALU = new Path(m, new LineTo(-145,0), new LineTo(-145,-506), new LineTo(55,-506)),
        //ALU out
        ALUToC = new Path(m, ALUToBus, new LineTo(200,125), new LineTo(160,125)),
        ALUToPC = new Path(m, ALUToBus, new LineTo(200,325), new LineTo(30,325)),
        ALUToMAR = new Path(m, ALUToBus, new LineTo(200,415), new LineTo(30,415)),
        ALUToMDR = new Path(m, ALUToBus, new LineTo(200,500), new LineTo(110,500), new MoveTo(80,485), new LineTo(30,485)),
        //To mem
        MDRToMem = new Path(m, new LineTo(20,0), new LineTo(20,30), new LineTo(230,30), new LineTo(230,85), new LineTo(0,85)),
        PCToMem = new Path(m, new LineTo(30,0), new LineTo(30,25), new LineTo(50,25), new MoveTo(80,35), new LineTo(200,35), new LineTo(200,295), new LineTo(0,295)),
        MARToMem = new Path(m, new LineTo(30,0), new LineTo(30,-25), new LineTo(50,-25), new MoveTo(80,-35), new LineTo(200,-35), new LineTo(200,225), new LineTo(0,225)),
        //From mem
        MemToIR = new Path(m, new LineTo(230,0), new LineTo(230,-330), new LineTo(0,-330)),
        MemToMDR = new Path(m, new LineTo(230,0), new LineTo(230,-100), new LineTo(80,-100), new MoveTo(50, -95), new LineTo(0,-95)),
        //Misc
        RegOut = new Path(m, new LineTo(-30,0), new MoveTo(-60,0), new LineTo(-90,0)),
        CToRegFile = new Path(m, new LineTo(-40,0), new MoveTo(-70,0), new LineTo(-100,0));
    //PathTransitions
    protected PathTransition
        //To ALU
        ptRegFileOutA = new PathTransition(Duration.seconds(3), RegOut, d1),
        ptRegFileOutB = new PathTransition(Duration.seconds(3), RegOut, d2),
        ptAoe = new PathTransition(Duration.seconds(3), AToALU, d1),
        ptBoe = new PathTransition(Duration.seconds(3), BToALU, d2),
        ptIRoeS1 = new PathTransition(Duration.seconds(3), IRoeS1ToALU, d2),
        ptIRoeS2 = new PathTransition(Duration.seconds(3), IRoeS2ToALU, d2),
        ptPCoeS1 = new PathTransition(Duration.seconds(3), PCoeS1ToALU, d3),
        ptMDRoeS2 = new PathTransition(Duration.seconds(3), MDRoeS2ToALU, d3),
        //ALU to Reg
        ptALUToC = new PathTransition(Duration.seconds(3), ALUToC, d3),
        ptCToRegFile = new PathTransition(Duration.seconds(3), CToRegFile, d3),
        //ALU to others
        ptALUToPC = new PathTransition(Duration.seconds(3), ALUToPC, d3),
        ptALUToMAR = new PathTransition(Duration.seconds(3), ALUToMAR, d3),
        ptALUToMDR = new PathTransition(Duration.seconds(3), ALUToMDR, d3),
        //To mem
        ptPCToMem = new PathTransition(Duration.seconds(3), PCToMem, d3),
        ptMARToMem = new PathTransition(Duration.seconds(3), MARToMem, d3),
        ptMDRToMem = new PathTransition(Duration.seconds(3), MDRToMem, d3),
        //From mem
        ptMemToIR = new PathTransition(Duration.seconds(3), MemToIR, d3),
        ptMemToMDR = new PathTransition(Duration.seconds(3), MemToMDR, d3);
    //ParallelTransitions
    protected ParallelTransition
        parT = new ParallelTransition();
    //SequentialTransitions
    protected SequentialTransition
        seqT = new SequentialTransition();
    //Control functions
    protected void on(Shape... args){
        for(Shape arg : args) try{ arg.setFill(Color.RED); } catch (NullPointerException np){}
    }
    protected void off(Shape... args){
        for(Shape arg : args)  try{ arg.setFill(Color.GHOSTWHITE); } catch (NullPointerException np){}
    }
    protected void setPos(StackPane sp, Positions p){
        sp.setLayoutX(p.getX());
        sp.setLayoutY(p.getY());
    }
    protected void show(StackPane... args){
        for(StackPane arg : args) arg.setVisible(true);
    }
    protected void hide(StackPane... args){
        for(StackPane arg : args) arg.setVisible(false);
    }
    public void clear(){
        off(A, B, C, RegFile, AMux, BMux, DMX, PC, ALU, IR, MAR, MDR, Mem, PCMARMux, MDRMux);
        hide(d1, d2, d3);
        parT.getChildren().clear();
        seqT.stop(); seqT.getChildren().clear();
    }
    protected void hideStage(){
        stage.hide();
    }
    protected void changeNode(Text t, Rectangle rec, int i){
        t.setText(Integer.toString(i));
        rec.setWidth(t.getLayoutBounds().getWidth() + 5);
    }
    protected void changeNode(Text t, Rectangle rec, long l){
        t.setText(Long.toString(l));
        rec.setWidth(t.getLayoutBounds().getWidth() + 5);
    }
    protected EventHandler<ActionEvent> cleanUp = (ActionEvent cU) -> {
        clear(); next.setDisable(false);
    };
    protected EventHandler<ActionEvent> play = (ActionEvent pl) -> {
        seqT.play();
    };
    protected EventHandler<ActionEvent> pause = (ActionEvent p) -> {
        seqT.pause();
    };
}