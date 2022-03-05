package app.user;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import app.Comment.Comment;
import app.movie.Movie;
import app.movie.MovieDB;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import static app.Main.commentDB;
import static app.Main.movieDB;

public class UserDB {

    public static List<User> users;
    public static MovieDB movieDB;
    public UserDB(MovieDB _movieDB) throws IOException {
        users = getUsersArray("http://138.197.181.131:5000/api/users");
        movieDB = _movieDB;
    }
    public List<User> getUsersArray(String apiAddress) throws IOException{
        List<User> Users = new ArrayList<>();
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
            Gson gson = new Gson();
            User[] userArray = gson.fromJson(inline, User[].class);
            Users = Arrays.asList(userArray);
        }

        return Users;
    }
    public static Iterable<User> getAllUsers() {
        return users;
    }

    public User getUserByEmail(String email) {
        for (User user : users)
            if (Objects.equals(user.getEmail(), email))
                return user;
        return null;
    }

    public List<Movie> getWatchList(String email) {
        User user = getUserByEmail(email);
        List<Movie> wList = new ArrayList<>();
        if(user == null){
            return wList;
        }
        for (Long w : user.getWatchList()) {
            Movie m = movieDB.getMovieById(w);
            wList.add(m);
        }
        return wList;
    }

    public boolean addToWatchList(String email, long movie_id) throws ParseException {
        User user = getUserByEmail(email);
        Movie movie = movieDB.getMovieById(movie_id);
        if (user.getAge() > movie.getAgeLimit()) {
            user.addWatchList(movie_id);
            return true;
        }
        else return false;
    }

    public boolean rateMovie(String email, long movie_id, long rate) {
        User user = getUserByEmail(email);
        Movie ratedMovie = movieDB.getMovieById(movie_id);
        if (rate > 0 && rate <= 10 && user != null && ratedMovie != null) {
            long prevRate = user.rateMovie(movie_id, rate);
            long total = ((long) (ratedMovie.getRating() * ratedMovie.getRatingCount())) - prevRate;
            if(prevRate == 0){
                ratedMovie.incRatingCount();
            }
            ratedMovie.setRating((double) (total + rate) / (ratedMovie.getRatingCount()));
            return true;
        }
        else return false;
    }
    public boolean voteComment(String email, long comment_id, long vote) {
        User user = getUserByEmail(email);
        Comment comment = commentDB.getCommentById(comment_id);
        if (vote == 0 || vote == 1 || vote == -1) {
            user.voteComment(comment_id, vote);
            return true;
        }
        else return false;
    }

    public boolean removeFromWatchList(String email, long movie_id) {
        User user = getUserByEmail(email);
        Movie movie = movieDB.getMovieById(movie_id);
        if (user.isInWatchList(movie_id)) {
            user.removeWatchList(movie_id);
            return true;
        }
        else return false;
    }
}
