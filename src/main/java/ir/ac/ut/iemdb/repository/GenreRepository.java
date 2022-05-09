package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Genre;
import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.repository.connectionpool.ConnectionPool;
import ir.ac.ut.iemdb.tools.Queries.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreRepository extends Repository<Genre, String>{

    private static final String TABLE_NAME = "Genres";
    private static final String COLUMNS = "movieId, genre";

    private static GenreRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }
    public static String getCOLUMNS() {
        return COLUMNS;
    }

    public static GenreRepository getInstance() {
        if (instance == null) {
            try {
                instance = new GenreRepository();
            } catch (SQLException ignored) {}
        }
        return instance;
    }

    private GenreRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(Queries.createGenre, TABLE_NAME, MovieRepository.getTableName())
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public List<Movie> getMovies(String genre) {
        List<Movie> result = new ArrayList<>();
        String statement = String.format(Queries.SearchByTwoS, MovieRepository.getCOLUMNS(), TABLE_NAME, MovieRepository.getTableName(), TABLE_NAME, "genre", genre, TABLE_NAME, "movieId", MovieRepository.getTableName(), "id");
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    result.add(MovieRepository.getInstance().convertResultSetToDomainModel(resultSet));}
                con.close();
                return result;
            } catch (SQLException ignored) {}
        } catch (SQLException ignored) {}
        return result;
    }

    public String getGenres(int movieId) {
        List<String> s = new ArrayList<>();
        String statement = String.format(Queries.SearchByInt, COLUMNS, TABLE_NAME, TABLE_NAME, "movieId", movieId);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    s.add(convertResultSetToDomainModel(resultSet).getName());
                }
                con.close();
                return Arrays.toString(s.toArray());
            } catch (SQLException ignored) {}
        } catch (SQLException ignored) {}
        return Arrays.toString(s.toArray());
    }

    @Override
    protected String getFindByIdStatement() {
        return null;
    }
    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
    }

    @Override
    protected String getInsertStatement() {
        return String.format(Queries.Insert, TABLE_NAME, COLUMNS, "?,?");
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Genre data) throws SQLException {
        st.setInt(1, data.getMovieId());
        st.setString(2, data.getName());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format(Queries.SelectAll, TABLE_NAME);
    }

    @Override
    protected Genre convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getInt(1),
                rs.getString(2)
        );
    }

    @Override
    protected ArrayList<Genre> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Genre> genres = new ArrayList<>();
        while (rs.next()) {
            genres.add(this.convertResultSetToDomainModel(rs));
        }
        return genres;
    }
}