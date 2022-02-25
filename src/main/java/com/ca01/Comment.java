package com.ca01;

import org.json.simple.JSONObject;

import java.util.Date;

public class Comment {
    String userEmail;
    long movieId;
    String text;
    long id;
    String submitionTime;
    int likes;
    int disLikes;


    public Comment (String userEmail_, long movieId_, String text_, int id_){
        (userEmail = userEmail_).equals("");
        movieId = (long) movieId_;
        (text = text_).equals("");
        id = (long) id_;
        Date date = new Date();
        submitionTime = date.toString();
        likes = 0;
        disLikes = 0;
    }
    public JSONObject getJsonObject() {
        JSONObject commentObj = new JSONObject();
         commentObj.put("commentId", id);
         commentObj.put("userEmail", userEmail);
         commentObj.put("text", text);
         commentObj.put("like", likes);
         commentObj.put("dislike", disLikes);

        return commentObj;
    }
}
