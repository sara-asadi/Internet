package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Cast;
import ir.ac.ut.iemdb.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CastRepository extends Repository<Cast, String>{
    private static final String TABLE_NAME = "CAST";
    private static CastRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static CastRepository getInstance() {
        if (instance == null) {
            try {
                instance = new CastRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in CastRepository.create query.");
            }
        }
        return instance;
    }

    private CastRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(" +
                        "movieId int not null, \n" +
                        "actorId int not null,\n" +
                        "PRIMARY KEY(movieId, actorId),\n" +
                        "FOREIGN KEY (movieId) REFERENCES Movie(id),\n" +
                        "FOREIGN KEY (actorId) REFERENCES Actor(id));", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }
    protected String getFindAllCastByMovieIdStatement(){
        return String.format("SELECT* FROM %s cast WHERE cast.movieId = ?;", TABLE_NAME);
    }

    protected void fillFindAllCastByMovieIdValues(PreparedStatement st, String movieId) throws SQLException {
        st.setInt(1, Integer.parseInt(movieId));
    }

    protected String getFindAllMovieByActorIdStatement(){
        return String.format("SELECT* FROM %s cast WHERE cast.actorId = ?;", TABLE_NAME);
    }
    protected void fillFindAllMovieByActorIdValues(PreparedStatement st, String actorId) throws SQLException {
        st.setInt(1, Integer.parseInt(actorId));
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
        return String.format("INSERT INTO %s(movieId, actorId) VALUES(?,?)", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Cast data) throws SQLException {
        st.setInt(1, data.getMovieId());
        st.setInt(2, data.getActorId());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected Cast convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Cast(rs.getInt(1), rs.getInt(2));
    }

    @Override
    protected ArrayList<Cast> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Cast> casts = new ArrayList<>();
        while (rs.next()) {
            casts.add(this.convertResultSetToDomainModel(rs));
        }
        return casts;
    }
}
