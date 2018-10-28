package dlxsimulationgui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DLXControlViewController implements Initializable {
    @FXML
    protected Text csS2op, csZflag, csALUop, csCload, csREGload, csAload, csAoe, csBload, csBoe,
            csREGselect, csIRload, csIRoeS1, csIRoeS2, csopcode, csopcodeALU, csReset,
            csPCload, csPCoeS1, csPCMARselect, csMARload, csMemRead, csMDRload, csMDRoeS2,
            csMemWrite, csMemOP, csMemWait;
    @FXML
    protected TextField tfS2op, tfZflag, tfALUop, tfCload, tfREGload, tfAload, tfAoe, tfBload, tfBoe,
            tfREGselect, tfIRload, tfIRoeS1, tfIRoeS2, tfopcode, tfopcodeALU, tfReset,
            tfPCload, tfPCoeS1, tfPCMARselect, tfMARload, tfMemRead, tfMDRload, tfMDRoeS2,
            tfMemWrite, tfMemOP, tfMemWait, tfR0, tfR1, tfR2, tfR3, tfR4, tfR5, tfR6, tfR7, tfR8,
            tfR9, tfR10, tfR11, tfR12, tfR13, tfR14, tfR15, tfR16, tfR17, tfR18, tfR19, tfR20,
            tfR21, tfR22, tfR23, tfR24, tfR25, tfR26, tfR27, tfR28, tfR29, tfR30, tfR31, tfIR,
            tfPC, tfMAR, tfMDR, tfA, tfB, tfC;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    protected void update(long IR, int[] RegFile, int... args){
        tfIR.setText(Long.toString(IR));
        int x = 0;
        tfR0.setText(Integer.toString(RegFile[x++]));
        tfR1.setText(Integer.toString(RegFile[x++])); tfR2.setText(Integer.toString(RegFile[x++])); 
        tfR3.setText(Integer.toString(RegFile[x++])); tfR4.setText(Integer.toString(RegFile[x++])); 
        tfR5.setText(Integer.toString(RegFile[x++])); tfR6.setText(Integer.toString(RegFile[x++])); 
        tfR7.setText(Integer.toString(RegFile[x++])); tfR8.setText(Integer.toString(RegFile[x++])); 
        tfR9.setText(Integer.toString(RegFile[x++])); tfR10.setText(Integer.toString(RegFile[x++]));
        tfR11.setText(Integer.toString(RegFile[x++])); tfR12.setText(Integer.toString(RegFile[x++])); 
        tfR13.setText(Integer.toString(RegFile[x++])); tfR14.setText(Integer.toString(RegFile[x++])); 
        tfR15.setText(Integer.toString(RegFile[x++])); tfR16.setText(Integer.toString(RegFile[x++])); 
        tfR17.setText(Integer.toString(RegFile[x++])); tfR18.setText(Integer.toString(RegFile[x++])); 
        tfR19.setText(Integer.toString(RegFile[x++])); tfR20.setText(Integer.toString(RegFile[x++])); 
        tfR21.setText(Integer.toString(RegFile[x++])); tfR22.setText(Integer.toString(RegFile[x++])); 
        tfR23.setText(Integer.toString(RegFile[x++])); tfR24.setText(Integer.toString(RegFile[x++])); 
        tfR25.setText(Integer.toString(RegFile[x++])); tfR26.setText(Integer.toString(RegFile[x++])); 
        tfR27.setText(Integer.toString(RegFile[x++])); tfR28.setText(Integer.toString(RegFile[x++])); 
        tfR29.setText(Integer.toString(RegFile[x++])); tfR30.setText(Integer.toString(RegFile[x++])); 
        tfR31.setText(Integer.toString(RegFile[x++]));
        x = 0;
        tfS2op.setText(Integer.toString(args[x++]));
        tfZflag.setText(Integer.toString(args[x++])); 
        tfALUop.setText(Integer.toString(args[x++])); 
        tfCload.setText(Integer.toString(args[x++])); 
        tfREGload.setText(Integer.toString(args[x++])); 
        tfAload.setText(Integer.toString(args[x++])); 
        tfAoe.setText(Integer.toString(args[x++])); 
        tfBload.setText(Integer.toString(args[x++])); 
        tfBoe.setText(Integer.toString(args[x++]));
        tfREGselect.setText(Integer.toString(args[x++])); 
        tfIRload.setText(Integer.toString(args[x++])); 
        tfIRoeS1.setText(Integer.toString(args[x++])); 
        tfIRoeS2.setText(Integer.toString(args[x++])); 
        tfopcode.setText(Integer.toString(args[x++])); 
        tfopcodeALU.setText(Integer.toString(args[x++])); 
        tfReset.setText(Integer.toString(args[x++]));
        tfPCload.setText(Integer.toString(args[x++])); 
        tfPCoeS1.setText(Integer.toString(args[x++])); 
        tfPCMARselect.setText(Integer.toString(args[x++])); 
        tfMARload.setText(Integer.toString(args[x++])); 
        tfMemRead.setText(Integer.toString(args[x++])); 
        tfMDRload.setText(Integer.toString(args[x++])); 
        tfMDRoeS2.setText(Integer.toString(args[x++]));
        tfMemWrite.setText(Integer.toString(args[x++])); 
        tfMemOP.setText(Integer.toString(args[x++])); 
        tfMemWait.setText(Integer.toString(args[x++])); 
        tfMAR.setText(Integer.toString(args[x++]));
        tfMDR.setText(Integer.toString(args[x++]));
        //tfIR.setText(Integer.toString(args[x++]));
        tfPC.setText(Integer.toString(args[x++]));
        tfA.setText(Integer.toString(args[x++]));
        tfB.setText(Integer.toString(args[x++]));
        tfC.setText(Integer.toString(args[x++]));
    }
    protected void signalsOff(){
        tfS2op.setText("-1");
        tfZflag.setText("0"); 
        tfALUop.setText("-1");
        tfCload.setText("0"); 
        tfREGload.setText("0"); 
        tfAload.setText("0"); 
        tfAoe.setText("0"); 
        tfBload.setText("0");  
        tfBoe.setText("0"); 
        tfREGselect.setText("-1");
        tfIRload.setText("0"); 
        tfIRoeS1.setText("0"); 
        tfIRoeS2.setText("0"); 
        tfopcode.setText("-1"); 
        tfopcodeALU.setText("-1");
        tfReset.setText("0"); 
        tfPCload.setText("0"); 
        tfPCoeS1.setText("0"); 
        tfPCMARselect.setText("-1");
        tfMARload.setText("0"); 
        tfMemRead.setText("0"); 
        tfMDRload.setText("0"); 
        tfMDRoeS2.setText("0"); 
        tfMemWrite.setText("0"); 
        tfMemOP.setText("-1");
        tfMemWait.setText("0");  
    }
    
    public void allOff(){
        csS2op.setFill(Color.BLACK); csZflag.setFill(Color.BLACK); 
        csALUop.setFill(Color.BLACK); csCload.setFill(Color.BLACK);
        csREGload.setFill(Color.BLACK); csAload.setFill(Color.BLACK);
        csAoe.setFill(Color.BLACK); csBload.setFill(Color.BLACK);
        csBoe.setFill(Color.BLACK); csREGselect.setFill(Color.BLACK);
        csIRload.setFill(Color.BLACK); csIRoeS1.setFill(Color.BLACK);
        csIRoeS2.setFill(Color.BLACK); csopcode.setFill(Color.BLACK);
        csopcodeALU.setFill(Color.BLACK); csReset.setFill(Color.BLACK);
        csPCload.setFill(Color.BLACK); csPCoeS1.setFill(Color.BLACK);
        csPCMARselect.setFill(Color.BLACK); csMARload.setFill(Color.BLACK);
        csMemRead.setFill(Color.BLACK); csMDRload.setFill(Color.BLACK);
        csMDRoeS2.setFill(Color.BLACK); csMemWrite.setFill(Color.BLACK);
        csMemOP.setFill(Color.BLACK); csMemWait.setFill(Color.BLACK);
    }
    public void clearAll(){
        tfS2op.clear(); tfZflag.clear(); tfALUop.clear(); tfCload.clear(); tfREGload.clear();
        tfAload.clear(); tfAoe.clear(); tfBload.clear(); tfBoe.clear(); tfREGselect.clear(); 
        tfIRload.clear(); tfIRoeS1.clear(); tfIRoeS2.clear(); tfopcode.clear(); tfopcodeALU.clear();
        tfReset.clear(); tfPCload.clear(); tfPCoeS1.clear(); tfPCMARselect.clear(); tfMARload.clear();
        tfMemRead.clear(); tfMDRload.clear(); tfMDRoeS2.clear(); tfMemWrite.clear(); tfMemOP.clear();
        tfMemWait.clear(); tfR0.clear(); tfR1.clear(); tfR2.clear(); tfR3.clear(); tfR4.clear(); tfR5.clear();
        tfR6.clear(); tfR7.clear(); tfR8.clear(); tfR9.clear(); tfR10.clear(); tfR11.clear(); tfR12.clear();
        tfR13.clear(); tfR14.clear(); tfR15.clear(); tfR16.clear(); tfR17.clear(); tfR18.clear(); tfR19.clear();
        tfR20.clear(); tfR21.clear(); tfR22.clear(); tfR23.clear(); tfR24.clear(); tfR25.clear(); tfR26.clear();
        tfR27.clear(); tfR28.clear(); tfR29.clear(); tfR30.clear(); tfR31.clear(); tfIR.clear(); tfPC.clear();
        tfMAR.clear(); tfMDR.clear(); tfA.clear(); tfB.clear(); tfC.clear();
    }
    public void clear(TextField... args){
        for(TextField arg : args) arg.clear();
    }
    public void on(Text... args){
        for(Text arg : args) arg.setFill(Color.RED);
    }
    public void off(Text... args){
        for(Text arg : args) arg.setFill(Color.BLACK);
    }
    public void set(TextField tf, int i){
        tf.setText(Integer.toString(i));
    }
}
