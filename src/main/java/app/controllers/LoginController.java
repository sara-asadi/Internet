package app.controllers;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import app.db.UserDB;
import app.model.User;

public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String page = "login.jsp";
        if(email != null) {
            User loggingUser = UserDB.getInstance().getUserByEmail(email);
            if(loggingUser!= null) {
                request.setAttribute("email", email);
                request.setAttribute("msg", "Login Success.....");
                UserDB.getInstance().setCurrentUser(loggingUser);
                page = "index.jsp";
            }
            else {
                request.setAttribute("msg", "Wrong Email, Try again!!!");
            }
        }
        else {
            request.setAttribute("msg", "Please enter username and password...");
        }
        request.getRequestDispatcher(page).include(request, response);
    }

}