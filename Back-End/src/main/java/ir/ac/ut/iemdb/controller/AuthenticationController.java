package ir.ac.ut.iemdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.ac.ut.iemdb.model.*;

import ir.ac.ut.iemdb.repository.UserRepository;
import ir.ac.ut.iemdb.services.AuthenticationService;
import ir.ac.ut.iemdb.tools.JWT.JWTTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.HashMap;

@RestController
@RequestMapping("")
public class AuthenticationController {
    @PostMapping("callback")
    public ResponseEntity<?> callback(
            @RequestParam("code") String code,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, InterruptedException {

        String client_id ="5e729b70c5d14691a84d";
        String client_secret="5cf54def66d8eb54609d81cbf4d9629410bd51ca";
        String accessTokenUrl= String.format(
                "https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s",
                client_id, client_secret, code);
        HttpClient client = HttpClient.newHttpClient();
        URI accessTokenUri= URI.create(accessTokenUrl);
        HttpRequest.Builder accessTokenBuilder = HttpRequest.newBuilder().uri(accessTokenUri);
        HttpRequest accessTokenRequest =
                accessTokenBuilder
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .header("Accept", "application/json")
                        .build();
        HttpResponse<String> accessTokenResult = client.send(accessTokenRequest, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> resultBody = mapper.readValue(accessTokenResult.body(), HashMap.class);
        String accessToken = (String) resultBody.get("access_token");

        URI userDataUri = URI.create("https://api.github.com/user");
        HttpRequest.Builder userDataBuilder = HttpRequest.newBuilder().uri(userDataUri);
        HttpRequest req = userDataBuilder.GET()
                .header("Authorization", String.format("token %s", accessToken))
                .build();
        HttpResponse<String> userDataResult = client.send(req, HttpResponse.BodyHandlers.ofString());
        return ResponseEntity.status(HttpStatus.OK).body(userDataResult.body());
    }
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
