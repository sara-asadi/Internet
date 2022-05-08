package ir.ac.ut.iemdb.model;

import java.util.Date;

public class Comment {
    int id;
    String userEmail;
    int movieId;
    String text;
    //int likes;
    //int disLikes;

    public Comment () {}
    public Comment (int id_, String userEmail_, int movieId_, String text_){
        (userEmail = userEmail_).equals("");
        movieId = (int) movieId_;
        (text = text_).equals("");
        id = (int) id_;
        //likes = 0;
        //disLikes = 0;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

//    public String getUserName() {
//        int i = userEmail.indexOf('@');
//        return "@"+userEmail.substring(0,i);
//    }

//    public int getLikes() {
//        return likes;
//    }

//    public int getDisLikes() {
//        return disLikes;
//    }

/*    public int like() {
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

    public Comment setId(int i) {
        return new Comment(userEmail, movieId, text, i);
    }

 */
}