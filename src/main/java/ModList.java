package main.java;

import main.java.utilities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.*;
import org.json.simple.parser.*;

public class ModList {

    List<Mod> mods;
   // List<Mod> loadedMods;
    JSONHandler jsonHandler;
    JSONParser jsonParser;

    public ModList(String modListFile) {
        this.jsonHandler = new JSONHandler();
        this.jsonParser = new JSONParser();
        this.mods = loadList(modListFile);

    }
    public List<Mod> getAllMods(){ return mods; }
    public List<Mod> getActiveMods(){ return mods.stream().filter( o -> o.getEnabled() == true).collect(Collectors.toList());}

    public void modifyMod(String modName, float fileSize, int index, boolean enabled){
        Mod mod = new Mod(modName, fileSize, index, enabled);
        while (containsMod(mods, modName)){
            // can either use parameter or get object property name
            mods.remove(mod);
        }
        mods.add(index, mod);

        // Handle JSON writing *MOVED TO SAVING THE LIST*
        // Could also just use parameters?
        // JSONObject modObject = new JSONObject();
        // modObject.put("mod-name", mod.getName());
        // modObject.put("filesize", mod.getFileSize());
        // modObject.put("enabled", mod.getEnabled());
        // modObject.put("index", mod.getIndex());
    }

    Mod loadMod(JSONObject jo){
        // Load mod from JSON object
        String modName = (String) jo.get("mod-name");
        double modSize = (double) jo.get("filesize");
        int modIndex =Math.toIntExact((long)jo.get("index"));
        boolean modEnabled = (boolean) jo.get("enabled");

        Mod mod = new Mod(modName, modSize, modIndex, modEnabled);
        return mod;
    }

    public void saveList(String outputPath){
        JSONArray modArray = new JSONArray();

        for (Mod mod:mods) {
            // Create a JSONObject that can later be written to a file from each mod
            JSONObject modObject = new JSONObject();
            modObject.put("mod-name", mod.getName());
            modObject.put("filesize", mod.getFileSize());
            modObject.put("enabled", mod.getEnabled());
            modObject.put("index", mod.getIndex());
            modArray.add(modObject);
        }
        try (FileWriter file = new FileWriter(outputPath)) {
            file.write(modArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Mod> loadList(String inputFile){
        List<Mod> loadedMods = new ArrayList<>();
        try (FileReader reader = new FileReader(inputFile))
        {
            if (reader.toString() == "") { return loadedMods; }
            //Read JSON file
            Object obj = jsonParser.parse(reader);// Temporary object
            JSONArray modList = (JSONArray) obj;

            // Iterate over each read mod
            for (Object mod:modList){
                Mod loadedMod = loadMod( (JSONObject) mod); // Create a new mod by parsing an object
                loadedMods.add(Math.toIntExact(loadedMod.getIndex()), loadedMod); //Insert the mod in the list where the index is
            }
        } catch (FileNotFoundException e) {
            setupFile(inputFile);
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            mods = new ArrayList<>();
            Mod mod = new Mod("modtest", 1.0, 0, false);
            mods.add(mod);
            tryAgain(inputFile);
            //e.printStackTrace();
        }
        System.out.print(loadedMods.get(0));
        return loadedMods;
    }
    private void tryAgain(String file){
        saveList(file);
        loadList(file);
    }

    private void setupFile(String filePath){
        try (FileWriter file = new FileWriter(filePath)) {
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean containsMod(List<Mod> list, String modName){
        return list.stream().filter(o -> o.getName().equals(modName)).findAny().isPresent();
    }
}
