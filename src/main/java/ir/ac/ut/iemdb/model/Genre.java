package ir.ac.ut.iemdb.model;

public class Genre {
    private int movieId;
    private String name;

    public Genre(){}

    public Genre(int movieId, String name){
        this.movieId = movieId;
        this.name = name;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
