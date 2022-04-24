package main.java;

import main.java.utilities.*;

import java.io.IOException;
import java.util.List;

import org.json.simple.*;

public class ModList {

    List<Mod> mods;
    List<Mod> loadedMods;
    JSONHandler jsonHandler;

    public ModList(List<Mod> mods) {
        this.mods = mods;
        this.jsonHandler = new JSONHandler();
    }

    public void modifyMod(String modName, float fileSize){
        Mod mod = new Mod(modName, false, fileSize);
        mods.add(mod);

        //Handle JSON writing
        JSONObject modObject = new JSONObject();
        modObject.put("modname", mod.getName());
        modObject.put("filesize", mod.getFileSize());
        modObject.put("enabled", mod.getEnabled());
    }

    void loadMod(JSONObject jo){
        // Load mod from JSON object
        String modName = (String) jo.get("modname");
        float modSize = (float) jo.get("filesize");
        boolean modEnabled = (boolean) jo.get("enabled");

        Mod mod = new Mod(modName, modEnabled, modSize);
        loadedMods.add(mod);
    }

    public void saveList() throws IOException {
        for (Mod mod:mods) {

        }
    }

    public void loadList(){
    }
}
