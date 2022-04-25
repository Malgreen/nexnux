package main.java;

import java.util.List;

public class Game {
    private String name;
    private String deployDirectory;
    private String modDirectory;
    private String modListFile;
    private ModList modList;
    private ModDeployer modDeployer;

    public Game(String name, String deployDirectory, String modDirectory, String modListFile) {
        this.name = name;
        this.deployDirectory = deployDirectory;
        this.modDirectory = modDirectory;
        this.modListFile = modListFile;
        this.modList = new ModList(modListFile);

    }

    public String getName() { return name; }
    public String getDeployDirectory() { return deployDirectory; }
    public String getModDirectory() { return modDirectory; }
    public String getModListFile() { return modListFile; }

    private void GetMods(){
        ModList modList = new ModList(modListFile);
        List<Mod> mods = modList.getAllMods();
    }

    @Override
    public String toString(){
        return name;
    }

}
