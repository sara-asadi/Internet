package app.movie;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieDBTest {
    protected MovieDB md;
    protected Movie movie;

    @BeforeEach
    void setUp() throws IOException {
        md = new MovieDB();
    }
    @Test
    void getMovieByYearInAYear() throws ParseException {
        assertEquals(1,md.getMoviesByYear("1999","1999").get(0).getId());
    }

    @Test
    void getMovieByYearIn5Years() throws ParseException {

        List<Movie> movieListByYear = md.getMoviesByYear("2000","2005");
        assertEquals(2, movieListByYear.size());
        assertTrue(md.getMoviesByYear("2000","2005").stream().anyMatch(p->p.getId() == 6));
        assertTrue(md.getMoviesByYear("2000","2005").stream().anyMatch(p->p.getId() == 7));
    }

    @Test
    void getMovieByYearNoMovieExist() throws ParseException {
        assertEquals(new ArrayList<>(), md.getMoviesByYear("2011","2015"));
    }

}