package main.java;

import main.java.utilities.ErrorHandler;
import main.java.utilities.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameList {

    List<Game> games;
    JSONHandler jsonHandler;
    JSONParser jsonParser;
    ErrorHandler errorHandler;
    private String filePath;

    public GameList(String gameListFile) {
        jsonHandler = new JSONHandler();
        jsonParser = new JSONParser();
        errorHandler = new ErrorHandler();
        this.filePath = gameListFile;
        this.games = loadList();
    }

    public void modifyGame(String name, String modDir, String deployDir, String modListFile){
        Game game = new Game(name, deployDir, modDir, modListFile);
        while (gameExists(games, name)){
            games.removeIf(obj -> obj.getName().equals(name));
        }
        games.add(game);
        saveList();
    }

    Game loadGame(JSONObject jo){
        String gameName = (String) jo.get("game-name");
        String modDir = (String) jo.get("mod-directory");
        String deployDir = (String) jo.get("deploy-directory");
        String modListFile = (String) jo.get("mod-list-file");

        Game game = new Game(gameName, deployDir, modDir, modListFile);
        return game;
    }

    public void saveList(){
        JSONArray jsonArray = new JSONArray();
        for (Game game:games){
            JSONObject gameObj = new JSONObject();
            gameObj.put("game-name", game.getName());
            gameObj.put("mod-directory", game.getModDirectory());
            gameObj.put("deploy-directory", game.getDeployDirectory());
            gameObj.put("mod-list-file", game.getModListFile());
            jsonArray.add(gameObj);
        }
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Game> loadList(){
        List<Game> loadedGames = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader); // Temporary object
            JSONArray gameList = (JSONArray) obj;

            // Iterate over each read game
            for (Object game:gameList){
                Game loadedGame = loadGame( (JSONObject) game); //Add the game to the list
                loadedGames.add(loadedGame);
            }
        } catch (FileNotFoundException e) {
           errorHandler.showErrorBox(e);
            setupFile();
            System.out.println("Setting up file");
            e.printStackTrace();
        } catch (IOException e) {
            errorHandler.showErrorBox(e);
            e.printStackTrace();
        } catch (ParseException e) {
            games = new ArrayList<Game>();
            reload();
            //errorHandler.showErrorBox(e);
            e.printStackTrace();
        }
        return loadedGames;
    }
    private void setupFile(){
        try (FileWriter file = new FileWriter(filePath)) {
            file.flush();

        } catch (IOException e) {
            errorHandler.showErrorBox(e);
            e.printStackTrace();
        }
    }
    void reload(){
        saveList();
        loadList();
    }

    public boolean gameExists(List<Game> list, String gameName){
        return list.stream().filter(o -> o.getName().equals(gameName)).findAny().isPresent();
    }
}
