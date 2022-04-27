package main.java.gui;

import main.java.Game;
import main.java.utilities.ErrorHandler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.AccessException;
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
    private Game currentGame;
    private ErrorHandler errorHandler;

    private List<JTextField> tfList;

    public GameConfig() {
        errorHandler = new ErrorHandler();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.setEnabled(!areFieldsEmpty());

        //Setup text field stuff
        tfList = new ArrayList<>();
        tfList.add(fieldModDir);
        tfList.add(fieldDeployDir);
        for (JTextField tf:tfList){
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
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public Game showDialog(){
        setVisible(true);
        return currentGame;
    }

    private boolean areFieldsEmpty(){
        boolean bool = fieldDeployDir.getText().trim().isEmpty() || fieldModDir.getText().trim().isEmpty();
        return bool;
    }

    public void onOK() {
        try{
            System.out.println("OK pressed");
            String modDeployFolder = fieldDeployDir.getText();
            String modFolder = fieldModDir.getText();
            String modListFile = modFolder + "\\" + "mods.json";
            currentGame = new Game("test", modDeployFolder, modFolder, modListFile);
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
    private void getModListFile(String modFolder){
        Path path = Paths.get(modFolder + "\\" + "mods.json");
        if (path.toFile().isFile()) {
            return;
        } else {

        }
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
            if (areFieldsEmpty()){
                buttonOK.setEnabled(false);
            } else {
                buttonOK.setEnabled(true);
            }
        }
    };

    public static void main(String[] args) {
        GameConfig dialog = new GameConfig();
        dialog.pack();
        System.exit(0);
    }
}
