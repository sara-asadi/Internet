package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.*;
import ir.ac.ut.iemdb.repository.ActorRepository;
import ir.ac.ut.iemdb.repository.CastRepository;

import ir.ac.ut.iemdb.repository.UserRepository;
import ir.ac.ut.iemdb.services.AuthenticationService;
import ir.ac.ut.iemdb.tools.JWT.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @GetMapping("/login/{email}/{password}")
    public ResponseEntity login(@PathVariable String email, @PathVariable String password) throws SQLException {
        try {
            Login loginData = new Login(email, password);
            User user = AuthenticationService.authenticateUser(loginData);
            String answer = JWTUtils.createJWT(user.getEmail(), 24);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not found. invalid login");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody Login loginData) throws IOException {
        String email = loginData.getEmail();
        String password = loginData.getPassword();
        try {
            User user = AuthenticationService.authenticateUser(loginData);
            String answer = JWTUtils.createJWT(user.getEmail(), 24);
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not found. invalid login");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(
            @RequestBody SignUp signUpData) throws IOException {
        String email = signUpData.getEmail();
        String password = signUpData.getPassword();
        try {
            AuthenticationService.signUpUser(signUpData);
            System.out.println("sign up successfull");
            return ResponseEntity.status(HttpStatus.OK).body("OK - sign up successfull");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("sign up unsuccessful");
        }
    }

    @GetMapping("logout")
    public String logout() throws SQLException {
        UserRepository.setCurrentUser("guest");
        return "logout successful!";
    }
}
