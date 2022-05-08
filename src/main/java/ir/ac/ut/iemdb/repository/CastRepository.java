package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.model.Cast;
import ir.ac.ut.iemdb.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CastRepository extends Repository<Cast, String>{
    private static final String TABLE_NAME = "Casts";
    private static CastRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static CastRepository getInstance() {
        if (instance == null) {
            try {
                instance = new CastRepository();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in CastRepository.create query.");
            }
        }
        return instance;
    }

    private CastRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format("CREATE TABLE IF NOT EXISTS %s(" +
                        "movieId int not null, \n" +
                        "actorId int not null,\n" +
                        "PRIMARY KEY(movieId, actorId),\n" +
                        "FOREIGN KEY (movieId) REFERENCES Movie(id),\n" +
                        "FOREIGN KEY (actorId) REFERENCES Actor(id));", TABLE_NAME)
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public List<Movie> getMovies(int actorId) throws SQLException {
        List<Movie> result = new ArrayList<Movie>();
        String statement = String.format("select %s from %s, %s\n" +
                        " where %s.actorId = %d and %s.movieId = %s.id;",
                MovieRepository.getCOLUMNS(), TABLE_NAME, MovieRepository.getTableName(), TABLE_NAME, actorId, TABLE_NAME, MovieRepository.getTableName());
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    result.add(MovieRepository.getInstance().convertResultSetToDomainModel(resultSet));}
                con.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll query.");
                throw ex;
            }
        }
    }

    public List<Actor> getCast(int movieId) throws SQLException {
        List<Actor> result = new ArrayList<Actor>();
        String statement = String.format("select %s from %s, %s\n" +
                        " where %s.movieId = %d and %s.actorId = %s.id;",
                ActorRepository.getCOLUMNS(), TABLE_NAME, ActorRepository.getTableName(), TABLE_NAME, movieId, TABLE_NAME, ActorRepository.getTableName());
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()){
                    result.add(ActorRepository.getInstance().convertResultSetToDomainModel(resultSet));}
                con.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll query.");
                throw ex;
            }
        }
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
        return String.format("INSERT IGNORE INTO %s(movieId, actorId) VALUES(?,?)", TABLE_NAME);
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Cast data) throws SQLException {
        st.setInt(1, data.getMovieId());
        st.setInt(2, data.getActorId());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format("SELECT * FROM %s;", TABLE_NAME);
    }

    @Override
    protected Cast convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Cast(rs.getInt(1), rs.getInt(2));
    }

    @Override
    protected ArrayList<Cast> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Cast> casts = new ArrayList<>();
        while (rs.next()) {
            casts.add(this.convertResultSetToDomainModel(rs));
        }
        return casts;
    }
}
