package ir.ac.ut.iemdb.repository;


import ir.ac.ut.iemdb.model.Rate;
import ir.ac.ut.iemdb.repository.connectionpool.ConnectionPool;
import ir.ac.ut.iemdb.tools.Queries.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RatesRepository extends Repository<Rate, String> {
    private static final String TABLE_NAME = "Rates";
    private static final String COLUMNS = "movieId, userEmail, rate";

    private static RatesRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static RatesRepository getInstance() {
        if (instance == null) {
            try {
                instance = new RatesRepository();
            } catch (SQLException ignored) {}
        }
        return instance;
    }

    private RatesRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(String.format(Queries.createRates, TABLE_NAME));
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public String getRating(int movieId) {
        String statement = String.format("select avg(rate) from %s\n" +
                                         "where %s.movieId = %d;",
                                         TABLE_NAME, TABLE_NAME, movieId);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                Object rating = null;
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    rating = resultSet.getObject(1);
                }
                con.close();
                if (rating == null)
                    return "0";
                return rating.toString();
            } catch (SQLException ignored) {}
        } catch (SQLException ignored) {}
        return "0";
    }
    public int getRatingCount(int movieId) {
        String statement = String.format("select count(userEmail) from %s\n" +
                                         "where %s.movieId = %d;",
                                         TABLE_NAME, TABLE_NAME, movieId);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                int ratingCount = 0;
                resultSet = st.executeQuery();
                while (resultSet.next()){
                     ratingCount = Integer.parseInt(resultSet.getString(1));
                }
                con.close();
                return ratingCount;
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        } catch (SQLException ignored) {
        }
        return -1;
    }

    @Override
    protected String getInsertStatement() {
        return String.format(Queries.InsertUpdate, TABLE_NAME, COLUMNS, "?,?,?", "rate", "?");
    }
    @Override
    protected void fillInsertValues(PreparedStatement st, Rate data) throws SQLException {
        st.setInt(1, data.getMovieId());
        st.setString(2, data.getUserEmail());
        st.setInt(3, data.getValue());
        st.setInt(4, data.getValue());
    }

    @Override
    protected String getFindByIdStatement() {
        return null;
    }
    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
    }

    @Override
    protected String getFindAllStatement() {
        return null;
    }

    @Override
    protected Rate convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Rate(rs.getInt(1), rs.getString(2), rs.getInt(3));
    }

    @Override
    protected ArrayList<Rate> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Rate> rates = new ArrayList<>();
        while (rs.next()) {
            rates.add(this.convertResultSetToDomainModel(rs));
        }
        return rates;
    }
}
