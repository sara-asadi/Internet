package ir.ac.ut.iemdb.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ir.ac.ut.iemdb.model.*;
import ir.ac.ut.iemdb.repository.*;
import ir.ac.ut.iemdb.services.HTTPRequestHandler.HTTPRequestHandler;
import ir.ac.ut.iemdb.tools.URL.Url;

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
        String MoviesJsonString = HTTPRequestHandler.getRequest(Url.movies);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Movie> movies = gson.fromJson(MoviesJsonString, new TypeToken<List<Movie>>() {
        }.getType());

        for (Movie movie : movies) {
            try {
                MovieRepository.getInstance().insert(movie);
                for (String genre: movie.getGenres()) {
                    GenreRepository.getInstance().insert(new Genre(movie.getId(),genre));
                }
                for (Integer actorId : movie.getCast()) {
                    CastRepository.getInstance().insert(new Cast(Math.toIntExact(movie.getId()), Math.toIntExact(actorId)));
                }
                for (Comment comment : movie.getComments()) {
                    CommentRepository.getInstance().insert(comment);
                }
            } catch (Exception ignored) {}
        }
    }
}