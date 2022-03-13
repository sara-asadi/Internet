package app.controllers;

import app.db.UserDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (UserDB.currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        response.sendRedirect("index.jsp");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (UserDB.currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        response.sendRedirect("index.jsp");
    }
}