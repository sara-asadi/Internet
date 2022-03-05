package app.util;

import io.javalin.http.Handler;

public class Path {

    public static class Web {
        public static final String MOVIES = "/movies";
        public static final String MOVIE = "/movies/{movie_id}";
        public static final String ACTOR = "/actors/{actor_id}";
        public static final String ADD_WATCHLIST = "/watchList/{user_id}/{movie_id}";
        public static final String WATCHLIST = "/watchList/{user_id}";
        public static final String RATE_MOVIE = "rateMovie/{user_id}/{movie_id}/{rate}";
        public static final String VOTE_COMMENT = "voteComment/{user_id}/{comment_id}/{vote}";
        public static final String MOVIE_SEARCH_YEAR = "/movies/search/{start_year}/{end_year}";
        public static final String MOVIE_SEARCH_GENRE = "/movies/search/{genre}";
        public static final String WELCOME = "/";
        public static final String REMOVE_WATCHLIST = "/remove/{user_id}/{movie_id}";
    }

    public static class Template {
        public static final String MOVIES = "/velocity/movie/all.vm";
        public static final String MOVIE = "/velocity/movie/one.vm";
        public static final String ACTOR = "/velocity/actor/actor.vm";
        public static final String WATCHLIST = "/velocity/user/watchList.vm";

        public static final String WELCOME = "/velocity/welcome.vm";

        public static final String SUCCESS = "/velocity/success.vm";
        public static final String FORBIDDEN = "/velocity/forbidden.vm";

        public static final String NOT_FOUND = "/velocity/notFound.vm";
    }

}
