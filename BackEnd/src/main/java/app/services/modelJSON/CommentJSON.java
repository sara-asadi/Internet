package app.services.modelJSON;

import app.db.CommentDB;
import app.model.Actor;
import app.model.Comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentJSON {
    String userName;
    long movieId;
    String text;
    long id;
    int likes;
    int disLikes;

    public CommentJSON (long id_) throws IOException {
        Comment comment = CommentDB.getInstance().getCommentById(id_);
        userName = comment.getUserName();
        movieId = comment.getMovieId();
        text = comment.getText();
        id = id_;
        likes = comment.getLikes();
        disLikes = comment.getDisLikes();
    }

    public long getId() {
        return id;
    }

    public long getMovieId() {
        return movieId;
    }

    public String getText() {
        return text;
    }

    public int getLikes() {
        return likes;
    }

    public int getDisLikes() {
        return disLikes;
    }

    public String getUserName() {
        return userName;
    }

}
