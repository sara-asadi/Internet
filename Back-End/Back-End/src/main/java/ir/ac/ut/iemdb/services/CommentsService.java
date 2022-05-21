package ir.ac.ut.iemdb.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ir.ac.ut.iemdb.model.Comment;
import ir.ac.ut.iemdb.repository.CommentRepository;
import ir.ac.ut.iemdb.services.HTTPRequestHandler.HTTPRequestHandler;
import ir.ac.ut.iemdb.tools.URL.Url;

import java.util.List;

public class CommentsService {

    private static CommentsService instance;


    private CommentsService() {
    }

    public static CommentsService getInstance() {
        if (instance == null) {
            instance = new CommentsService();
        }
        return instance;
    }

    public void importCommentsFromWeb() throws Exception {
        String CommentsJsonString = HTTPRequestHandler.getRequest(Url.comments);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Comment> comments = gson.fromJson(CommentsJsonString, new TypeToken<List<Comment>>() {
        }.getType());

        for (Comment comment : comments) {
            try {
                CommentRepository.getInstance().insert(comment);
            } catch (Exception ignored) {}
        }
    }
}