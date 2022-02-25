package com.ca01;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public int addActor(JSONObject data){
        Actor newActor = new Actor((long) data.get("id"), (String) data.get("name"), (String) data.get("birthDate"), (String) data.get("nationality"));

        int idx = findActor((long)data.get("id"));
        if(idx != -1) {
            actors.set(idx, newActor);
            return 0;
        }
        actors.add(newActor);
        //System.out.println(Arrays.toString(actors.toArray()));
        return 0;
    }
    public int addMovie(JSONObject data) {
        List<Long> cast = (List<Long>) data.get("cast");

        for (Long x: cast)
            if(findActor(x) == -1) {
                System.out.println(x);
                return -1;
            }

        Movie newMovie = new Movie((long) data.get("id"), (String) data.get("name"), (String) data.get("summary"), (String) data.get("releaseDate"), (String) data.get("director"), (List<String>) data.get("writers"), (List<String>) data.get("genres"), cast, (double) data.get("imdbRate"), (long) data.get("duration"), (long) data.get("ageLimit"));

        int idx = findMovie((long) data.get("id"));
        if(idx != -1) {
            movies.set(idx, newMovie);
            return 0;
        }
        movies.add(newMovie);
        return 0;
    }
    public int addUser(JSONObject data) {
        User newUser = new User((String) data.get("email"), (String) data.get("password"), (String)data.get("nickname"), (String) data.get("name"), (String) data.get("birthDate"));

        int idx = findUser((String) data.get("email"));

        if(idx != -1) {
            users.set(idx, newUser);
            return 0;
        }
        users.add(newUser);

        return 0;
    }
    public int addComment(JSONObject data) {
        String userEmail = (String) data.get("userEmail");
        if (findUser(userEmail) == -1)
            return -1;

        long movieId = (long) data.get("movieId");
        if (findMovie(movieId) == -1)
            return -2;

        int id = comments.size()+1;
        Comment comment = new Comment(userEmail, movieId, (String)data.get("text"), id);
        comments.add(comment);

        return id;
    }
    public int rateMovie(JSONObject data) {
        long score = (long) data.get("score");
        if (score>10 || score<1)
            return -3;

        String userEmail = (String) data.get("userEmail");
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
                long prevscore = list.get(1);
                long total = ((long) (movie.ratingCount * movie.rating))-prevscore;
                movie.rating = (double) (total+score)/(movie.ratingCount);
                list.set(1, score);
                return 0;
            }

        List<Long> temp = new ArrayList<>();
        temp.add(movieId);
        temp.add(score);
        user.ratedMovies.add(temp);

        movie.rating = ((movie.rating * movie.ratingCount)+score)/(movie.ratingCount + 1);
        return 0;
    }
    public int voteComment(JSONObject data) {
        long vote = (long) data.get("vote");
        if (vote != 0 && vote != 1 && vote != -1)
            return -3;

        String userEmail = (String) data.get("userEmail");
        long commentId = (long) data.get("commentId");
        int userIdx = findUser(userEmail);
        int commentIdx = findComment(commentId);

        if (userIdx == -1)
            return -1;
        if (commentIdx == -1)
            return -2;

        User user = users.get(userIdx);
        Comment comment = comments.get(commentIdx);

        for (List<Long> list:user.ratedComments)
            if (list.get(0) == commentId){
                long prevVote = list.get(1);
                if (vote == 0){
                    if (prevVote == 1) comment.likes -= 1;
                    if (prevVote == -1) comment.disLikes -= 1;
                    list.set(0, (long) -1);
                    return 0;
                }
                if (vote == 1 && prevVote == -1) {
                    comment.likes += 1;
                    comment.disLikes -= 1;
                    list.set(1, (long) 1);
                    return 0;
                }
                if (vote == -1 && prevVote == 1) {
                    comment.likes -= 1;
                    comment.disLikes += 1;
                    list.set(1, (long) -1);
                    return 0;
                }
            }

        if (vote == 0)
            return 0;

        List<Long> temp = new ArrayList<>();
        temp.add(commentId);
        temp.add(vote);
        user.ratedComments.add(temp);

        if (vote == 1) comment.likes += 1;
        if (vote == -1) comment.disLikes += 1;
        return 0;
    }
    public int addToWatchList(JSONObject data) throws ParseException {
        String userEmail = (String) data.get("userEmail");
        int userIdx = findUser(userEmail);
        if (userIdx == -1)
            return -1;

        long movieId = (long) data.get("movieId");
        int movieIdx = findMovie(movieId);
        if (movieIdx == -1)
            return -2;

        Movie movie = movies.get(movieIdx);
        User user = users.get(userIdx);

        if (user.getAge() < movie.ageLimit)
            return -3;

        if (user.isInWatchList(movieId))
            return -4;

        return user.addWatchList(movieId);
    }
    public int removeFromWatchList(JSONObject data) {
        String userEmail = (String) data.get("userEmail");
        int userIdx = findUser(userEmail);
        if (userIdx == -1)
            return -1;

        long movieId = (long) data.get("movieId");
        int movieIdx = findMovie(movieId);
        if (movieIdx == -1)
            return -2;

        User user = users.get(userIdx);
        if (!user.isInWatchList(movieId))
            return -1;

        return user.removeWatchList(movieId);
    }

}
