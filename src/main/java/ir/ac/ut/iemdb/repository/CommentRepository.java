package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository extends Repository<Comment, String> {
    private static final String TABLE_NAME = "Comments";
    private static final String COLUMNS = "id, userEmail, movieId, commentText";
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
                e.printStackTrace();
                System.out.println("error in CommentRepository.create query.");
            }
        }
        return instance;
    }

    private CommentRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(
                        "    CREATE TABLE IF NOT EXISTS %s(id int NOT NULL auto_increment,\n" +
                        "    userEmail varchar(25) NOT NULL,\n" +
                        "    movieId int not null,\n" +
                        "    commentText varchar(200) not null,\n" +
                        "    PRIMARY KEY (id),\n" +
                        "    FOREIGN KEY (userEmail) REFERENCES %s(email),\n" +
                        "    FOREIGN KEY (movieId) REFERENCES %s(id))",
                        TABLE_NAME, UserRepository.getTableName(),MovieRepository.getTableName()));
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format("SELECT %s FROM %s comment WHERE comment.id = ?;", COLUMNS, TABLE_NAME);
    }

    @Override
    protected void fillFindByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setInt(1, Integer.parseInt(id));
    }

    @Override
    protected String getInsertStatement() {
        return String.format("INSERT IGNORE INTO %s(userEmail, movieId, commentText) VALUES(?,?,?)", TABLE_NAME);
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
        String statement = String.format("select %s from %s\n" +
                        " where %s.movieId = %d;",
                COLUMNS, TABLE_NAME, TABLE_NAME, movieId);
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
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll query.");
                throw ex;
            }
        }
    }

    @Override
    protected Comment convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Comment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
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
