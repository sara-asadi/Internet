package ir.ac.ut.iemdb.model;

public class WatchList {
    String userEmail;
    long movieId;

    public WatchList(){}

    public WatchList(String userEmail, long movieId){
        this.userEmail = userEmail;
        this.movieId = movieId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

}
