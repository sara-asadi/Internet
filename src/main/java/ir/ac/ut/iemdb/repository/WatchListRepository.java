package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Cast;
import ir.ac.ut.iemdb.model.WatchList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WatchListRepository extends Repository<WatchList, String>{
    private static final String TABLE_NAME = "WATCHLIST";
    private static WatchListRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static WatchListRepository getInstance() {
        if (instance == null) {
            try {
                instance = new WatchListRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in WatchListRepository.create query.");
            }
        }
        return instance;
    }

    private WatchListRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(" +
                        "    movieId int not null,\n" +
                        "    userEmail varchar(25) not null,\n" +
                        "    primary key (movieId, userEmail),\n" +
                        "    FOREIGN KEY (userEmail) REFERENCES User(email),\n" +
                        "    FOREIGN KEY (movieId) REFERENCES Movie(id)", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }
    protected String getFindAllWatchListMovieByUserEmailStatement(){
        return String.format("SELECT* FROM %s watchMovie WHERE watchMovie.userEmail = ?;", TABLE_NAME);
    }

    protected void fillFindAllWatchListMovieByUserEmailValues(PreparedStatement st, WatchList data) throws SQLException {
        st.setString(1, data.getUserEmail());
        st.setLong(2, data.getMovieId());

    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT INTO %s(userEmail, actorId) VALUES(?,?)", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, WatchList data) throws SQLException {
        st.setString(1, data.getUserEmail());
        st.setLong(2, data.getMovieId());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected WatchList convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new WatchList(rs.getString(1), rs.getLong(2));
    }

    @Override
    protected ArrayList<WatchList> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<WatchList> watchLists = new ArrayList<>();
        while (rs.next()) {
            watchLists.add(this.convertResultSetToDomainModel(rs));
        }
        return watchLists;
    }

    @Override
    protected String getFindByIdStatement() {
        return null;
    }

    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {

    }
}
