package ir.ac.ut.iemdb.model;

public class WatchList {
    String userEmail;
    int movieId;

    public WatchList(){}

    public WatchList(String userEmail, int movieId){
        this.userEmail = userEmail;
        this.movieId = movieId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

}