package com.ca01;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {
    protected DataManager dataManager;
    protected JSONParser parser;

    @BeforeEach
    void setUp(){
        dataManager = new DataManager();
        parser = new JSONParser();
    }

    @Test
    void addNewActorTest() throws ParseException {
        String actorString = "{\"id\": 2, \"name\": \"Al Pacino\", \"birthDate\": \"1940-04-25\", \"nationality\": \"American\"}";
        JSONObject actorObject = (JSONObject) parser.parse(actorString);
        dataManager.addActor(actorObject);
        assertTrue(dataManager.actors.size()==1);
    }

    @Test
    void updateActorTest() throws ParseException {
        String actorString = "{\"id\": 2, \"name\": \"Al Pacino\", \"birthDate\": \"1940-04-25\", \"nationality\": \"American\"}";
        JSONObject actorObject = (JSONObject) parser.parse(actorString);
        dataManager.addActor(actorObject);

        String actorNationUpdateString = "{\"id\": 2, \"name\": \"Al Pacino\", \"birthDate\": \"1940-04-25\", \"nationality\": \"Italian\"}";
        JSONObject actorUpdateObject = (JSONObject) parser.parse(actorNationUpdateString);
        dataManager.addActor(actorUpdateObject);
        assertEquals(dataManager.actors.get(0).nationality , "Italian");
        assertTrue( (dataManager.actors.size() == 1));
    }

    @Test
    void addNewMovieTest() throws ParseException {
        dataManager.actors.add(new Actor(1,"","",""));
        dataManager.actors.add(new Actor(2,"","",""));

        String movieString = "{\"id\": 1, \"name\": \"The Godfather\", \"summary\": \"The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.\", \"releaseDate\": \"1972-03-14\", \"director\": \"Francis Ford Coppola\", \"writers\": [\"Mario Puzo\", \"Francis Ford Coppola\"], \"genres\": [\"Crime\", \"Drama\"], \"cast\": [1, 2], \"imdbRate\": 9.2, \"duration\": 175, \"ageLimit\": 14}";
        JSONObject movieObject = (JSONObject) parser.parse(movieString);

        int a = dataManager.addMovie(movieObject);
        System.out.println(a);
        System.out.println(dataManager.movies);
        assertTrue(dataManager.movies.size() == 1);
    }

    @Test
    void updateMovieTest() throws ParseException {
        dataManager.actors.add(new Actor(1,"","",""));
        dataManager.actors.add(new Actor(2,"","",""));

        String movieString = "{\"id\": 1, \"name\": \"The Godfather\", \"summary\": \"The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.\", \"releaseDate\": \"1972-03-14\", \"director\": \"Francis Ford Coppola\", \"writers\": [\"Mario Puzo\", \"Francis Ford Coppola\"], \"genres\": [\"Crime\", \"Drama\"], \"cast\": [1, 2], \"imdbRate\": 9.2, \"duration\": 175, \"ageLimit\": 14}";
        JSONObject movieObject = (JSONObject) parser.parse(movieString);
        dataManager.addMovie(movieObject);

        String movieRateUpdateString = "{\"id\": 1, \"name\": \"The Godfather\", \"summary\": \"The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.\", \"releaseDate\": \"1972-03-14\", \"director\": \"Francis Ford Coppola\", \"writers\": [\"Mario Puzo\", \"Francis Ford Coppola\"], \"genres\": [\"Crime\", \"Drama\"], \"cast\": [1, 2], \"imdbRate\": 8.9, \"duration\": 175, \"ageLimit\": 14}";
        JSONObject movieUpdateObject = (JSONObject) parser.parse(movieRateUpdateString);
        dataManager.addMovie(movieUpdateObject);

        assertEquals(8.9, dataManager.movies.get(0).imdbRate);
        assertTrue(dataManager.movies.size() == 1);
    }
    @Test
    void addMovieCastDoNotExistTest() throws ParseException {
        dataManager.actors.add(new Actor(1,"","",""));

        String movieString = "{\"id\": 1, \"name\": \"The Godfather\", \"summary\": \"The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.\", \"releaseDate\": \"1972-03-14\", \"director\": \"Francis Ford Coppola\", \"writers\": [\"Mario Puzo\", \"Francis Ford Coppola\"], \"genres\": [\"Crime\", \"Drama\"], \"cast\": [1, 2], \"imdbRate\": 9.2, \"duration\": 175, \"ageLimit\": 14}";
        JSONObject movieObject = (JSONObject) parser.parse(movieString);

        int error = dataManager.addMovie(movieObject);
        assertTrue(error == -1);
        assertTrue(dataManager.movies.size() == 0);
    }

    @Test
    void addNewUserTest() throws ParseException {
        String userString = "{\"email\": \"sara@ut.ac.ir\", \"password\": \"sara1234\", \"name\": \"Sara\", \"nickname\": \"sara\", \"birthDate\": \"1998-03-11\"}";
        JSONObject userObject = (JSONObject) parser.parse(userString);
        dataManager.addUser(userObject);
        assertTrue(dataManager.users.size()==1);
    }

    @Test
    void updateUserTest() throws ParseException {
        String userString = "{\"email\": \"sara@ut.ac.ir\", \"password\": \"sara1234\", \"name\": \"Sara\", \"nickname\": \"sara\", \"birthDate\": \"1998-03-11\"}";
        JSONObject userObject = (JSONObject) parser.parse(userString);
        dataManager.addUser(userObject);

        String userNationUpdateString = "{\"email\": \"sara@ut.ac.ir\", \"password\": \"sara_4321\", \"name\": \"Sara\", \"nickname\": \"sara\", \"birthDate\": \"1998-03-11\"}";
        JSONObject userUpdateObject = (JSONObject) parser.parse(userNationUpdateString);
        dataManager.addUser(userUpdateObject);

        assertEquals(dataManager.users.get(0).password , "sara_4321");
        assertTrue( (dataManager.users.size() == 1));
    }

    @Test
    void addNewCommentTest() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String commentString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"text\": \"Interesting Movie\"}";
        JSONObject commentObject =(JSONObject) parser.parse(commentString);
        dataManager.addComment(commentObject);
        assertTrue(dataManager.movies.get(0).comments.size()==1);
    }

    @Test
    void commentWithoutUserIdTest() throws ParseException {
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String commentString = "{\"userEmail\": \"\", \"movieId\": 1, \"text\": \"Interesting Movie\"}";
        JSONObject commentObject =(JSONObject) parser.parse(commentString);
        int error = dataManager.addComment(commentObject);
        assertEquals(-1, error);
        assertTrue(dataManager.comments.size() == 0);
        assertTrue(dataManager.movies.get(0).comments.size()==0);
    }

    @Test
    void commentWithoutMovieIdTest() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));

        String commentString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"text\": \"Interesting Movie\"}";
        JSONObject commentObject =(JSONObject) parser.parse(commentString);
        int error = dataManager.addComment(commentObject);
        assertEquals(-2, error);
        assertTrue(dataManager.comments.size() == 0);
    }

    @Test
    void userNewRateMovieTest() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 7}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);
        assertEquals(7,dataManager.movies.get(0).rating);
        assertTrue(dataManager.users.get(0).ratedMovies.size() == 1);
    }

    @Test
    void userUpdateRateMovieTest() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 7}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);

        String rate2String = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 6}";
        JSONObject rate2Object = (JSONObject) parser.parse(rate2String);
        dataManager.rateMovie(rate2Object);

        assertEquals(6,dataManager.movies.get(0).rating);
        assertTrue(dataManager.users.get(0).ratedMovies.size() == 1);
    }

    @Test
    void multiUserRateHandlingWithBoundriesValueTest() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));
        dataManager.users.add(new User("sajjad@ut.ac.ir","","","",""));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

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
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 7}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        int error = dataManager.rateMovie(rateObject);
        assertEquals(-1, error);
        assertEquals(0,dataManager.movies.get(0).rating);
    }

    @Test
    void rateMovieNotFound() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));

        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 7}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        int error = dataManager.rateMovie(rateObject);
        assertEquals(-2, error);
        assertTrue(dataManager.users.get(0).ratedMovies.size() == 0);
    }

    @Test
    void rateScoreMoreThatValidTest() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 12}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);
        assertEquals(0,dataManager.movies.get(0).rating);
        assertTrue(dataManager.users.get(0).ratedMovies.size() == 0);
    }

    @Test
    void rateScoreEqualsToZeroTest() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": 0}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);
        assertEquals(0,dataManager.movies.get(0).rating);
        assertTrue(dataManager.users.get(0).ratedMovies.size() == 0);
    }

    @Test
    void rateScoreLessThatValidTest() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","",""));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String rateString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1, \"score\": -3}";
        JSONObject rateObject = (JSONObject) parser.parse(rateString);
        dataManager.rateMovie(rateObject);
        assertEquals(0,dataManager.movies.get(0).rating);
        assertTrue(dataManager.users.get(0).ratedMovies.size() == 0);
    }

    @Test
    void userAddMovieToWatchListTest() throws ParseException, java.text.ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","","2000-01-10"));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String watchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        dataManager.addToWatchList(watchListObject);
        assertTrue(dataManager.users.get(0).watchList.size() == 1);
    }

    @Test
    void addToWatchListUserNotFound() throws ParseException, java.text.ParseException{
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String watchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        int error = dataManager.addToWatchList(watchListObject);
        assertEquals(-1, error);
    }

    @Test
    void addToWatchListMovieNotFound() throws ParseException, java.text.ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","","2000-01-10"));

        String watchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        dataManager.addToWatchList(watchListObject);
        int error = dataManager.addToWatchList(watchListObject);
        assertEquals(-2, error);
        assertTrue(dataManager.users.get(0).watchList.size() == 0);
    }

    @Test
    void userUnderAgeAddMovieToWatchListTest() throws ParseException, java.text.ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","","2010-01-10"));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,15));

        String watchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        int error = dataManager.addToWatchList(watchListObject);
        assertEquals(-3, error);
        assertTrue(dataManager.users.get(0).watchList.size() == 0);
    }

    @Test
    void userAddAlreadyAddedMovieToWatchListTest() throws ParseException, java.text.ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","","2000-01-10"));
        dataManager.users.get(0).watchList.add(1L);
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String watchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject watchListObject = (JSONObject) parser.parse(watchListString);
        int error = dataManager.addToWatchList(watchListObject);
        assertEquals(-4, error);
        assertTrue(dataManager.users.get(0).watchList.size() == 1);
    }

    @Test
    void userRemoveMovieFromWatchList() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","","2000-01-10"));
        dataManager.users.get(0).watchList.add(1L);
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String removeWatchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject removeWatchListObject = (JSONObject) parser.parse(removeWatchListString);
        dataManager.removeFromWatchList(removeWatchListObject);
        assertTrue(dataManager.users.get(0).watchList.size() == 0);
    }

    @Test
    void userRemoveMovieFromWatchListNotFound() throws ParseException {
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String removeWatchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject removeWatchListObject = (JSONObject) parser.parse(removeWatchListString);
        int error = dataManager.removeFromWatchList(removeWatchListObject);
        assertEquals(-1, error);
    }

    @Test
    void userRemoveMovieFromWatchListMovieNotFound() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","","2000-01-10"));
        dataManager.users.get(0).watchList.add(1L);

        String removeWatchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject removeWatchListObject = (JSONObject) parser.parse(removeWatchListString);
        int error = dataManager.removeFromWatchList(removeWatchListObject);
        assertEquals(-2, error);
        assertTrue(dataManager.users.get(0).watchList.size() == 1);
    }

    @Test
    void userRemoveMovieFromWatchListNeverHaded() throws ParseException {
        dataManager.users.add(new User("sara@ut.ac.ir","","","","2000-01-10"));
        dataManager.users.get(0).watchList.add(2L);
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        String removeWatchListString = "{\"userEmail\": \"sara@ut.ac.ir\", \"movieId\": 1}";
        JSONObject removeWatchListObject = (JSONObject) parser.parse(removeWatchListString);
        int error = dataManager.removeFromWatchList(removeWatchListObject);
        assertEquals(-1, error);
        assertTrue(dataManager.users.get(0).watchList.size() == 1);
    }

}

