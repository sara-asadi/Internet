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

    public List<Movie> getWatchListMovies() throws IOException {
        List<Movie> movies = new ArrayList<>();
        for (Long aLong : watchList) {
            Movie movie = MovieDB.getInstance().getMovieById(aLong);
            movies.add(movie);
        }
        return movies;
    }
    public double getRecommendationScore(Movie movie) throws IOException {
        List<Movie> wlMovies = getWatchListMovies();
        List<String> genres = movie.getGenresList();
        int genre_similarity = 0;
        for (Movie wlMovie : wlMovies) {
            int same_genres = 0;
            for (String genre : genres) {
                if (wlMovie.getGenresList().contains(genre))
                    same_genres += 1;
            }
            genre_similarity += same_genres;
        }
        return 3 * genre_similarity + movie.getImdbRate() + movie.getRating();
    }
    public List<Movie> getRecommendationList() throws IOException {
        List<Movie> movies = MovieDB.getInstance().getMovies();
        List<Movie> wlMovies = getWatchListMovies();
        List<Movie> top3 = new ArrayList<>();
        for (Movie movie : movies) {
            if (wlMovies.contains(movie))
                continue;
            double score = getRecommendationScore(movie);
            if (top3.size() == 0) {
                top3.add(movie);
            }
            else if (top3.size() == 1) {
                if (getRecommendationScore(top3.get(0)) < score) {
                    top3.add(top3.get(0));
                    top3.set(0, movie);
                }
                else
                    top3.add(movie);
            }
            else if (top3.size() == 2) {
                if (getRecommendationScore(top3.get(1)) < score)
                {
                    top3.add(top3.get(1));
                    if (getRecommendationScore(top3.get(0)) > score)
                        top3.set(1, movie);
                    else {
                        top3.set(1, top3.get(0));
                        top3.set(0, movie);
                    }
                }
                else {
                    top3.add(movie);
                }
            }
            else {
                if (getRecommendationScore(top3.get(2)) < score)
                {
                    top3.remove(top3.get(2));
                    if (getRecommendationScore(top3.get(1)) < score)
                    {
                        top3.add(top3.get(1));
                        if (getRecommendationScore(top3.get(0)) > score)
                            top3.set(1, movie);
                        else {
                            top3.set(1, top3.get(0));
                            top3.set(0, movie);
                        }
                    }
                    else {
                        top3.add(movie);
                    }
                }
            }
        }
        return top3;
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

    public void voteComment(long movie_id, int comment_id, String vote) throws IOException {
        Movie movie = MovieDB.getInstance().getMovieById(movie_id);
        Comment comment = movie.getComment(comment_id);
        Set s = ratedComments.entrySet();
        boolean dup = false;
        for (Object o : s) {
            Map.Entry ratedComments = (Map.Entry) o;
            if (ratedComments.getKey().equals(comment_id)) {
                dup = true;
                String prevVote = (String) ratedComments.getValue();
                if (!Objects.equals(prevVote, vote)) {
                    if (Objects.equals(vote, "like")) {
                        comment.like();
                        comment.unDisLike();
                    } else if (Objects.equals(vote, "dislike")) {
                        comment.disLike();
                        comment.unLike();
                    }
                }
            }
        }
        if (dup) {
            ratedComments.replace(comment_id, vote);
            movie.updateComment(comment_id, comment);
        } else {
            ratedComments.put(comment_id, vote);
            if (Objects.equals(vote, "like"))
                comment.like();
            else if (Objects.equals(vote, "dislike"))
                comment.disLike();
            movie.updateComment(comment_id, comment);
        }
    }
}