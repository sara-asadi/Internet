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
                String.format("CREATE TABLE IF NOT EXISTS %s(id long not null,\n" +
                        "    movieId  long not null,\n" +
                        "    actorId long not null,\n" +
                        "    PRIMARY KEY(id));", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }
    protected String getFindAllCastByMovieId(){
        return String.format("SELECT* FROM %s cast WHERE cast.movieId = ?;", TABLE_NAME);
    }

    protected String getFindAllMovieByActorId(){
        return String.format("SELECT* FROM %s cast WHERE cast.actorId = ?;", TABLE_NAME);
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format("SELECT* FROM %s cast WHERE cast.id = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setLong(1, Long.parseLong(id));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT INTO %s(id,movieId, actorId) VALUES(?,?,?)", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Cast data) throws SQLException {
        st.setLong(1, data.getId());
        st.setLong(2, data.getMovieId());
        st.setLong(3, data.getActorId());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected Cast convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Cast(rs.getLong(1), rs.getLong(2), rs.getLong(3));
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
