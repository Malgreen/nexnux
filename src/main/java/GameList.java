package main.java;

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

    public GameList() {
        jsonHandler = new JSONHandler();
        jsonParser = new JSONParser();
    }

    void modifyGame(){

    }

    Game loadGame(JSONObject jo){
        String gameName = (String) jo.get("game-name");
        String modDir = (String) jo.get("mod-directory");
        String deployDir = (String) jo.get("deploy-directory");
        String modListFile = (String) jo.get("mod-list-file");

        Game game = new Game(gameName, deployDir, modDir, modListFile);
        return game;
    }

    public void saveList(String outputPath){
        JSONArray jsonArray = new JSONArray();
        for (Game game:games){
            JSONObject gameObj = new JSONObject();
            gameObj.put("game-name", game.getName());
            gameObj.put("mod-directory", game.getModDirectory());
            gameObj.put("deploy-directory", game.getDeployDirectory());
            gameObj.put("mod-list-file", game.getModListFile());
            jsonArray.add(gameObj);
        }
        try (FileWriter file = new FileWriter(outputPath + "\\" + "games.json")) {
            file.write(jsonArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Game> loadList(String inputFile){
        List<Game> loadedGames = new ArrayList<>();
        try (FileReader reader = new FileReader(inputFile))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader); // Temporary object
            JSONArray gameList = (JSONArray) obj;

            // Iterate over each read game
            for (Object game:gameList){
               // TODO: IMPLEMENT loadGame  Mod loadedMod = loadMod( (JSONObject) mod); // Create a new mod by parsing an object
                Game loadedGame = loadGame( (JSONObject) game);
                games.add(loadedGame);
               // TODO: IMPLEMENT  mods.add(loadedMod.getIndex(), loadedMod); //Insert the mod in the list where the index is
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return loadedGames;
    }
}
