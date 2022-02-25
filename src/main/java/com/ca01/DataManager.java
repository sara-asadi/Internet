package com.ca01;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataManager {
    List<Actor> actors;
    List<Movie> movies;
    List<User> users;
    List<Comment> comments;

    public DataManager(){
        actors = new ArrayList<>();
        movies = new ArrayList<>();
        users = new ArrayList<>();
        comments = new ArrayList<>();
    }
    private int findActor(long id) {
        for(int i = 0; i < actors.size(); i++)
            if(actors.get(i).id == id)
                return i;
        return -1;
    }
    private int findMovie(long id) {
        for(int i = 0; i < movies.size(); i++)
            if(movies.get(i).id == id)
                return i;
        return -1;
    }
    private int findUser(String email) {
        for(int i = 0; i < users.size(); i++)
            if(Objects.equals(users.get(i).email, email))
                return i;
        return -1;
    }
    private int findComment(long id) {
        for(int i = 0; i < comments.size(); i++)
            if(comments.get(i).id == id)
                return i;
        return -1;
    }
    public Actor getActorById(long actorId) {
        return actors.get(findActor(actorId));
    }
    public void isDate(String str) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
    }

    //try {;} catch (NullPointerException e) {return -1;}
    //try {isDate(birthDate);}catch (ParseException e){return -1;}
    //try {id = (long) data.get("id");} catch (NullPointerException e){return -1;}
    //(=(String) data.get("")).equals("");

    public int addActor(JSONObject data) throws ParseException {
        String birthDate=(String) data.get("birthDate");
        try {isDate(birthDate);}catch (ParseException e){return -1;}

        Actor newActor = new Actor((long) data.get("id"), (String) data.get("name"), birthDate, (String) data.get("nationality"));
        int idx = findActor((long) data.get("id"));
        if (idx != -1) actors.set(idx, newActor);
        else actors.add(newActor);
        return 0;
    }
    public int addMovie(JSONObject data) {
        String releaseDate=(String) data.get("releaseDate");
        try {isDate(releaseDate);}catch (ParseException e){return -1;}

        for (Long x : (List<Long>) data.get("cast")) if (findActor(x) == -1) return -2;
        Movie newMovie = new Movie((long) data.get("id"),(String) data.get("name"), (String) data.get("summary"), releaseDate, (String) data.get("director"), (List<String>) data.get("writers"),(List<String>) data.get("genres"), (List<Long>) data.get("cast"), (double) data.get("imdbRate"), (long) data.get("duration"), (long) data.get("ageLimit"));
        int idx = findMovie((long) data.get("id"));
        if (idx != -1) movies.set(idx, newMovie);
        else movies.add(newMovie);
        return 0;
    }
    public int addUser(JSONObject data) {
        String birthDate=(String) data.get("birthDate");
        try {isDate(birthDate);}catch (ParseException e){return -1;}

        User newUser = new User((String) data.get("email"), (String) data.get("password"), (String) data.get("nickname"), (String) data.get("name"), birthDate);
        int idx = findUser((String) data.get("email"));
        if (idx != -1) users.set(idx, newUser);
        else users.add(newUser);
        return 0;
    }
    public int addComment(JSONObject data) {
        String userEmail = (String) data.get("userEmail");
        if (findUser(userEmail) == -1) return -1;

        int movieIdx = findMovie((long) data.get("movieId"));
        if (movieIdx == -1) return -2;
        Movie movie = movies.get(movieIdx);
        int id = comments.size()+1;
        Comment comment = new Comment(userEmail, movie.id, (String)data.get("text"), id);
        comments.add(comment);
        movie.comments.add(comment);
        return id;
    }
    public int rateMovie(JSONObject data) {
        long score = (long) data.get("score");
        if (score>10 || score<1)
            return -3;

        String userEmail;
        (userEmail = (String) data.get("userEmail")).equals("");
        long movieId = (long) data.get("movieId");
        int userIdx = findUser(userEmail);
        int movieIdx = findMovie(movieId);

        if (userIdx == -1)
            return -1;
        if (movieIdx == -1)
            return -2;

        Movie movie = movies.get(movieIdx);
        User user = users.get(userIdx);

        for (List<Long> list:user.ratedMovies)
            if (list.get(0) == movieId){
                long prevScore = list.get(1);
                long total = ((long) (movie.ratingCount * movie.rating))-prevScore;
                movie.rating = (double) (total+score)/(movie.ratingCount);
                list.set(1, score);
                return 0;
            }

        List<Long> temp = new ArrayList<>();
        temp.add(movieId);
        temp.add(score);
        user.ratedMovies.add(temp);
        if (movie.rating == null) {
            movie.rating = Double.valueOf(score);
            movie.ratingCount += 1;
        }
        else {
            movie.rating = ((movie.rating * movie.ratingCount) + score) / (movie.ratingCount + 1);
            movie.ratingCount += 1;
        }
        return 0;
    }
    public int voteComment(JSONObject data) {
        long vote = (long) data.get("vote");
        if (vote != 0 && vote != 1 && vote != -1)
            return -3;

        String userEmail;
        (userEmail = (String) data.get("userEmail")).equals("");
        long commentId = (long) data.get("commentId");
        int userIdx = findUser(userEmail);
        int commentIdx = findComment(commentId);

        if (userIdx == -1)
            return -1;
        if (commentIdx == -1)
            return -2;

        User user = users.get(userIdx);
        Comment comment = comments.get(commentIdx);
        long movieId = comment.movieId;

        for (List<Long> list:user.ratedComments)
            if (list.get(0) == commentId){
                long prevVote = list.get(1);
                if (vote == 0){
                    if (prevVote == 1) comment.likes -= 1;
                    else if (prevVote == -1) comment.disLikes -= 1;
                    list.set(0, (long) -1);
                }
                else if (vote == 1 && prevVote == -1) {
                    comment.likes += 1;
                    comment.disLikes -= 1;
                    list.set(1, (long) 1);
                }
                else if (vote == -1 && prevVote == 1) {
                    comment.likes -= 1;
                    comment.disLikes += 1;
                    list.set(1, (long) -1);
                }
                movies.get(findMovie(movieId)).updateComment(comment, commentId);
                return 0;
            }
        if (vote != 0) {
            List<Long> temp = new ArrayList<>();
            temp.add(commentId);
            temp.add(vote);
            user.ratedComments.add(temp);
            if (vote == 1) comment.likes += 1;
            if (vote == -1) comment.disLikes += 1;
            movies.get(findMovie(movieId)).comments.add(comment);
        }
        return 0;
    }
    public int addToWatchList(JSONObject data) throws ParseException {
        String userEmail;
        (userEmail = (String) data.get("userEmail")).equals("");
        int userIdx = findUser(userEmail);
        if (userIdx == -1)
            return -1;
        User user = users.get(userIdx);

        long movieId = (long) data.get("movieId");
        int movieIdx = findMovie(movieId);
        if (movieIdx == -1)
            return -2;
        Movie movie = movies.get(movieIdx);

        if (user.getAge() < movie.ageLimit)
            return -3;

        if (user.isInWatchList(movieId))
            return -4;

        return user.addWatchList(movieId);
    }
    public int removeFromWatchList(JSONObject data) {
        long movieId = (long) data.get("movieId");
        int movieIdx = findMovie(movieId);
        if (movieIdx == -1)
            return -2;

        String userEmail;
        (userEmail = (String) data.get("userEmail")).equals("");
        int userIdx = findUser(userEmail);
        if (userIdx == -1)
            return -1;
        User user = users.get(userIdx);
        if (!user.isInWatchList(movieId))
            return -1;

        return user.removeWatchList(movieId);
    }
    public JSONObject getMoviesList() {
        JSONObject obj = new JSONObject();
        JSONArray movieList = new JSONArray();
        for (Movie movie : movies)
            movieList.add(movie.getJsonObject());
        obj.put("MoviesList", movieList);
        return obj;
    }
    public JSONObject getMovieById(JSONObject data) {
        long movieId = (long) data.get("movieId");
        int movieIdx = findMovie(movieId);
        if (movieIdx == -1)
            return null;
        Movie movie = movies.get(movieIdx);
        return movie.getJsonObject(this);
    }
    public JSONObject getMovieByGenre(JSONObject data) {
        String genre;
        (genre = (String) data.get("genre")).equals("");
        JSONObject obj = new JSONObject();
        JSONArray movieList = new JSONArray();
        for (Movie movie : movies)
            if (movie.hasGenre(genre))
                movieList.add(movie.getJsonObject());
        obj.put("MoviesListByGenre", movieList);
        return obj;
    }
    public JSONObject getWatchList(JSONObject data) {
        String userEmail;
        (userEmail = (String) data.get("userEmail")).equals("");
        JSONObject obj = new JSONObject();
        JSONArray watchList = new JSONArray();
        int userIdx = findUser(userEmail);
        if (userIdx == -1)
            return null;
        User user = users.get(userIdx);
        for (Long movieId : user.watchList) {
            watchList.add(movies.get(findMovie(movieId)).getJsonObject());
        }
        obj.put("WatchList", watchList);
        return obj;
    }
}
