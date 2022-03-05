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
    public UserDB() throws IOException {
        users = getUsersArray("http://138.197.181.131:5000/api/users");
    }
    public List<User> getUsersArray(String apiAddress) throws IOException{
        List<User> Users = new ArrayList<>();
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
        try {
        return user.getWatchList();
        }catch (NullPointerException e){
            List<Movie> m = new ArrayList<>();
            return m;
        }
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
        Movie movie = movieDB.getMovieById(movie_id);
        if (rate > 0 && rate <= 10) {
            user.rateMovie(movie_id, rate);
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
}
