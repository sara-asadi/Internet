package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Film;
import ir.ac.ut.iemdb.model.IEMDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilmRepository extends Repository<Film, String> {

    private static final String TABLE_NAME = "FILM_TABLE";
    private static FilmRepository instance;

    public static FilmRepository getInstance() {
        if (instance == null) {
            try {
                instance = new FilmRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in FilmRepository.create query.");
            }
        }
        return instance;
    }

    private FilmRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(id CHAR(50),\nname CHAR(225),\nactor CHAR(225),\nPRIMARY KEY(id));", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Film data) throws SQLException {

    }

    @Override
    protected String getFindByIdStatement() {
        return String.format("SELECT* FROM %s film WHERE film.id = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setString(1, id);
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT INTO %s(id, name, summary, releaseDate, director, writers, genres, cast, imdbRate, duration, ageLimit, image, coverImage) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_NAME);
    }

//    @Override
//    protected void fillInsertValues(PreparedStatement st, IEMDB data) throws SQLException {
//        st.setString(1, data.getId());
//        st.setString(2, data.getName());
//        st.setString(3, data.getActor());
//    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected Film convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Film(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getArray(6), rs.getArray(7), rs.getArray(8), rs.getDouble(9), rs.getDouble(10), rs.getInt(11), rs.getString(12), rs.getString(13));
    }

    @Override
    protected ArrayList<Film> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Film> films = new ArrayList<>();
        while (rs.next()) {
            films.add(this.convertResultSetToDomainModel(rs));
        }
        return films;
    }
}
