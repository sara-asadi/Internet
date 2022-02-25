package com.ca01;

import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    protected User user;

    @BeforeEach
    void setUp(){
        user = new User("sara@ut.ac.ir","sara1234", "Sara","sara","1998-03-11");
    }

    @Test
    void getAgeTest() throws ParseException {
        assertEquals(24,user.getAge());
    }

    @Test
    void movieIsInWatchList(){
        user.watchList.add(1L);
        assertEquals(true, user.isInWatchList(1L));
    }

    @Test
    void movieIsNotInWatchList(){
        assertEquals(false, user.isInWatchList(1L));

    }
}