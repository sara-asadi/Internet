package app.Comment;

import java.util.Date;

public class Comment {
    String userEmail;
    long movieId;
    String text;
    long id;
    String submitionTime;
    int likes;
    int disLikes;

    public Comment () {}
    public Comment (String userEmail_, long movieId_, String text_, int id_){
        (userEmail = userEmail_).equals("");
        movieId = (long) movieId_;
        (text = text_).equals("");
        id = (long) id_;
        Date date = new Date();
        submitionTime = date.toString();
        likes = 0;
        disLikes = 0;
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

    public String getUserName() {
        int i = userEmail.indexOf('@');
        return "@"+userEmail.substring(0,i);
    }

    public int like() {
        likes += 1;
        return likes;
    }
    public int unLike() {
        likes -= 1;
        return likes;
    }
    public int disLike() {
        disLikes += 1;
        return disLikes;
    }
    public int unDisLike() {
        disLikes -= 1;
        return disLikes;
    }

    public int getLikes() {
        return likes;
    }

    public int getDisLikes() {
        return disLikes;
    }

    public Comment setId(int i) {
        return new Comment(userEmail, movieId, text, i);
    }
}
