package com.ca01;

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
        userEmail = userEmail_;
        movieId = movieId_;
        text =text_;
        id = id_;
        Date date = new Date();
        submitionTime = date.toString();
        likes = 0;
        disLikes = 0;
    }
}
