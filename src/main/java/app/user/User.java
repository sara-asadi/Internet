package app.user;

import app.movie.Movie;
import app.movie.MovieDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static app.Main.commentDB;
import static app.Main.movieDB;

public class User {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String birthDate;
    private Map ratedMovies;
    private Map ratedComments;
    private List<Long> watchList;

    public User(){
        ratedMovies = new HashMap();
        ratedComments = new HashMap();
        watchList = new ArrayList<>();
    }
    public User(String email_, String password_, String nickname_, String name_, String birthDate_){
        (email = email_).equals("");
        (password = password_).equals("");
        (nickname = nickname_).equals("");
        (name = name_).equals("");
        (birthDate = birthDate_).equals("");
        ratedMovies = new HashMap();
        ratedComments = new HashMap();
        watchList = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getNickname() {
        return nickname;
    }

    public List<Movie> getWatchList() {
        List<Movie> wList = new ArrayList<>();
        for (Long w : watchList) {
            Movie m = movieDB.getMovieById(w);
            wList.add(m);
        }
        return wList;
    }

    public int getAge() throws ParseException {
        Date bDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        Date cDate = new Date();
        return cDate.getYear()-bDate.getYear();
    }
    public boolean isInWatchList(long movieId){
        for (Long id: watchList)
            if (id == movieId)
                return true;
        return false;
    }
    public int addWatchList(long movieId){
        if (isInWatchList(movieId))
            return 1;
        watchList.add(movieId);
        return 0;
    }
    public int removeWatchList(long movieId){
        watchList.remove(movieId);
        return 0;
    }
    public void rateMovie(long movie_id, long rate) {
        Movie movie = movieDB.getMovieById(movie_id);
        Set s = ratedMovies.entrySet();
        for (Object o : s) {
            Map.Entry ratedMovie = (Map.Entry) o;
            if (ratedMovie.getKey().equals(movie_id)) {
                long prevRate = (long) ratedMovie.getValue();
                long total = ((long) (movie.getRating() * movie.getRatingCount())) - prevRate;
                movie.setRating((double) (total + rate) / (movie.getRatingCount()));
                ratedMovie.setValue(rate);
                return;
            }
        }
        ratedMovies.put(movie_id, rate);
        movie.incRatingCount();
        movie.setRating(((movie.getRating() * movie.getRatingCount()) + rate) / (movie.getRatingCount()));
    }

    public void voteComment(long comment_id, long vote) {
        Set comments = ratedComments.entrySet();
        for (Object o : comments) {
            Map.Entry comment = (Map.Entry) o;
            if (comment.getKey().equals(comment_id))
                if (vote == 0 && comment.getValue().equals(1)) {
                    comment.setValue(vote);
                    commentDB.getCommentById(comment_id).unLike();
                } else if (vote == 0 && comment.getValue().equals(-1)) {
                    comment.setValue(vote);
                    commentDB.getCommentById(comment_id).unDisLike();
                } else if (vote == 1 && comment.getValue().equals(-1)) {
                    comment.setValue(vote);
                    commentDB.getCommentById(comment_id).like();
                    commentDB.getCommentById(comment_id).unDisLike();
                } else if (vote == 1 && comment.getValue().equals(0)) {
                    comment.setValue(vote);
                    commentDB.getCommentById(comment_id).like();
                } else if (vote == -1 && comment.getValue().equals(1)) {
                    comment.setValue(vote);
                    commentDB.getCommentById(comment_id).disLike();
                    commentDB.getCommentById(comment_id).unLike();
                } else if (vote == -1 && comment.getValue().equals(0)) {
                    comment.setValue(vote);
                    commentDB.getCommentById(comment_id).disLike();
                }
            movieDB.getMovieById(commentDB.getCommentById(comment_id).getMovieId()).updateComment(comment_id, commentDB.getCommentById(comment_id));
            return;
        }
        if (vote != 0) {
            ratedComments.put(comment_id, vote);
            if (vote == 1)
                commentDB.getCommentById(comment_id).like();
            if (vote == -1)
                commentDB.getCommentById(comment_id).disLike();
            movieDB.getMovieById(commentDB.getCommentById(comment_id).getMovieId()).addComment(commentDB.getCommentById(comment_id));
        }
        return;
    }
}