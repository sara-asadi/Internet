package ir.ac.ut.iemdb.model;

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

    public User() {
    }

    public User(String email_, String password_, String nickname_, String name_, String birthDate_) {
        (email = email_).equals("");
        (password = password_).equals("");
        (nickname = nickname_).equals("");
        (name = name_).equals("");
        (birthDate = birthDate_).equals("");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /*
    public List<Integer> getWatchList() {
        return watchList;
    }

    public List<Movie> getWatchListMovies() throws IOException {
        List<Movie> movies = new ArrayList<>();
        for (Integer aInteger : watchList) {
            Movie movie = MovieDB.getInstance().getMovieById(aInteger);
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
    public boolean isInWatchList(int movieId){
        for (Integer id: watchList)
            if (id == movieId)
                return true;
        return false;
    }
    public int addWatchList(int movieId){
        if (isInWatchList(movieId))
            return 1;
        watchList.add(movieId);
        return 0;
    }
    public int removeWatchList(int movieId){
        watchList.remove(movieId);
        return 0;
    }
    public int rateMovie(int movie_id, int rate) {

        Set s = ratedMovies.entrySet();
        for (Object o : s) {
            Map.Entry ratedMovie = (Map.Entry) o;
            if (ratedMovie.getKey().equals(movie_id)) {
                int prevRate = (int) ratedMovie.getValue();
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

    public void voteComment(int movie_id, int comment_id, String vote) throws IOException {
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
     */
}