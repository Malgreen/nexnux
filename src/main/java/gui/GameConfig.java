package main.java.gui;

import main.java.Game;

import javax.swing.*;
import java.awt.event.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GameConfig extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField fieldModDir;
    private JTextField fieldDeployDir;
    private JLabel textModDir;
    private JLabel textDeployDir;

    public GameConfig() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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

    public Game onOK() {
        String modDeployFolder = fieldDeployDir.getText();
        String modFolder = fieldModDir.getText();
        String modListFile = modFolder + "\\" + "mods.json";
        Game game = new Game("test", modDeployFolder, modFolder, modListFile);
        // add your code here
        return game;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    private void getModListFile(String modFolder){
        Path path = Paths.get(modFolder + "\\" + "mods.json");
        if (path.toFile().isFile()) {
            return;
        } else {

        }
    }

    public static void main(String[] args) {
        GameConfig dialog = new GameConfig();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
