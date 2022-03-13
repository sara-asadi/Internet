package app.controllers;


import java.io.*;
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

public class RateMovieController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int rate = Integer.parseInt(request.getParameter("quantity"));
        long movie_id = Integer.parseInt(request.getParameter("movie_id"));
        Movie movie = MovieDB.getInstance().getMovieById(movie_id);
        User user = UserDB.getInstance().getCurrentUser();
        if (rate > 0 && rate <= 10) {
            long prevRate = user.rateMovie(movie_id, rate);
            long total = ((long) (movie.getRating() * movie.getRatingCount())) - prevRate;
            if(prevRate == 0){
                movie.incRatingCount();
            }
            movie.setRating((double) (total + rate) / (movie.getRatingCount()));
            response.sendRedirect(String.valueOf(movie_id));
        }
        else {
            String error = "invalid rate value!";
        }
    }
}
