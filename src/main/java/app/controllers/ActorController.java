package app.controllers;

import app.db.ActorDB;
import app.db.UserDB;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ActorController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (UserDB.currentUser == null) {
            response.sendRedirect("../login.jsp");
            return;
        } else {
            request.setAttribute("id", Long.parseLong(request.getPathInfo().substring(1)));

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/actor.jsp");
            dispatcher.forward(request, response);
        }
    }
}

