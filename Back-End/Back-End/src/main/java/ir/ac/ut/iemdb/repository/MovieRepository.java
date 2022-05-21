package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.repository.connectionpool.ConnectionPool;
import ir.ac.ut.iemdb.tools.Queries.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository extends Repository<Movie, String> {

    private static final String TABLE_NAME = "Movie";
    private static final String COLUMNS = "id, name, summary, releaseDate, director, writers, imdbRate, duration, ageLimit, image, coverImage";

    private static MovieRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getCOLUMNS() {
        return COLUMNS;
    }

    public static MovieRepository getInstance() {
        if (instance == null) {
            try {
                instance = new MovieRepository();
            } catch (SQLException ignored) {}
        }
        return instance;
    }

    private MovieRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(Queries.createMovie, TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public List<Movie> sort(String sortBy) {
        List<Movie> result = new ArrayList<>();
        String statement = String.format(Queries.Sort, MovieRepository.getCOLUMNS(), TABLE_NAME, sortBy);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    result.add(convertResultSetToDomainModel(resultSet));}
                con.close();
                return result;
            } catch (SQLException ignored) {}
        } catch (SQLException ignored) {}
        return result;
    }
    public List<Movie> search(String key) {
        List<Movie> result = new ArrayList<>();
        String query =  "SELECT id, name, summary, releaseDate, director, writers, imdbRate, duration, ageLimit, image, coverImage " +
                        "FROM Movie\n " +
                        "WHERE Movie.name LIKE '%"+key+"%';";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(query);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    result.add(convertResultSetToDomainModel(resultSet));}
                con.close();
                return result;
            } catch (SQLException ignored) {}
        } catch (SQLException ignored) {}
        return result;
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format(Queries.SearchByOne,COLUMNS, TABLE_NAME, TABLE_NAME, "id");
    }
    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setInt(1, Integer.parseInt(id));
    }

    @Override
    protected String getInsertStatement() {
        return String.format(Queries.Insert, TABLE_NAME, COLUMNS, "?,?,?,?,?,?,?,?,?,?,?");
    }
    @Override
    protected void fillInsertValues(PreparedStatement st, Movie data) throws SQLException {
        st.setInt(1, data.getId());
        st.setString(2, data.getName());
        st.setString(3, data.getSummary());
        st.setString(4, data.getReleaseDate());
        st.setString(5, data.getDirector());
        st.setString(6, data.writersString());
        st.setDouble(7, data.getImdbRate());
        st.setInt(8, data.getDuration());
        st.setInt(9, data.getAgeLimit());
        st.setString(10, data.getImage());
        st.setString(11, data.getCoverImage());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format(Queries.SelectAll, TABLE_NAME);
    }

    @Override
    protected Movie convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Movie(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getDouble(7),
                rs.getInt(8),
                rs.getInt(9),
                RatesRepository.getInstance().getRating(rs.getInt(1)),
                RatesRepository.getInstance().getRatingCount(rs.getInt(1)),
                rs.getString(10),
                rs.getString(11),
                GenreRepository.getInstance().getGenres(rs.getInt(1))
        );
    }

    @Override
    protected ArrayList<Movie> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Movie> movies = new ArrayList<>();
        while (rs.next()) {
            movies.add(this.convertResultSetToDomainModel(rs));
        }
        return movies;
    }
}
