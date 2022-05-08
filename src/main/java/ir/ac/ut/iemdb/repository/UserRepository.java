package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository extends Repository<User, String> {
    private static final String TABLE_NAME = "User";
    private static UserRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            try {
                instance = new UserRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in UserRepository.create query.");
            }
        }
        return instance;
    }

    private UserRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(\n" +
                        "    email varchar(25) not null,\n" +
                        "    password varchar(20) not null,\n" +
                        "    nickname varchar(20),\n" +
                        "    name  varchar(20),\n" +
                        "    birthDate date,\n" +
                        "    PRIMARY KEY(email))", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format("SELECT* FROM %s user WHERE user.id = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setString(1, id);
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s(email, password, nickname, name, birthDate) VALUES(?,?,?,?,?)", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, User data) throws SQLException {
        st.setString(1, data.getEmail());
        st.setString(2, data.getPassword());
        st.setString(3, data.getNickname());
        st.setString(4, data.getName());
        st.setString(5, data.getBirthDate());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
    }

    @Override
    protected ArrayList<User> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(this.convertResultSetToDomainModel(rs));
        }
        return users;
    }
}
