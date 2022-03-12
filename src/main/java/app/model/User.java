package app.model;

import app.db.CommentDB;
import app.db.MovieDB;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public List<Long> getWatchList() {
        return watchList;
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
    public long rateMovie(long movie_id, long rate) {

        Set s = ratedMovies.entrySet();
        for (Object o : s) {
            Map.Entry ratedMovie = (Map.Entry) o;
            if (ratedMovie.getKey().equals(movie_id)) {
                long prevRate = (long) ratedMovie.getValue();
                ratedMovie.setValue(rate);
                return prevRate;
            }
        }
        ratedMovies.put(movie_id, rate);
        return 0;
    }

    public Map getRatedMovies() {
        return ratedMovies;
    }

    public void voteComment(long comment_id, long vote) throws IOException {
        Set comments = ratedComments.entrySet();
        for (Object o : comments) {
            Map.Entry comment = (Map.Entry) o;
            if (comment.getKey().equals(comment_id))
                if (vote == 0 && comment.getValue().equals(1)) {
                    comment.setValue(vote);
                    CommentDB.getInstance().getCommentById(comment_id).unLike();
                } else if (vote == 0 && comment.getValue().equals(-1)) {
                    comment.setValue(vote);
                    CommentDB.getInstance().getCommentById(comment_id).unDisLike();
                } else if (vote == 1 && comment.getValue().equals(-1)) {
                    comment.setValue(vote);
                    CommentDB.getInstance().getCommentById(comment_id).like();
                    CommentDB.getInstance().getCommentById(comment_id).unDisLike();
                } else if (vote == 1 && comment.getValue().equals(0)) {
                    comment.setValue(vote);
                    CommentDB.getInstance().getCommentById(comment_id).like();
                } else if (vote == -1 && comment.getValue().equals(1)) {
                    comment.setValue(vote);
                    CommentDB.getInstance().getCommentById(comment_id).disLike();
                    CommentDB.getInstance().getCommentById(comment_id).unLike();
                } else if (vote == -1 && comment.getValue().equals(0)) {
                    comment.setValue(vote);
                    CommentDB.getInstance().getCommentById(comment_id).disLike();
                }
            MovieDB.getInstance().getMovieById(CommentDB.getInstance().getCommentById(comment_id).getMovieId()).updateComment(comment_id, CommentDB.getInstance().getCommentById(comment_id));
            return;
        }
        if (vote != 0) {
            ratedComments.put(comment_id, vote);
            if (vote == 1)
                CommentDB.getInstance().getCommentById(comment_id).like();
            if (vote == -1)
                CommentDB.getInstance().getCommentById(comment_id).disLike();
            MovieDB.getInstance().getMovieById(CommentDB.getInstance().getCommentById(comment_id).getMovieId()).addComment(CommentDB.getInstance().getCommentById(comment_id));
        }
        return;
    }
}