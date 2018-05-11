package sample;

import javafx.scene.control.TextField;

public class Validator {
    public static double validate_double(TextField t){
        try{
            return Double.parseDouble(t.getText());
        }catch(NumberFormatException e){
            return -1;
        }
    }
    public static double validate_double(double t){
        try{
            return t;
        }catch(NumberFormatException e){
            return -1;
        }
    }
    public static int validate_int(TextField t){
        try{
            return Integer.parseInt(t.getText());
        }catch(NumberFormatException e){
            return -1;
        }
    }
    public static int validate_int(int t){
        try{
            return t;
        }catch(NumberFormatException e){
            return -1;
        }
    }
    public static String validate_string(TextField t){
        if(!t.getText().isEmpty())
            return t.getText();
        return "-1";
    }
    public static String validate_string(String t){
        if(!t.isEmpty())
            return t;
        return "-1";
    }
}
