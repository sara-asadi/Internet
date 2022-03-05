package app.user;

import app.movie.MovieDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class UserDBRateTest {

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
    void userNewRateMovieTest(){
       ub.rateMovie(user.getEmail(), 1,7);
        assertEquals(7, mb.getMovieById(1).getRating());
        assertEquals(1, ub.getUserByEmail(user.getEmail()).getRatedMovies().size());
    }

    @Test
    void userUpdateRateMovieTest(){
        ub.rateMovie(user.getEmail(), 1,7);
        ub.rateMovie(user.getEmail(), 1,5);
        assertEquals(5, mb.getMovieById(1).getRating());
        assertEquals(1, ub.getUserByEmail(user.getEmail()).getRatedMovies().size());
    }

    @Test
    void multiUserRateHandlingWithBoundariesValueTest(){
        ub.rateMovie("sara@ut.ac.ir", 1,1);
        ub.rateMovie("sajjad@ut.ac.ir", 1,10);
        assertEquals(5.5, mb.getMovieById(1).getRating());
    }
    @Test
    void rateUserNotFountTest(){
        boolean done = ub.rateMovie("sara2@ut.ac.ir", 1,6);
        assertEquals(false, done);
    }

    @Test
    void rateMovieNotFound(){
        boolean done = ub.rateMovie(user.getEmail(), 20,3);
        assertEquals(false, done);
    }

    @Test
    void rateScoreMoreThatValidTest(){
        boolean done = ub.rateMovie(user.getEmail(), 2,20);
        assertEquals(false, done);
    }

    @Test
    void rateScoreEqualsToZeroTest(){
        boolean done = ub.rateMovie(user.getEmail(), 3,0);
        assertEquals(false, done);
    }

    @Test
    void rateScoreLessThatValidTest(){
        boolean done = ub.rateMovie(user.getEmail(), 10,-5);
        assertEquals(false, done);
    }
}
