package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.IEMDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IEMDBRepository extends Repository<IEMDB, String> {
    private static final String TABLE_NAME = "IEMDB_TABLE";
    private static IEMDBRepository instance;

    public static IEMDBRepository getInstance() {
        if (instance == null) {
            try {
                instance = new IEMDBRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in IEMDBRepository.create query.");
            }
        }
        return instance;
    }

    private IEMDBRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(id CHAR(50),\nname CHAR(225),\nactor CHAR(225),\nPRIMARY KEY(id));", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format("SELECT* FROM %s iemdb WHERE iemdb.id = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setString(1, id);
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT INTO %s(id, name, actor) VALUES(?,?,?)", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, IEMDB data) throws SQLException {
        st.setString(1, data.getId());
        st.setString(2, data.getName());
        st.setString(3, data.getActor());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected IEMDB convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new IEMDB(rs.getString(1), rs.getString(2), rs.getString(3));
    }

    @Override
    protected ArrayList<IEMDB> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<IEMDB> iemdbs = new ArrayList<>();
        while (rs.next()) {
            iemdbs.add(this.convertResultSetToDomainModel(rs));
        }
        return iemdbs;
    }
}
