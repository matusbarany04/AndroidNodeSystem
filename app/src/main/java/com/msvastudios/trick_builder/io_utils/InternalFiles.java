package com.msvastudios.trick_builder.io_utils;

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
