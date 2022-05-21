package ir.ac.ut.iemdb.repository;


import ir.ac.ut.iemdb.model.Rate;
import ir.ac.ut.iemdb.model.Vote;
import ir.ac.ut.iemdb.repository.connectionpool.ConnectionPool;
import ir.ac.ut.iemdb.tools.Queries.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VotesRepository extends Repository<Vote, String> {
    private static final String TABLE_NAME = "Votes";
    private static final String COLUMNS = "commentId, userEmail, vote";

    private static VotesRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static VotesRepository getInstance() {
        if (instance == null) {
            try {
                instance = new VotesRepository();
            } catch (SQLException ignored) {}
        }
        return instance;
    }

    private VotesRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(String.format(Queries.createVotes, TABLE_NAME));
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public static int getLikes(int commentId) {
        String statement = String.format("select count(userEmail) from %s\n" +
                                        "where %s.commentId = %d and votes.vote = 1;",
                TABLE_NAME, TABLE_NAME, commentId);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                int likes = 0;
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    likes = Integer.parseInt(resultSet.getString(1));
                }
                con.close();
                return likes;
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        } catch (SQLException ignored) {
        }
        return -1;
    }

    public static int getDislikes(int commentId) {
        String statement = String.format("select count(userEmail) from %s\n" +
                                         "where %s.commentId = %d and votes.vote = -1;",
                                         TABLE_NAME, TABLE_NAME, commentId);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                int dislikes = 0;
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    dislikes = Integer.parseInt(resultSet.getString(1));
                }
                con.close();
                return dislikes;
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        } catch (SQLException ignored) {
        }
        return -1;
    }

    @Override
    protected String getInsertStatement() {
        return String.format(Queries.InsertUpdate, TABLE_NAME, COLUMNS, "?,?,?", "vote", "?");
    }
    @Override
    protected void fillInsertValues(PreparedStatement st, Vote data) throws SQLException {
        st.setInt(1, data.getCommentId());
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
    protected Vote convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Vote(rs.getInt(1), rs.getString(2), rs.getInt(3));
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
