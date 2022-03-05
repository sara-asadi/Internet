package app.user;

import app.Main;
import app.movie.Movie;
import app.movie.MovieDB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDBTest {
    protected UserDB ub;
    protected MovieDB mb;
    protected User user;


    @BeforeEach
    void setUp() throws IOException {
        mb = new MovieDB();
        ub = new UserDB(mb);
        user = new User("sara@ut.ac.ir", "sara1234", "sara", "Sara",  "1998-03-11");
    }

    @Test
    void getUserByEmailWithValidEmail(){
        User userActual = ub.getUserByEmail("sara@ut.ac.ir");
        Assert.assertTrue(new ReflectionEquals(user).matches(userActual));
    }

    @Test
    void getUserByEmailWithInvalidEmail(){
        User userActual = ub.getUserByEmail("sara2@ut.ac.ir");
       assertNull(userActual);
    }

    @Test
    void getWatchListNotEmpty() throws ParseException {

        List<Movie> watchList = new ArrayList<>();
        watchList.add(mb.getMovieById(1));

        ub.addToWatchList("sara@ut.ac.ir",1);
        assertEquals(watchList, ub.getWatchList(user.getEmail()));

    }

    @Test
    void getWatchListEmpty(){
        List<Movie> watchList = new ArrayList<>();
        assertEquals(watchList, ub.getWatchList(user.getEmail()));
    }

    @Test
    void getWatchListInvalidEmail(){
        List<Movie> watchList = new ArrayList<>();
        assertEquals(watchList, ub.getWatchList("sara2@ut.ac.ir"));
    }
}