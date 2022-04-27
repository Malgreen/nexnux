package main.java.gui;

import main.java.Game;
import main.java.utilities.ErrorHandler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GameConfig extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField fieldModDir;
    private JTextField fieldDeployDir;
    private JLabel textModDir;
    private JLabel textDeployDir;
    private JTextField fieldName;
    private JLabel textName;
    private Game currentGame;
    private final ErrorHandler errorHandler;

    public GameConfig() {
        errorHandler = new ErrorHandler();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.setEnabled(!areFieldsEmpty());

        //Setup text field stuff
        List<JTextField> tfList = new ArrayList<>();
        tfList.add(fieldModDir);
        tfList.add(fieldDeployDir);
        tfList.add(fieldName);
        for (JTextField tf: tfList){
            tf.getDocument().addDocumentListener(documentListener);
        }


        //Setup button listeners
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public Game showDialog(){
        setVisible(true);
        return currentGame;
    }

    private boolean areFieldsEmpty(){
        return fieldDeployDir.getText().trim().isEmpty() || fieldModDir.getText().trim().isEmpty() || fieldName.getText().trim().isEmpty();
    }

    public void onOK() {
        try{
            System.out.println("OK pressed");
            String modDeployFolder = fieldDeployDir.getText();
            String modFolder = fieldModDir.getText();
            String gameName = fieldName.getText();
            String modListFile = modFolder + "\\" + "mods.json";
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
        System.out.println("cancelled");
        currentGame = null;
        setVisible(false);
        dispose();
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

    public static void main(String[] args) {
        GameConfig dialog = new GameConfig();
        dialog.pack();
        System.exit(0);
    }
}
