package app.movie;

import app.book.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MovieDao {

    public static List<Movie> movies;
    public MovieDao() throws IOException, ParseException {
        movies = getMoviesArray("http://138.197.181.131:5000/api/movies");
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
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Movies = Arrays.asList(mapper.readValue(inline, Movie[].class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Movies;
    }

    public static Iterable<Movie> getAllMovies() {
        return movies;
    }

    public static Movie getMovieById(long id) {
        return movies.stream().filter(b -> b.id== id).findFirst().orElse(null);
    }
}
