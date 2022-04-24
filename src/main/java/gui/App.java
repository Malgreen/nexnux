package main.java.gui;

import javax.swing.*;

import com.github.junrar.exception.RarException;
import main.java.utilities.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class App {
    private JButton buttonBrowse;
    private JPanel panelMain;
    private JButton buttonExtract;
    private JTextField outputPathTextField;
    private JButton buttonOutput;
    private JScrollPane modPane;

    private final FileExtractor extractor;
    private String currentFile;
    private String currentFileName;
    private String currentOutput;

    public App() {
        extractor = new FileExtractor();
        buttonBrowse.addActionListener(e -> chooseFile());
        buttonExtract.addActionListener(e -> extractFile());
        buttonOutput.addActionListener(e -> chooseOutputPath());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void chooseOutputPath(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.showOpenDialog(null);
        if(fileChooser.getSelectedFile() != null)
        {
            currentOutput = fileChooser.getSelectedFile().getAbsolutePath();
            outputPathTextField.setText(currentOutput);
        }

    }

    private void chooseFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        if (fileChooser.getSelectedFile() != null) {
            currentFile = fileChooser.getSelectedFile().getAbsolutePath();
            currentFileName = fileChooser.getSelectedFile().getName();
        }

    }
    private void extractFile(){
        if (currentFile == null) return;
        try {
            // Extract the file to a new folder called the name of the file
            extractor.extractFile(currentFile, currentOutput);
        }
        catch (IOException ex){ System.out.println(ex.getMessage());}
        catch (RarException e) { System.out.println("Rar archive not supported, rar5 or later"); }
    }

}
