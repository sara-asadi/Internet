package ir.ac.ut.iemdb.repository;


import ir.ac.ut.iemdb.model.Rate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RatesRepository extends Repository<Rate, String> {
    private static final String TABLE_NAME = "Rates";
    private static RatesRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static RatesRepository getInstance() {
        if (instance == null) {
            try {
                instance = new RatesRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in RatesRepository.create query.");
            }
        }
        return instance;
    }

    private RatesRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(" +
                        "    movieId int not null,\n" +
                        "    userEmail varchar(25) not null,\n" +
                        "    rateValue int not null, \n" +
                        "    primary key (movieId, userEmail),\n" +
                        "    FOREIGN KEY (userEmail) REFERENCES User(email),\n" +
                        "    FOREIGN KEY (movieId) REFERENCES Movie(id))", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    protected String getFindStatement() {
        return String.format("SELECT * FROM %s rates WHERE rates.userEmail = ? and rates.movieId = ?;", TABLE_NAME);
    }

    protected void fillFindValues(PreparedStatement st, String userEmail, Integer movieId) throws SQLException {
        st.setString(1, userEmail);
        st.setInt(2, movieId);
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
        return String.format("INSERT INTO %s(movieId, userEmail, value) VALUES(?,?,?)", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Rate data) throws SQLException {
        st.setInt(1, data.getMovieId());
        st.setString(2, data.getUserEmail());
        st.setInt(3, data.getValue());
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
