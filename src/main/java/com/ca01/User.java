package com.ca01;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class User {
    String email;
    String password;
    String nickname;
    String name;
    String birthDate;
    List<List<Long>> ratedMovies;
    List<List<Long>> ratedComments;
    List<Long> watchList;

    public  User(String email_, String password_, String nickname_, String name_, String birthDate_){
        email = email_;
        password = password_;
        nickname = nickname_;
        name = name_;
        birthDate = birthDate_;
        ratedMovies = new ArrayList<List<Long>>();
        ratedComments = new ArrayList<List<Long>>();
        watchList = new ArrayList<>();
    }
    public int getAge() throws ParseException {
        Date bDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        Date cDate = new Date();
        return cDate.getYear()-bDate.getYear();
    }
    public boolean isInWatchList(long movieId){
        for (Long id: watchList)
            if (id == movieId)
                return true;
        return false;
    }
    public int addWatchList(long movieId){
        watchList.add(movieId);
        return 0;
    }
    public int removeWatchList(long movieId){
        watchList.remove(movieId);
        return 0;
    }
}