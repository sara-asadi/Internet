package com.ca01;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        String actor1String = "{\"id\": 1, \"name\": \"Marlon Brando\", \"birthDate\": \"1924-04-03\", \"nationality\": \"American\"}";
        JSONObject actor1Object = (JSONObject) parser.parse(actor1String);
        String actor2String = "{\"id\": 2, \"name\": \"Al Pacino\", \"birthDate\": \"1940-04-25\", \"nationality\": \"American\"}";
        JSONObject actor2Object = (JSONObject) parser.parse(actor2String);

        dataManager.addActor(actor1Object);
        dataManager.addActor(actor2Object);

        String movieString = "{\"id\": 1, \"name\": \"The Godfather\", \"summary\": \"The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.\", \"releaseDate\": \"1972-03-14\", \"director\": \"Francis Ford Coppola\", \"writers\": [\"Mario Puzo\", \"Francis Ford Coppola\"], \"genres\": [\"Crime\", \"Drama\"], \"cast\": [1, 2], \"imdbRate\": 9.2, \"duration\": 175, \"ageLimit\": 14}";
        JSONObject movieObject = (JSONObject) parser.parse(movieString);

        int a = dataManager.addMovie(movieObject);
        System.out.println(a);
        System.out.println(dataManager.movies);
        assertTrue(dataManager.movies.size() == 1);
    }

    @Test
    void updateMovieTest() throws ParseException {
        String actor1String = "{\"id\": 1, \"name\": \"Marlon Brando\", \"birthDate\": \"1924-04-03\", \"nationality\": \"American\"}";
        JSONObject actor1Object = (JSONObject) parser.parse(actor1String);
        String actor2String = "{\"id\": 2, \"name\": \"Al Pacino\", \"birthDate\": \"1940-04-25\", \"nationality\": \"American\"}";
        JSONObject actor2Object = (JSONObject) parser.parse(actor2String);

        dataManager.addActor(actor1Object);
        dataManager.addActor(actor2Object);

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
        String actor1String = "{\"id\": 1, \"name\": \"Marlon Brando\", \"birthDate\": \"1924-04-03\", \"nationality\": \"American\"}";
        JSONObject actor1Object = (JSONObject) parser.parse(actor1String);

        dataManager.addActor(actor1Object);

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


}

