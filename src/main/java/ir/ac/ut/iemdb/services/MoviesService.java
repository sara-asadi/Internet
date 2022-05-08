package ir.ac.ut.iemdb.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.model.Cast;
import ir.ac.ut.iemdb.model.Comment;
import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.repository.*;
import ir.ac.ut.iemdb.services.HTTPRequestHandler.HTTPRequestHandler;

import java.util.List;

public class MoviesService {

    private static MoviesService instance;


    private MoviesService() {
    }

    public static MoviesService getInstance() {
        if (instance == null) {
            instance = new MoviesService();
        }
        return instance;
    }

    public void importMoviesFromWeb() throws Exception {
        String MoviesJsonString = HTTPRequestHandler.getRequest("http://138.197.181.131:5000/api/v2/movies");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Movie> movies = gson.fromJson(MoviesJsonString, new TypeToken<List<Movie>>() {
        }.getType());

        for (Movie movie : movies) {
            try {
                MovieRepository.getInstance().insert(movie);
                //for (String genre: movie.getGenres()) {
                //    GenreRepository.getInstance().insert(genre);
                //}
                for (Comment comment : movie.getComments()) {
                    CommentRepository.getInstance().insert(comment);
                }
                for (Integer actorId : movie.getCast()) {
                    CastRepository.getInstance().insert(new Cast(Math.toIntExact(movie.getId()), Math.toIntExact(actorId)));
                }
            } catch (Exception ignored) {}
        }
    }
}
