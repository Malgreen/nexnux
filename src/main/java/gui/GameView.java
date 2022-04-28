package main.java.gui;

import main.java.Game;
import main.java.GameList;
import main.java.utilities.ErrorHandler;

import javax.swing.*;
import java.io.File;

public class GameView {
    private JPanel panelMain;
    private JList listDisplayGames;
    private JButton buttonAccept;
    private JButton buttonAdd;
    private JButton buttonConfig;
    private JButton toolbarSettings;
    private JButton buttonRemove;

    private ErrorHandler errorHandler;

    private String gameListFile;
    GameList gameList;
    private Game focusedGame;

    public GameView() {
        buttonAccept.addActionListener(e -> onAccept());
        buttonConfig.addActionListener(e -> onConfig());
        buttonAdd.addActionListener(e -> onAddGame());
        buttonRemove.setEnabled(false);

        errorHandler = new ErrorHandler();

        getProgramSettings();
        gameList = new GameList(gameListFile);

        displayGames();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GameView");
        frame.setContentPane(new GameView().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    void onAccept(){

    }

    void onConfig(){

    }

    void onAddGame(){
        configureGame(true);
    }

    void displayGames(){
        DefaultListModel<Object> listOfGames  = new DefaultListModel<>(); // This class is used to display in JLists
        for(Game game:gameList.loadList()){
            System.out.println("Loading game list: " + game.toString());
            listOfGames.addElement(game.toString());
        }
        listDisplayGames.setModel(listOfGames); //lets go
    }

    void getProgramSettings(){
        gameListFile = System.getProperty("user.home") + File.separator + ".nexnux" + File.separator + "games.json";
    }

    void configureGame(boolean isInstall){
        GameConfigView gameConfigurator = new GameConfigView(isInstall, focusedGame);
        Game game = gameConfigurator.showDialog(); //holy mother of christ it works
        if (game != null) {
            if (gameList.dirsAlreadyUsed(game.getModDirectory(), game.getDeployDirectory())) { errorHandler.showPopup("Mod or deploy directory already in use"); return;}
            if (game.getModDirectory().equals(game.getDeployDirectory())) { errorHandler.showPopup("Mod and deploy directory cannot be the same"); return; }
            gameList.modifyGame(game.getName(), game.getModDirectory(), game.getDeployDirectory(), game.getModListFile());
        }
        displayGames();
    }
}
