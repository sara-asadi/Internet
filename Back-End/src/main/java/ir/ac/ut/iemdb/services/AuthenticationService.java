package ir.ac.ut.iemdb.services;

import ir.ac.ut.iemdb.model.Login;
import ir.ac.ut.iemdb.model.SignUp;
import ir.ac.ut.iemdb.model.User;
import ir.ac.ut.iemdb.repository.UserRepository;
import ir.ac.ut.iemdb.tools.exceptions.ForbiddenException;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;

public class AuthenticationService {
    public static User authenticateUser(Login login) throws Exception{
        if(login.getEmail() == null || login.getEmail().length() == 0 ||
            login.getPassword() == null || login.getPassword().length() == 0)
            throw new ForbiddenException("Fields must have values");
        User user = UserRepository.getInstance().findById(login.getEmail());
        if (user != null) {
            if (!user.getPassword().equals(login.getPassword())){
                throw new Exception("passwords do not match"+"email:"+user.getPassword()+"pass:"+login.getPassword());
            }
            return user;
        }
        else
            throw new Exception();
    }

    public static boolean isStudentInDB(String email) throws SQLException {
        if(email == null || email.length() == 0)
            throw new ForbiddenException("Field must have values");
        User user = UserRepository.getInstance().findById(email);
        return user != null;
    }

    public static void signUpUser(SignUp signUpData) throws Exception {
        User user = UserRepository.getInstance().findById(signUpData.getEmail());
        if (user != null)
            throw new Exception("email already exists");
        try {
            UserRepository.getInstance().insert(new User(signUpData.getEmail(), signUpData.getPassword(), signUpData.getNickname(), signUpData.getNickname(), signUpData.getBirthDate()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
