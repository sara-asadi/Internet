package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Genre;
import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.model.WatchList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenreRepository extends Repository<Genre, String>{

    private static final String TABLE_NAME = "Genre";
    private static final String COLUMNS = "movieId,name";

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
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in GenreRepository.create query.");
            }
        }
        return instance;
    }

    private GenreRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(" +
                        "   movieId int not null,\n" +
                        "    name  varchar(20) not null,\n" +
                        "    PRIMARY KEY(movieId,name)),"+
                        "    FOREIGN KEY (movieId) REFERENCES %s(id));", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    protected String getFindAllMovieByGenreStatement(){
        return String.format("SELECT* FROM %s movieGenre WHERE  movieGenre.name = ?;", TABLE_NAME);
    }

    protected void fillFindAllMovieByGenreValues(PreparedStatement st, Genre data) throws SQLException {
        st.setString(1, data.getName());
    }

    protected String getFindAllGenreByMovieIdStatement(){
        return String.format("SELECT* FROM %s movieGenre WHERE  movieGenre.movieId = ?;", TABLE_NAME);
    }

    protected void fillFindAllGenreByMovieIdValues(PreparedStatement st, Genre data) throws SQLException {
        st.setInt(1, data.getMovieId());
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
        return String.format("INSERT IGNORE INTO %s(movieId, name) \n" +
                "VALUES(?,?);", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Genre data) throws SQLException {
        st.setInt(1, data.getMovieId());
        st.setString(2, data.getName());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected Genre convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Genre(rs.getInt(1), rs.getString(2));
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
