package app.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

public class UserDao {

    private final List<User> users;

    public UserDao() {
        this.users = new ArrayList<>();
    }


    public User getUserByEmail(String email) {
        return users.stream().filter(b -> b.email.equals(email)).findFirst().orElse(null);
    }

    public Iterable<String> getAllUserNames() {
        return users.stream().map(user -> user.email).collect(Collectors.toList());
    }

}
