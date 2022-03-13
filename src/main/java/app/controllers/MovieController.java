package app.controllers;

import app.db.CommentDB;
import app.db.MovieDB;
import app.db.UserDB;
import app.model.Comment;
import app.model.Movie;
import app.model.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.SyncFailedException;

public class MovieController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CommentDB.getInstance();
        request.setAttribute("id",Long.parseLong(request.getPathInfo().substring(1)));
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/movie.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        long movie_id = Integer.parseInt(request.getParameter("movie_id"));

        if(action.equals("comment")){
            String comment = request.getParameter("comment");
            User user = UserDB.getInstance().getCurrentUser();
            Comment commentObj = new Comment(user.getEmail(), movie_id,comment, CommentDB.getInstance().generateId());
            CommentDB.getInstance().addComment(commentObj);
        }
        else if(action.equals("like")){
            long comment_id = Integer.parseInt(request.getParameter("comment_id"));
            CommentDB.getInstance().getCommentById(comment_id).like();
        }
        else if(action.equals("dislike")){
            long comment_id = Integer.parseInt(request.getParameter("comment_id"));
            CommentDB.getInstance().getCommentById(comment_id).disLike();
        }
        doGet(request,response);
    }
}
