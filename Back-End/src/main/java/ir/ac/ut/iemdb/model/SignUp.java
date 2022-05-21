package ir.ac.ut.iemdb.model;

public class SignUp {
    private final String name;
    private final String nickname;
    private final String birthDate;
    private final String email;
    private final String password;

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBirthDate() { return birthDate; }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public SignUp(String name, String nickname, String birthDate,
                  String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }

}
