package ir.ac.ut.iemdb.model;

public class Vote {
    int commentId;
    String userEmail;
    int value;

    public Vote(){}

    public Vote(int commentId, String userEmail, int value){
        this.commentId = commentId;
        this.userEmail = userEmail;
        this.value = value;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        value = value;
    }
}