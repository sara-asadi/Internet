package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.*;

import ir.ac.ut.iemdb.repository.UserRepository;
import ir.ac.ut.iemdb.services.AuthenticationService;
import ir.ac.ut.iemdb.tools.JWT.JWTTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("")
public class AuthenticationController {
    @PostMapping("login")
    public ResponseEntity<?> login(
            @RequestBody Login loginData) throws IOException {
        try {
            String token =  AuthenticationService.login(loginData);
            JWTTokenResponse jwtResponse = new JWTTokenResponse(token, loginData.getEmail());
            jwtResponse.setCode(200);
            jwtResponse.setMessgae("Logged In!");
            return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e.getMessage().equals("password incorrect"))
                return ResponseEntity.status(HttpStatus.OK).body("password incorrect");
            if (e.getMessage().equals("email not found"))
                return ResponseEntity.status(HttpStatus.OK).body("email not found");
            else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(
            @RequestBody SignUp signupData) throws IOException {
        try {
            String token =  AuthenticationService.signup(signupData);
            JWTTokenResponse jwtResponse = new JWTTokenResponse(token, signupData.getEmail());
            jwtResponse.setCode(200);
            jwtResponse.setMessgae("Signed Up!");
            return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e.getMessage().equals("email already exists")) {
                return ResponseEntity.status(HttpStatus.OK).body("email already exists");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("sign up unsuccessful");
        }
    }

    @GetMapping("logout")
    public String logout() throws SQLException {
        UserRepository.setCurrentUser("guest");
        return "logout successful!";
    }
}
