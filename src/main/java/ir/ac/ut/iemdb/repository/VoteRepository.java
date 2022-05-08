package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Cast;
import ir.ac.ut.iemdb.model.Vote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VoteRepository extends Repository<Vote, String>{
    private static final String TABLE_NAME = "VOTE";
    private static VoteRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static VoteRepository getInstance() {
        if (instance == null) {
            try {
                instance = new VoteRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in VoteRepository.create query.");
            }
        }
        return instance;
    }

    private VoteRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(" +
                        "    userEmail varchar(25) not null,\n" +
                        "    commentId int not null,\n" +
                        "    value int not null,\n" +
                        "    primary key (userEmail, commentId),\n" +
                        "    FOREIGN KEY (userEmail) REFERENCES User(email),\n" +
                        "    FOREIGN KEY (commentId) REFERENCES Comment(id)", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }
    protected String getFindAllVoteByCommentIdStatement(){
        return String.format("SELECT* FROM %s vote WHERE vote.commentId = ?;", TABLE_NAME);
    }

    protected void fillFindAllVoteByCommentIdValues(PreparedStatement st, String commentId) throws SQLException {
        st.setInt(1, Integer.parseInt(commentId));
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
        return String.format("INSERT INTO %s(userEmail, commentId, value) VALUES(?,?,?)", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Vote data) throws SQLException {
        st.setString(1, data.getUserEmail());
        st.setLong(2, data.getCommentId());
        st.setInt(3,data.getValue());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected Vote convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Vote(rs.getString(1), rs.getLong(2),rs.getInt(3));
    }

    @Override
    protected ArrayList<Vote> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Vote> votes = new ArrayList<>();
        while (rs.next()) {
            votes.add(this.convertResultSetToDomainModel(rs));
        }
        return votes;
    }
}
