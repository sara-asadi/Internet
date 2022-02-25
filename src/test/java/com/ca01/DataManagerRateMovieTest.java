package com.ca01;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DataManagerRateMovieTest {
    protected DataManager dataManager;
    protected JSONParser parser;

    @BeforeEach
    void setUp(){
        dataManager = new DataManager();
        parser = new JSONParser();

        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));
        dataManager.users.add(new User("sajjad@ut.ac.ir","","","",""));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

    }
    
    @Test
    void userNewRateMovieTest() throws ParseException {
        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 7}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);
        assertEquals(7,dataManager.movies.get(0).rating);
        assertEquals(1, dataManager.users.get(0).ratedMovies.size());
    }

    @Test
    void userUpdateRateMovieTest() throws ParseException {
        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 7}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);

        String rate2String = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 6}";
        JSONObject rate2Object = (JSONObject) parser.parse(rate2String);
        dataManager.rateMovie(rate2Object);

        assertEquals(6,dataManager.movies.get(0).rating);
        assertEquals(1, dataManager.users.get(0).ratedMovies.size());
    }

    @Test
    void multiUserRateHandlingWithBoundriesValueTest() throws ParseException {
       
        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 1}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);

        String rate2String = "{\"userEmail\": \"sajjad@ut.ac.ir\", \"movieId\": 1, \"score\": 10}";
        JSONObject rate2Object = (JSONObject) parser.parse(rate2String);
        dataManager.rateMovie(rate2Object);

        assertEquals(5.5,dataManager.movies.get(0).rating);
    }
    @Test
    void rateUserNotFountTest() throws ParseException {
        String rateString = "{\"userEmail\": \"sara1@ut.ac.ir\", \"movieId\": 1, \"score\": 7}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        int error = dataManager.rateMovie(rateObject);
        assertEquals(-1, error);
        assertNull(dataManager.movies.get(0).rating);
    }

    @Test
    void rateMovieNotFound() throws ParseException {
        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 2, \"score\": 7}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        int error = dataManager.rateMovie(rateObject);
        assertEquals(-2, error);
        assertEquals(0, dataManager.users.get(0).ratedMovies.size());
    }

    @Test
    void rateScoreMoreThatValidTest() throws ParseException {
        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 12}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);
        assertNull(dataManager.movies.get(0).rating);
        assertEquals(0, dataManager.users.get(0).ratedMovies.size());
    }

    @Test
    void rateScoreEqualsToZeroTest() throws ParseException {
        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 0}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);
        assertNull(dataManager.movies.get(0).rating);
        assertEquals(0, dataManager.users.get(0).ratedMovies.size());
    }

    @Test
    void rateScoreLessThatValidTest() throws ParseException {
        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": -3}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        
        dataManager.rateMovie(rateObject);
        assertNull(dataManager.movies.get(0).rating);
        assertEquals(0, dataManager.users.get(0).ratedMovies.size());
    }
}
