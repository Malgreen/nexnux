package main.java.utilities;

import main.java.Mod;
import org.json.simple.*;

public class JSONHandler {

    public void createFile(){
        // Setup initial file
    }

    public void addObject(Object o){
        JSONObject jo = new JSONObject();

    }
    public void readFile(){

    }
    public void readLine(){

    }

    public void addMod(Mod mod){
        JSONObject modObject = new JSONObject();
        modObject.put("modname", mod.getName());
        modObject.put("filesize", mod.getFileSize());
        modObject.put("enabled", mod.getEnabled());

    }
}
