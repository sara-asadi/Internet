package ir.ac.ut.iemdb.model;

public class Cast {
    private int movieId;
    private int actorId;

    public Cast(int movie_id, int actor_id){
        this.movieId = movie_id;
        this.actorId = actor_id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
}


