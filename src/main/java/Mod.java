package main.java;

public class Mod {
    private String name;
    private long index;
    private boolean enabled;
    private double fileSize;

    public Mod(String name, double fileSize, long index, boolean enabled) {
        this.name = name;
        this.fileSize = fileSize;
        this.index = index;
        this.enabled = enabled;
    }
    public String getName(){
        return this.name;
    }
    public double getFileSize(){
        return this.fileSize;
    }
    public boolean getEnabled(){
        return this.enabled;
    }
    public long getIndex(){ return this.index; }

    public void setEnabled(boolean enable){
        enabled = enable;
    }

    @Override
    public String toString(){
        return getName();
    }

}
