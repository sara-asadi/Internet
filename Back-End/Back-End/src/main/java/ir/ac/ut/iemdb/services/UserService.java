package ir.ac.ut.iemdb.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ir.ac.ut.iemdb.model.User;
import ir.ac.ut.iemdb.repository.UserRepository;
import ir.ac.ut.iemdb.services.HTTPRequestHandler.HTTPRequestHandler;
import ir.ac.ut.iemdb.tools.URL.Url;

import java.util.List;

public class UserService {

    private static UserService instance;


    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void importUserFromWeb() throws Exception {
        String UserJsonString = HTTPRequestHandler.getRequest(Url.users);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<User> users = gson.fromJson(UserJsonString, new TypeToken<List<User>>() {
        }.getType());

        for (User user : users) {
            try {
                UserRepository.getInstance().insert(user);
            } catch (Exception ignored) {}
        }
    }
}