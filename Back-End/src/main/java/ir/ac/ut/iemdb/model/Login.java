package ir.ac.ut.iemdb.model;

public class Login {
    String email;
    String password;

    public Login(String email_, String password_) {
        email=email_;
        password=password_;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
