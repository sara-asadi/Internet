package app.db;

import app.model.Movie;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MovieDB {
    private static MovieDB instance;
    public static List<Movie> movies;

    private MovieDB() throws IOException {
        movies = getMoviesArray("http://138.197.181.131:5000/api/movies");
    }
    public static MovieDB getInstance() throws IOException {
        if (instance == null)
            instance = new MovieDB();
        return instance;
    }
    public List<Movie> getMoviesArray(String apiAddress) throws IOException{
        List<Movie> Movies = new ArrayList<>();
        URL url = new URL(apiAddress);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            Gson gson = new Gson();
            Movie[] movieArray = gson.fromJson(inline.toString(), Movie[].class);
            Movies = Arrays.asList(movieArray);
        }

        return Movies;
    }
    public static Iterable<Movie> getAllMovies() {
        return movies;
    }
    public List<Movie> getMovies(){return movies;}
    public Movie getMovieById(long id) {
        return movies.stream().filter(m -> m.getId()== id).findFirst().orElse(null);
    }

    public List<Movie> getMoviesByYear(String start_year, String end_year) throws ParseException, ParseException {
        List<Movie> yearMovies = new ArrayList<>();
        for (Movie movie : movies)
            if (movie.isInPeriod(start_year, end_year))
                yearMovies.add(movie);
        return yearMovies;
    }

    public Object getMoviesByGenre(String genre) {
        List<Movie> genreMovies = new ArrayList<>();
        for (Movie movie : movies)
            if (movie.getGenres().contains(genre))
                genreMovies.add(movie);
        return genreMovies;
    }
}
