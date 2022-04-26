package main.java.gui;

import javax.swing.*;

import com.github.junrar.exception.RarException;
import main.java.Game;
import main.java.GameList;
import main.java.utilities.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class App {
    private JButton buttonBrowse;
    private JPanel panelMain;
    private JButton buttonExtract;
    private JTextField outputPathTextField;
    private JButton buttonOutput;
    private JScrollPane modPane;
    private JList list1;
    private JButton newgameButton;
    private JButton buttonRefresh;

    private final FileExtractor extractor;
    private String currentFile;
    private String currentFileName;
    private String currentOutput;
    private GameList gameList;
    private List<Game> listOfGames;

    public App() {
        extractor = new FileExtractor();
        buttonBrowse.addActionListener(e -> chooseFile());
        buttonExtract.addActionListener(e -> extractFile());
        buttonOutput.addActionListener(e -> chooseOutputPath());
        newgameButton.addActionListener(e -> addGameWindow());
        buttonRefresh.addActionListener(e -> refreshList());

       // listOfGames = gameList.loadList("C:/Users/martinalgreen/Desktop/games.json");
        gameList = new GameList();
        Path path = Paths.get("C:/Users/martinalgreen/Desktop/games.json");
        if (path.toFile().isFile()) {
            listOfGames = gameList.loadList("C:/Users/martinalgreen/Desktop/games.json");
        } else {
            listOfGames = new ArrayList<>();
        }
        displayGames();
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

    void addGameWindow(){
        GameConfig gameConfigurator = new GameConfig();
        gameConfigurator.setVisible(true);
        Game game = gameConfigurator.onOK();
        if (game != null) { listOfGames.add(game); }
    }

    void displayGames(){
        DefaultListModel gameslists  = new DefaultListModel<>();
        for(Game game:listOfGames){
            gameslists.addElement(game.toString());
        }
        list1.setModel(gameslists); //lets go
    }

    void refreshList(){
        displayGames();
    }

}
