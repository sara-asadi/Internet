package ir.ac.ut.iemdb.model;

import java.util.Date;

public class Comment {
    int id;
    String userEmail;
    int movieId;
    String commentText;
    //int likes;
    //int disLikes;

    public Comment () {}
    public Comment (int id_, String userEmail_, int movieId_, String commentText_){
        (userEmail = userEmail_).equals("");
        movieId = (int) movieId_;
        (commentText = commentText_).equals("");
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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