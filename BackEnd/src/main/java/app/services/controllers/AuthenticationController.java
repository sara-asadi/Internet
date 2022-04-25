package app.services.controllers;


import app.db.UserDB;
import app.model.User;
import app.services.modelJSON.MovieJSON;
import app.services.repository.Movies;
import app.tools.Response;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@RestController
public class AuthenticationController {
    @GetMapping("")
    public List<MovieJSON> getMovies() throws IOException {
        return Movies.getInstance().getMoviesList();
    }

    @PostMapping(path = "login", consumes = "application/json", produces = "application/json")
    public String login(@RequestBody(required = true) String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        String email = properties.getProperty("email");
        String password = properties.getProperty("password");

        if(email != null) {
            User loggingUser = UserDB.getInstance().getUserByEmail(email);
            if(loggingUser!= null && Objects.equals(password, loggingUser.getPassword())) {
                UserDB.getInstance().login(loggingUser);
                return Response.OK_RESPONSE;
            }
            else {
                String error = "Invalid Email or Password!";
                response.sendError(HttpStatus.BAD_REQUEST.value(), error);
                return null;
            }
        }
        else {
            String error = "Invalid Email or Password!";
            response.sendError(HttpStatus.BAD_REQUEST.value(), error);
            return null;
        }
    }

    @PostMapping(path = "signup", consumes = "application/json", produces = "application/json")
    public String signup(@RequestBody(required = true) String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        String email = properties.getProperty("email");
        String password = properties.getProperty("password");
        String nickname = properties.getProperty("nickname");
        String name = properties.getProperty("name");
        String birthDate = properties.getProperty("birthDate");

        User user = new User(email, password, nickname, name, birthDate);
        if (!UserDB.getInstance().addUser(user)){
            response.sendError(HttpStatus.BAD_REQUEST.value(), "User Already Exists");
            return null;
        }
        return Response.OK_RESPONSE;

    }

    @DeleteMapping("logout")
    public void logout(@RequestBody String jsonString) throws IOException {
        UserDB.getInstance().logout();
    }

}

