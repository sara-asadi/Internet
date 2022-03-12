package app.db;

import app.model.Movie;
import app.model.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

public class UserDB {
    private static UserDB instance;
    public static List<User> users;
    public static User currentUser;
    private UserDB() throws IOException {
        users = getUsersArray("http://138.197.181.131:5000/api/users");
    }
    public static UserDB getInstance() throws IOException {
        if (instance == null)
            instance = new UserDB();
        return instance;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser(){
        return currentUser;
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

    public User getUserByEmail(String email) {
        for (User user : users)
            if (Objects.equals(user.getEmail(), email))
                return user;
        return null;
    }

    public List<Movie> getWatchList(String email) throws IOException {
        User user = getUserByEmail(email);
        List<Movie> wList = new ArrayList<>();
        if(user == null){
            return wList;
        }
        for (Long w : user.getWatchList()) {
            Movie m = MovieDB.getInstance().getMovieById(w);
            wList.add(m);
        }
        return wList;
    }

    public boolean addToWatchList(String email, long movie_id) throws ParseException, IOException {
        User user = getUserByEmail(email);
        Movie movie = MovieDB.getInstance().getMovieById(movie_id);
        if (user.getAge() > movie.getAgeLimit()) {
            user.addWatchList(movie_id);
            return true;
        }
        else return false;
    }

    public boolean rateMovie(String email, long movie_id, long rate) throws IOException {
        User user = getUserByEmail(email);
        Movie ratedMovie = MovieDB.getInstance().getMovieById(movie_id);
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
    public boolean voteComment(String email, long comment_id, long vote) throws IOException {
        User user = getUserByEmail(email);
        if (vote == 0 || vote == 1 || vote == -1) {
            user.voteComment(comment_id, vote);
            return true;
        }
        else return false;
    }

    public boolean removeFromWatchList(String email, long movie_id) {
        User user = getUserByEmail(email);
        if (user.isInWatchList(movie_id)) {
            user.removeWatchList(movie_id);
            return true;
        }
        else return false;
    }
}

