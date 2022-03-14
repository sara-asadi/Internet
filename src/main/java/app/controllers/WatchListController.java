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

public class WatchListController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (UserDB.currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        response.sendRedirect("watchlist.jsp");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (UserDB.currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    }
}

