package ir.ac.ut.iemdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ir.ac.ut.iemdb.model.*;

import ir.ac.ut.iemdb.repository.UserRepository;
import ir.ac.ut.iemdb.services.AuthenticationService;
import ir.ac.ut.iemdb.security.jwt.JWTTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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


    @PostMapping("callback")
    public ResponseEntity<?> callback(
            @RequestBody Code c) throws Exception {

        String client_id ="5e729b70c5d14691a84d";
        String client_secret="5cf54def66d8eb54609d81cbf4d9629410bd51ca";
        String accessTokenUrl= String.format("https://github.com/login/oauth/access_token?client_id=5e729b70c5d14691a84d&client_secret=5cf54def66d8eb54609d81cbf4d9629410bd51ca&code=%s", c.getCode());

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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Git gitData = gson.fromJson(userDataResult.body(), new TypeToken<Git>() {
        }.getType());
        if (gitData.getEmail() == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("sign up unsuccessful");
        try {
            String token =  AuthenticationService.signup(new SignUp(gitData.getName(), gitData.getName(), gitData.getCreated_at(), gitData.getEmail(), "null"));
            JWTTokenResponse jwtResponse = new JWTTokenResponse(token, gitData.getEmail());
            jwtResponse.setCode(200);
            jwtResponse.setMessgae("Signed Up!");
            return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
        } catch (Exception e) {
            if (e.getMessage().equals("email already exists")) {
                String token = AuthenticationService.login(new Login(gitData.getEmail(), "null"));
                JWTTokenResponse jwtResponse = new JWTTokenResponse(token, gitData.getEmail());
                jwtResponse.setCode(200);
                jwtResponse.setMessgae("Logged In!");
                return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
            }
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("sign up unsuccessful");
        }
    }
}
