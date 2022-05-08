package ir.ac.ut.iemdb.model;

public class Cast {
    private long id;
    private long movie_id;
    private long actor_id;

    public Cast(long id, long movie_id, long actor_id){
        this.id = id;
        this.movie_id = movie_id;
        this.actor_id = actor_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovieId() {
        return movie_id;
    }

    public void setMovieId(long MovieId) {
        this.movie_id = MovieId;
    }

    public long getActorId() {
        return actor_id;
    }

    public void setActorId(long ActorId) {
        this.actor_id = ActorId;
    }

}


