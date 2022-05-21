package ir.ac.ut.iemdb.model;

public class Rate {
    private int movieId;
    private String userEmail;
    private int value;

    public Rate(int movieId, String userEmail, int value) {
        this.movieId = movieId;
        this.userEmail = userEmail;
        this.value = value;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
