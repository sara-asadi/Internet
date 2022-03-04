package app.user;

import org.mindrot.jbcrypt.BCrypt;

import static app.Main.*;

public class UserController {

    public static boolean authenticate(String username, String password) {
        return true;
    }

    // This method doesn't do anything, it's just included as an example
    public static void setPassword(String username, String oldPassword, String newPassword) {
        if (authenticate(username, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password
        }
    }
}
