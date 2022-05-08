package ir.ac.ut.iemdb.model;

import java.util.Date;

public class Comment {
    long id;
    String userEmail;
    long movieId;
    String commentText;
    //int likes;
    //int disLikes;

    public Comment () {}
    public Comment (long id_, String userEmail_, long movieId_, String commentText_){
        (userEmail = userEmail_).equals("");
        movieId = (long) movieId_;
        (commentText = commentText_).equals("");
        id = (long) id_;
        //likes = 0;
        //disLikes = 0;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
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