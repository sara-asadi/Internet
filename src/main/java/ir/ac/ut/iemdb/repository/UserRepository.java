package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.User;
import ir.ac.ut.iemdb.repository.connectionpool.ConnectionPool;
import ir.ac.ut.iemdb.tools.Queries.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository extends Repository<User, String> {
    private static final String TABLE_NAME = "User";
    private static final String COLUMNS= "email, password, nickname, name, birthDate";
    private static String currentUser;
    private static UserRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }
    public static String getCOLUMNS() {
        return COLUMNS;
    }
    public static String getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(String currentUser) {
        UserRepository.currentUser = currentUser;
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            try {
                instance = new UserRepository();
            } catch (SQLException ignored) {}
        }
        try {
        instance.insert(new User("guest", "1234", "guest", "guest", ""));
        currentUser = "guest";
        } catch (SQLException ignored) {}
        return instance;
    }

    private UserRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(Queries.createUser, TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format(Queries.SearchByOne, COLUMNS, TABLE_NAME, TABLE_NAME, "email");
    }
    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setString(1, id);
    }

    @Override
    protected String getInsertStatement() {
        return String.format(Queries.Insert, TABLE_NAME, COLUMNS, "?,?,?,?,?");
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
        return String.format(Queries.SelectAll, TABLE_NAME);
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new User(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5)
        );
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
