package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Actor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActorRepository extends Repository<Actor, String> {
    private static final String TABLE_NAME = "Actor";
    private static ActorRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static ActorRepository getInstance() {
        if (instance == null) {
            try {
                instance = new ActorRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in ACTORRepository.create query.");
            }
        }
        return instance;
    }

    private ActorRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(id  int not null,\n" +
                        "    name  varchar(20) not null,\n" +
                        "    birthDate varchar(20),\n" +
                        "    nationality varchar(20),\n" +
                        "    image varchar(500),\n" +
                        "    PRIMARY KEY(id));", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format("SELECT* FROM %s actor WHERE actor.id = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setInt(1, Integer.parseInt(id));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s(id, name, birthDate, nationality, image) VALUES(?,?,?,?,?);", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Actor data) throws SQLException {
        st.setInt(1, data.getId());
        st.setString(2, data.getName());
        st.setString(3, data.getBirthDate());
        st.setString(4, data.getNationality());
        st.setString(5, data.getImage());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected Actor convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Actor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
    }

    @Override
    protected ArrayList<Actor> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Actor> actors = new ArrayList<>();
        while (rs.next()) {
            actors.add(this.convertResultSetToDomainModel(rs));
        }
        return actors;
    }
}
