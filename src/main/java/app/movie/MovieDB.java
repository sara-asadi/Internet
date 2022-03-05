package app.movie;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class MovieDB {

    public static List<Movie> movies;

    public MovieDB() throws IOException {
        movies = getMoviesArray("http://138.197.181.131:5000/api/movies");
    }
    public List<Movie> getMoviesArray(String apiAddress) throws IOException{
        List<Movie> Movies = new ArrayList<>();
        URL url = new URL(apiAddress);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int responsecode = con.getResponseCode();
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();
            Gson gson = new Gson();
            Movie[] movieArray = gson.fromJson(inline, Movie[].class);
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

    public List<Movie> getMoviesByYear(String start_year, String end_year) throws ParseException {
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
