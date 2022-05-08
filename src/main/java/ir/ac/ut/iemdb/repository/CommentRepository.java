package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentRepository extends Repository<Comment, String> {
    private static final String TABLE_NAME = "Comments";
    private static CommentRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static CommentRepository getInstance() {
        if (instance == null) {
            try {
                instance = new CommentRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in CommentRepository.create query.");
            }
        }
        return instance;
    }

    private CommentRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(" +
                        "    id long NOT NULL AUTO_INCREMENT,\n" +
                        "    userEmail varchar(25) NOT NULL,\n" +
                        "    movieId long not null,\n" +
                        "    commentText text,\n" +
                        "    PRIMARY KEY (id),\n" +
                        "    FOREIGN KEY (userEmail) REFERENCES %s(userEmail),\n" +
                        "    FOREIGN KEY (movieId) REFERENCES %s(movieId))", TABLE_NAME, "Movie", "User"/*UserRepository.getInstance().getTableName(), FilmRepository.getInstance().getTableName()*/
                )
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format("SELECT* FROM %s comment WHERE comment.id = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setLong(1, Long.parseLong(id));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT INTO %s(id, userEmail, movieId, commentText) VALUES(?,?,?,?)", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Comment data) throws SQLException {
        st.setLong(1, data.getId());
        st.setString(2, data.getUserEmail());
        st.setLong(3, data.getMovieId());
        st.setString(4, data.getCommentText());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected Comment convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Comment(rs.getLong(1), rs.getString(2), rs.getLong(3), rs.getString(4));
    }

    @Override
    protected ArrayList<Comment> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Comment> comments = new ArrayList<>();
        while (rs.next()) {
            comments.add(this.convertResultSetToDomainModel(rs));
        }
        return comments;
    }

    protected String getFindByMovieStatement() {
        return String.format("SELECT * FROM %s comment WHERE comment.movieId = ?;", TABLE_NAME);
    }

    protected void fillFindByMovieValues(PreparedStatement st, String id) throws SQLException {
        st.setLong(1, Long.parseLong(id));
    }
}
