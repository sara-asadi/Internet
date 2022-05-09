package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.model.WatchList;
import ir.ac.ut.iemdb.repository.connectionpool.ConnectionPool;
import ir.ac.ut.iemdb.tools.Queries.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchListRepository extends Repository<WatchList, String>{

    private static final String TABLE_NAME = "WatchList";
    private static final String COLUMNS = "userEmail, movieId";

    private static WatchListRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getCOLUMNS() {
        return COLUMNS;
    }

    public static WatchListRepository getInstance() {
        if (instance == null) {
            try {
                instance = new WatchListRepository();
            } catch (SQLException ignored) {}
        }
        return instance;
    }

    private WatchListRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(Queries.createWatchList, TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public List<Movie> getUserWatchListMovies(String userEmail) {
        List<Movie> result = new ArrayList<>();
        String statement = "SELECT * FROM Movie, WatchList\nWHERE WatchList.userEmail = \""+userEmail+"\" and watchList.movieId = Movie.id;";

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
            } catch (SQLException e) {e.printStackTrace();}
        } catch (SQLException e) {
            System.out.println("hi");
        }
        return result;
    }

    public void remove(int movieId, String userEmail) {
        String statement = String.format("delete from %s\nwhere %s.%s = %d and %s.%s = \"%s\";", TABLE_NAME, TABLE_NAME, "movieId", movieId, TABLE_NAME, "userEmail", userEmail);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                st.executeUpdate();
                con.close();
            } catch (SQLException i) {i.printStackTrace();}
        } catch (SQLException e) {e.printStackTrace();}
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
    protected void fillInsertValues(PreparedStatement st, WatchList data) throws SQLException {
        st.setString(1, data.getUserEmail());
        st.setLong(2, data.getMovieId());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format(Queries.SelectAll, TABLE_NAME);
    }

    @Override
    protected WatchList convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new WatchList(rs.getString(1), rs.getInt(2));
    }

    @Override
    protected ArrayList<WatchList> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<WatchList> watchLists = new ArrayList<>();
        while (rs.next()) {
            watchLists.add(this.convertResultSetToDomainModel(rs));
        }
        return watchLists;
    }

}