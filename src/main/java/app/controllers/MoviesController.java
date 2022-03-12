package app.controllers;


import java.io.*;
import java.util.Collection;
import java.util.List;

import app.db.MovieDB;
import app.model.Movie;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

public class MoviesController extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        String action = request.getParameter("action");

        if(StringUtils.isBlank(action)) {
            request.getRequestDispatcher("movies.jsp").forward(request, response);
        }
        if (action.equals("sort_by_imdb")) {
            MovieDB.getInstance().SortByImdbRate();
            request.getRequestDispatcher("movies.jsp").forward(request, response);
        }
        if (action.equals("sort_by_date")) {
            System.out.println("************");
            MovieDB.getInstance().SortByReleaseDate();
            request.getRequestDispatcher("movies.jsp").forward(request, response);
        }
        if (action.equals("clear")) {
            MovieDB.getInstance().clearSearchFilter();
            request.getRequestDispatcher("movies.jsp").forward(request, response);
        }
        if (action.equals("search")) {
            String searchKey = request.getParameter("search");
            MovieDB.getInstance().FilterMovies(searchKey);
            request.getRequestDispatcher("movies.jsp").forward(request, response);
        }
	}
}
