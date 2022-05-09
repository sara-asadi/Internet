package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.model.User;
import ir.ac.ut.iemdb.repository.ActorRepository;
import ir.ac.ut.iemdb.repository.CastRepository;

import ir.ac.ut.iemdb.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("")
public class AuthenticationController {
    @GetMapping("login/{email}/{password}")
    public String login(@PathVariable String email, @PathVariable String password) throws SQLException {
        try {
            User user = UserRepository.getInstance().findById(email);
            if (Objects.equals(user.getPassword(), password)) {
                UserRepository.setCurrentUser(email);
                return "You are logged in as " + user.getNickname();
            }
            return "wrong password";
        } catch (NullPointerException e) {
            return "wrong email!\n please signup";
        }
    }
    @GetMapping("logout")
    public String logout() throws SQLException {
        UserRepository.setCurrentUser("guest");
        return "logout successful!";
    }
}
