package main.java;

public class Mod {
    private String name;
    private int index;
    private boolean enabled;
    private float fileSize;

    public Mod(String name, boolean enabled, float fileSize) {
        this.name = name;
        this.fileSize = fileSize;
        this.enabled = enabled;
    }
    public String getName(){
        return this.name;
    }
    public float getFileSize(){
        return this.fileSize;
    }
    public boolean getEnabled(){
        return this.enabled;
    }

    public void setEnabled(boolean enable){
        enabled = enable;
    }

}
