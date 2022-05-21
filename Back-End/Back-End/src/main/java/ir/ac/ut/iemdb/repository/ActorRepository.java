package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.repository.connectionpool.ConnectionPool;
import ir.ac.ut.iemdb.tools.Queries.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActorRepository extends Repository<Actor, String> {
    private static final String TABLE_NAME = "Actor";
    private static final String COLUMNS = "id, name, birthDate, nationality, image";

    private static ActorRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }
    public static String getCOLUMNS() {
        return COLUMNS;
    }

    public static ActorRepository getInstance() {
        if (instance == null) {
            try {
                instance = new ActorRepository();
            } catch (SQLException ignored) { }
        }
        return instance;
    }

    private ActorRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(Queries.createActor, TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format(Queries.SearchByOne,COLUMNS, TABLE_NAME, TABLE_NAME, "id");
    }
    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setInt(1, Integer.parseInt(id));
    }

    @Override
    protected String getInsertStatement() {
        return String.format(Queries.Insert, TABLE_NAME, COLUMNS, "?,?,?,?,?");
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
        return String.format(Queries.SelectAll, TABLE_NAME);
    }

    @Override
    protected Actor convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Actor(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5)
        );
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
