package main.java;

import main.java.utilities.ErrorHandler;

import java.io.File;
import java.util.List;

public class Game {
    private String name;
    private String deployDirectory;
    private String modDirectory;
    private String modListFile;
    private ModList modList;
    private ModDeployer modDeployer;
    private ErrorHandler errorHandler;

    public Game(String name, String deployDirectory, String modDirectory, String modListFile) {
        this.errorHandler = new ErrorHandler();

        this.name = name;
        this.deployDirectory = deployDirectory;
        this.modDirectory = modDirectory;
        validateFolders(deployDirectory,modDirectory);
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
    private void validateFolders(String deployPath, String modsPath){
        try {
            String folderDeployPath = deployPath;
            File deployFolder = new File(folderDeployPath);
            deployFolder.mkdir();

            String folderModsPath = modsPath.substring(0, deployPath.lastIndexOf(File.separator));
            File modsFolder = new File(folderModsPath);
            modsFolder.mkdir();
        } catch (Exception e) {
            errorHandler.showPopup("Invalid or illegal path");
        }
    }

    @Override
    public String toString(){
        return name;
    }

}
