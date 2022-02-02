package com.example.storeme;

public class StoreMeObject {

    //Variables
    private int id;
    private String type;
    private String attribute1;
    private String attribute2;

    //Constructor
    public StoreMeObject (String type, String attribute1, String attribute2){
        this.type = type;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
    }

    //Setters
    public void setObjectId(int id){
        this.id = id;
    }

    public void setObjectType(String type){
        this.type = type;
    }

    public void setObjectAttribute1(String attribute1){
        this.attribute1 = attribute1;
    }

    public void setObjectAttribute2(String attribute2){
        this.attribute2 = attribute2;
    }

    //Getters
    public int getObjectId(){
        return id;
    }

    public String getObjectType(){
        return type;
    }

    public String getObjectAttribute1(){
        return attribute1;
    }

    public String getObjectAttribute2(){
        return attribute2;
    }
}
