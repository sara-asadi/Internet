package ir.ac.ut.iemdb.model;

public class Vote {
    String userEmail;
    long commentId;
    int value;


    public Vote(){}

    public Vote(String userEmail,long commentId, int value){
        this.userEmail = userEmail;
        this.commentId = commentId;
        this.value = value;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        value = value;
    }
}