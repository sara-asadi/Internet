package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Movie;

import java.sql.*;
import java.util.ArrayList;

public class MovieRepository extends Repository<Movie, String> {

    private static final String TABLE_NAME = "Movie";
    private static final String COLUMNS = "id,name,summary,releaseDate,director,writers,imdbRate," +
            "duration,ageLimit,rating,ratingCount,image,coverImage";

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
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in MovieRepository.create query.");
            }
        }
        return instance;
    }

    private MovieRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(id int not null,\n" +
                        "    id int not null,\n" +
                        "    name  varchar(20) not null,\n" +
                        "    summary varchar(1000),\n" +
                        "    releaseDate varchar(20),\n" +
                        "    director varchar(20),\n" +
                        "    writers varchar(200),\n" +
                        "    imdbRate double,\n" +
                        "    duration int,\n" +
                        "    ageLimit int,\n" +
                        "    rating double default 0,\n" +
                        "    ratingCount int default 0,\n" +
                        "    image varchar(250),\n" +
                        "    coverImage varchar(250),\n" +
                        "    PRIMARY KEY(id));", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format("SELECT %s FROM %s movie WHERE movie.id = ?;",COLUMNS, TABLE_NAME);
    }

    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setInt(1, Integer.parseInt(id));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s(id, name, summary, releaseDate, director, writers, imdbRate, duration, ageLimit, image, coverImage) \n" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?);", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Movie data) throws SQLException {
        st.setInt(1, data.getId());
        st.setString(2, data.getName());
        st.setString(3, data.getSummary());
        st.setString(4, data.getReleaseDate());
        st.setString(5, data.getDirector());
        st.setString(6, data.getName());
        st.setDouble(7, data.getImdbRate());
        st.setInt(8, data.getDuration());
        st.setInt(9, data.getAgeLimit());
        st.setString(10, data.getImage());
        st.setString(11, data.getCoverImage());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected Movie convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getDouble(7),
                rs.getInt(8),
                rs.getInt(9), rs.getString(10), rs.getString(11));
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
