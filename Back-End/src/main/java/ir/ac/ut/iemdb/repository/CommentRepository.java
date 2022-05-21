package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Comment;
import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.repository.connectionpool.ConnectionPool;
import ir.ac.ut.iemdb.tools.Queries.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository extends Repository<Comment, String> {
    private static final String TABLE_NAME = "Comments";
    private static final String COLUMNS = "id, userEmail, movieId, commentText";
    private static final String COLUMNS_INIT = "userEmail, movieId, commentText";

    private static CommentRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }
    public static String getCOLUMNS() {
        return COLUMNS;
    }

    public static CommentRepository getInstance() {
        if (instance == null) {
            try {
                instance = new CommentRepository();
            } catch (SQLException e) {
                //e.printStackTrace();
                //System.out.println("error in CommentRepository.create query.");
            }
        }
        return instance;
    }

    private CommentRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(Queries.createComment, TABLE_NAME, UserRepository.getTableName(),MovieRepository.getTableName()));
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format(Queries.SearchByOne, COLUMNS, TABLE_NAME, TABLE_NAME, "id");
    }
    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setInt(1, Integer.parseInt(id));
    }

    @Override
    protected String getInsertStatement() {
        return String.format(Queries.Insert, TABLE_NAME, COLUMNS_INIT, "?,?,?");
    }
    @Override
    protected void fillInsertValues(PreparedStatement st, Comment data) throws SQLException {
        st.setString(1, data.getUserEmail());
        st.setInt(2, data.getMovieId());
        st.setString(3, data.getText());
    }

    @Override
    protected String getFindAllStatement() {
        return null;
    }

    public List<Comment> findByMovieId(int movieId) throws SQLException {
        List<Comment> result = new ArrayList<Comment>();
        String statement = String.format(Queries.SearchByInt,COLUMNS, TABLE_NAME, TABLE_NAME, "movieId", movieId);
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    result.add(convertResultSetToDomainModel(resultSet));}
                con.close();
                return result;
            } catch (SQLException ignored) {}
        } catch (SQLException ignored) {}
        return result;
    }

    @Override
    protected Comment convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Comment(
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getString(4),
                VotesRepository.getLikes(rs.getInt(1)),
                VotesRepository.getDislikes(rs.getInt(1))
        );
    }

    @Override
    protected ArrayList<Comment> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Comment> comments = new ArrayList<>();
        while (rs.next()) {
            comments.add(this.convertResultSetToDomainModel(rs));
        }
        return comments;
    }
}
