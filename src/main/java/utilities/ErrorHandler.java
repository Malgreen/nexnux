package main.java.utilities;

import javax.swing.*;
import java.awt.*;

public class ErrorHandler {

    public void showErrorBox(Exception e){

        JOptionPane.showMessageDialog(new JFrame("Error"), e.getMessage());
    }

    public void showPopup(String text){
        JOptionPane.showMessageDialog(new JFrame("Alert"), text);
    }

}
