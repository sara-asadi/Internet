package app.controllers;


import java.io.*;

import app.FlightManager;
import org.apache.commons.lang.StringUtils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookFlight")
public class BookFlightController  extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String destination = request.getParameter("destination");
        String numberOfTickets = request.getParameter("numberOfTickets");
        
        if(StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName) ||
        		StringUtils.isBlank(destination) || StringUtils.isBlank(numberOfTickets)) {
            String buyPageName = "/buy.jsp";
            if(StringUtils.isBlank(firstName))
            	request.setAttribute("badFirstName", "true");
            if(StringUtils.isBlank(lastName))
            	request.setAttribute("badLastName", "true");
            
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(buyPageName);
            requestDispatcher.forward(request, response);
        } else {

        	FlightManager.getInstance().bookFlight(destination, Integer.parseInt(numberOfTickets));

            String buyPageName = "newBook.jsp";
    		RequestDispatcher requestDispatcher = request.getRequestDispatcher(buyPageName);
            requestDispatcher.forward(request, response);        	
        }
        
	}
}
