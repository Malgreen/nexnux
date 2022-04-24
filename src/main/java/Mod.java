package main.java;

public class Mod {
    private String name;
    private int index;
    private boolean enabled;
    private float fileSize;

    public Mod(String name, float fileSize, int index, boolean enabled) {
        this.name = name;
        this.fileSize = fileSize;
        this.index = index;
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
    public int getIndex(){ return this.index; }

    public void setEnabled(boolean enable){
        enabled = enable;
    }

}
