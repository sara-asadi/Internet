package app.db;


import app.model.Comment;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommentDB {
    private static CommentDB instance;
    private static List<Comment> comments;
    int commentsNumber;

    private CommentDB() throws IOException {
        comments = getCommentsArray("http://138.197.181.131:5000/api/comments");
        for (int i = 1; i<=comments.size(); i++) {
            comments.set(i-1, comments.get(i-1).setId(i));
        }
        commentsNumber = comments.size();
    }
    public void InsertComments() throws IOException {
        for (Comment comment : comments) {
            MovieDB.getInstance().getMovieById(comment.getMovieId()).addComment(comment);
        }
    }
    public static CommentDB getInstance() throws IOException {
        if (instance == null)
            instance = new CommentDB();
        return instance;
    }

    public void addComment(String text, long movie_id, String email) throws IOException {
        Comment comment = new Comment(email, movie_id, text, commentsNumber+1);
        commentsNumber += 1;
        MovieDB.getInstance().getMovieById(movie_id).addComment(comment);
    }
    public List<Comment> getCommentsArray(String apiAddress) throws IOException{
        List<Comment> Comments;
        URL url = new URL(apiAddress);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            Gson gson = new Gson();
            Comment[] commentArray = gson.fromJson(inline.toString(), Comment[].class);
            Comments = Arrays.asList(commentArray);
        }

        return Comments;
    }
}
