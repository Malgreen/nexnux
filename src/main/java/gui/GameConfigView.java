package main.java.gui;

import main.java.Game;
import main.java.utilities.ErrorHandler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameConfigView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField fieldDeployDir;
    private JTextField fieldModDir;
    private JTextField fieldGameName;
    private JButton buttonDeployDir;
    private JButton buttonModDir;

    private ErrorHandler errorHandler;
    private Game currentGame;

    public GameConfigView(boolean isInstall, Game inputGame) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        errorHandler = new ErrorHandler();
        buttonOK.setEnabled(false);

        // Dialog stuff
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { onCancel(); } });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Buttons
        buttonDeployDir.addActionListener(e -> onDeployBrowse());
        buttonModDir.addActionListener(e -> onModBrowse());

        // Setup for checking if any fields are empty
        List<JTextField> tfList = new ArrayList<>();
        tfList.add(fieldModDir);
        tfList.add(fieldDeployDir);
        tfList.add(fieldGameName);
        for (JTextField tf: tfList){
            tf.getDocument().addDocumentListener(documentListener);
        }

        //Check if it is a new game
        if (!isInstall) {
            currentGame = inputGame;
            displayGameInfo();
        }

    }
    //public static void main(String[] args) {
    //    //GameConfigView dialog = new GameConfigView();
    //    dialog.pack();
    //    dialog.setVisible(true);
    //    System.exit(0);
    //}

    public Game showDialog(){
        setVisible(true);
        pack();
        return currentGame;
    }

    private void onOK() {
        try{
            System.out.println("OK pressed");
            String modDeployFolder = fieldDeployDir.getText();
            String modFolder = fieldModDir.getText();
            String gameName = fieldGameName.getText();
            String modListFile = modFolder + File.separator + "mods.json";
            currentGame = new Game(gameName, modDeployFolder, modFolder, modListFile); //DONT DO THIS
        } catch (Exception e) {
            errorHandler.showErrorBox(e);
            e.printStackTrace();
            return;
        }
        setVisible(false);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onDeployBrowse(){
        chooseFolder(fieldDeployDir);
    }

    private void onModBrowse(){
        chooseFolder(fieldModDir);
    }

    private void chooseFolder(JTextField outputTextField){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.showOpenDialog(null);
        if(fileChooser.getSelectedFile() != null)
        {
            outputTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void displayGameInfo(){
        fieldGameName.setText(currentGame.getName());
        fieldDeployDir.setText(currentGame.getDeployDirectory());
        fieldModDir.setText(currentGame.getModDirectory());
    }

    private boolean areFieldsEmpty(){
        return fieldDeployDir.getText().trim().isEmpty() || fieldModDir.getText().trim().isEmpty() || fieldGameName.getText().trim().isEmpty();
    }

    DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            buttonOK.setEnabled(!areFieldsEmpty());
        }
    };
}
