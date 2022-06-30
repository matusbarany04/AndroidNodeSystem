package com.msvastudios.trick_builder.utils.textfiles;

public enum InternalFiles {
    NODE("node");

    String fileName;

    InternalFiles(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }
}
