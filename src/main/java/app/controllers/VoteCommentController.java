package app.controllers;


import java.io.*;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import app.db.MovieDB;
import app.db.UserDB;
import app.db.CommentDB;
import app.model.Movie;
import app.model.Comment;
import app.model.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

public class VoteCommentController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long movie_id = Integer.parseInt(request.getParameter("movie_id"));
        int comment_id = Integer.parseInt(request.getParameter("comment_id"));
        String action = request.getParameter("action");
        Movie movie = MovieDB.getInstance().getMovieById(movie_id);
        User user = UserDB.getInstance().getCurrentUser();
        user.voteComment(movie_id,comment_id, action);

        response.sendRedirect(String.valueOf(movie_id));
    }
}