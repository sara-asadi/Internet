package app.controllers;


import java.io.*;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import app.db.MovieDB;
import app.db.UserDB;
import app.model.Movie;
import app.model.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

public class RemoveFromWatchListController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long movie_id = Integer.parseInt(request.getParameter("movie_id"));
        Movie movie = MovieDB.getInstance().getMovieById(movie_id);
        User user = UserDB.getInstance().getCurrentUser();
        try {
            if (user.getAge() > movie.getAgeLimit()) {
                user.removeWatchList(movie_id);
                response.sendRedirect("watchlist");
            } else {
                String error = "couldn't remove!";
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}