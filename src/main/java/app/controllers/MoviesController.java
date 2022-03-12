package app.controllers;


import java.io.*;
import java.util.Collection;
import java.util.List;

import app.db.MovieDB;
import app.model.Movie;
import org.apache.commons.lang.StringUtils;

//import ir.ac.ut.ece.ie.FlightManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/movies")
public class MoviesController extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if(StringUtils.isBlank(action) || action == "sort_by_imdb") {
            Collection<Movie> movies = MovieDB.getInstance().getMovies();
//            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/movies.jsp");
            request.setAttribute("movies",movies);
            request.getRequestDispatcher("movies.jsp").forward(request, response);
        } else {

//        	FlightManager.getInstance().bookFlight(destination, Integer.parseInt(numberOfTickets));

            String buyPageName = "newBook.jsp";
    		RequestDispatcher requestDispatcher = request.getRequestDispatcher(buyPageName);
            requestDispatcher.forward(request, response);        	
        }
        
	}
}
