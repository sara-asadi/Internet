package ir.ac.ut.iemdb.services;

import com.google.common.hash.Hashing;
import ir.ac.ut.iemdb.model.Login;
import ir.ac.ut.iemdb.model.SignUp;
import ir.ac.ut.iemdb.model.User;
import ir.ac.ut.iemdb.repository.UserRepository;
import ir.ac.ut.iemdb.security.jwt.JWTAuthentication;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class AuthenticationService {

    public static String login(Login data) throws Exception {
        User user = UserRepository.getInstance().findById(data.getEmail());
        if (user != null) {
            if (!PasswordIsCorrect(user, data.getPassword()))
                throw new Exception("password incorrect");
            UserRepository.setCurrentUser(data.getEmail());
            return JWTAuthentication.createAndSignToken(data.getEmail());
        }
        else throw new Exception("email not found");
    }

    private static boolean PasswordIsCorrect(User user, String p) throws SQLException {
        String userPassword = user.getPassword();
        String password = Hashing.sha256().hashString(p, StandardCharsets.UTF_8).toString();
        return password.startsWith(userPassword);
    }

    public static String signup(SignUp data) throws Exception {
        try {
            User user = UserRepository.getInstance().findById(data.getEmail());
        } catch (Exception e) { //new email
            UserRepository.getInstance().insert(new User(data.getEmail(), data.getPassword(), data.getNickname(), data.getNickname(), data.getBirthDate()));
            UserRepository.setCurrentUser(data.getEmail());
            return JWTAuthentication.createAndSignToken(data.getEmail());
        }
        throw new Exception("email already exists");
    }
}
