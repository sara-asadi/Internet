package com.ca01;

import org.json.simple.JSONObject;

public class Actor{
    long id;
    String name;
    String birthDate;
    String nationality;

    public  Actor(long id_, String name_, String birthDate_, String nationality_){
        id = id_;
        name = name_;
        birthDate = birthDate_;
        nationality = nationality_;
    }
    public JSONObject getJsonObject() {
        JSONObject actorObj = new JSONObject();
        actorObj.put("actorId", id);
        actorObj.put("name", name);
        return actorObj;
    }
}