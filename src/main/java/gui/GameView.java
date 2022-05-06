package main.java.gui;

import main.java.Game;
import main.java.GameList;
import main.java.gui.utilities.CheckboxListCellRenderer;
import main.java.gui.utilities.ListItemTransferHandler;
import main.java.utilities.ErrorHandler;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        buttonRemove.addActionListener(e -> onRemoveGame());
        buttonRemove.setEnabled(false);
        buttonConfig.setEnabled(false);
        setupWindow();
        errorHandler = new ErrorHandler();

        getProgramSettings();
        gameList = new GameList(gameListFile);

        displayGames();
        listDisplayGames.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                buttonConfig.setEnabled(listDisplayGames.getSelectedValue() != null);
                buttonRemove.setEnabled(listDisplayGames.getSelectedValue() != null);
                focusGame();
            }
        });
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
        // Find object in gameList that has the same name as the selected item in listDiplayGames
        focusGame();
        System.out.println(listDisplayGames.getSelectedValue());
        System.out.println(focusedGame);


        if (focusedGame != null){
            gameList.removeGame(focusedGame);
            configureGame(false);
        }
    }

    void onAddGame(){
        configureGame(true);
    }

    void onRemoveGame() { gameList.removeGame(focusedGame); displayGames(); }

    void displayGames(){
        DefaultListModel<Object> listOfGames  = new DefaultListModel<>(); // This class is used to display in JLists
        listDisplayGames.setDragEnabled(true);
        listDisplayGames.setDropMode(DropMode.INSERT); // Fix this
        listDisplayGames.setTransferHandler(new ListItemTransferHandler());
        //Drag and drop works, but maybe save the actual list in the way that displayed list is shown?
        // Maybe dont use drag and drop, but specific buttons for moving load order
        // ^ that would be ugly though, lets try to be a bit more civilized
        for(Game game:gameList.loadList()){
            System.out.println("Loading game list: " + game.toString());
            listOfGames.addElement(game.toString());
        }
        listDisplayGames.setModel(listOfGames); //lets go
        saveDisplayedGames(listOfGames);
    }

    void saveDisplayedGames(DefaultListModel<Object> displayedList){

        for(int i=0; i < displayedList.getSize(); i++){
            String gameName = displayedList.getElementAt(i).toString();
            String gameModDir;
            String gameDeployDir;
            String gameModsFile;
        }
    }

    void focusGame(){
        if (listDisplayGames.getSelectedValue() != null) {
            focusedGame = gameList.loadList().stream().filter(o -> o.getName().equals(listDisplayGames.getSelectedValue().toString())).findAny().orElse(null);
        }
    }

    void getProgramSettings(){
        gameListFile = System.getProperty("user.home") + File.separator + ".nexnux" + File.separator + "games.json";
    }

    void configureGame(boolean isInstall){
        GameConfigView gameConfigurator = new GameConfigView(isInstall, focusedGame);
        Game game = gameConfigurator.showDialog(); //holy mother of christ it works
        if (game != null) {

            // First check if the directories can be used, then modify/add the game to the list
            if (gameList.dirsAlreadyUsed(game.getModDirectory(), game.getDeployDirectory())) { errorHandler.showPopup("Mod or deploy directory already in use"); return;}
            if (game.getModDirectory().equals(game.getDeployDirectory())) { errorHandler.showPopup("Mod and deploy directory cannot be the same"); return; }
            gameList.modifyGame(game.getName(), game.getModDirectory(), game.getDeployDirectory(), game.getModListFile());
        }
        displayGames();
    }

    private void setupWindow(){
        this.panelMain.setMinimumSize(new Dimension(500,300));
        listDisplayGames.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    }
}
