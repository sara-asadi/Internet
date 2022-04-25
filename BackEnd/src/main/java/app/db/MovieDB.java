package app.db;

import app.model.Movie;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class MovieDB {
    private static MovieDB instance;
    public static List<Movie> movies;
    public static List<Movie> filtered;

    private MovieDB() throws IOException {
        movies = getMoviesArray("http://138.197.181.131:5000/api/v2/movies");
        movies = movies.stream().sorted(Comparator.comparing(Movie::getImdbRate).reversed()).collect(Collectors.toList());
        filtered = movies;
        for (Movie movie : movies) {
            System.out.println(movie.getName());
            System.out.println(movie.getReleaseCDate());
        }
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
    public List<Movie> getMovies(){return movies;}
    public List<Movie> getMoviesFiltered(){return filtered;}
    public void SortByImdbRate() {
        movies = movies.stream().sorted(Comparator.comparing(Movie::getImdbRate).reversed()).collect(Collectors.toList());
        filtered = filtered.stream().sorted(Comparator.comparing(Movie::getImdbRate).reversed()).collect(Collectors.toList());
    }
    public void SortByDate() {
        movies = movies.stream().sorted(Comparator.comparing(Movie::getReleaseCDate).reversed()).collect(Collectors.toList());
        filtered = filtered.stream().sorted(Comparator.comparing(Movie::getReleaseCDate).reversed()).collect(Collectors.toList());
    }
    public void FilterMovies(String searchKey) {
        List<Movie> matched = new ArrayList<>();
        for (Movie movie : movies)
            if (movie.getName().contains(searchKey))
                matched.add(movie);
        filtered = matched;
    }
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
    public List<Movie> getMoviesByGenre(String genre) {
        List<Movie> genreMovies = new ArrayList<>();
        for (Movie movie : movies)
            if (movie.getGenres().contains(genre))
                genreMovies.add(movie);
        return genreMovies;
    }

    public void clearSearchFilter() {
        filtered = movies;
    }
}
