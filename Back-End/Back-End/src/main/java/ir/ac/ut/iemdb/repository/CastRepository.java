package ir.ac.ut.iemdb.repository;

import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.model.Cast;
import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.repository.connectionpool.ConnectionPool;
import ir.ac.ut.iemdb.tools.Queries.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CastRepository extends Repository<Cast, String>{
    private static final String TABLE_NAME = "Casts";
    private static final String COLUMNS = "movieId, actorId";
    private static CastRepository instance;

    public static String getTableName() {
        return TABLE_NAME;
    }
    public static String getCOLUMNS() {
        return COLUMNS;
    }

    public static CastRepository getInstance() {
        if (instance == null) {
            try {
                instance = new CastRepository();
            } catch (SQLException ignored) {}
        }
        return instance;
    }

    private CastRepository() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(Queries.createCast, TABLE_NAME, MovieRepository.getTableName(), ActorRepository.getTableName())
        );
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public List<Movie> getMovies(int actorId) {
        List<Movie> result = new ArrayList<Movie>();
        String statement = String.format(Queries.SearchByTwo, MovieRepository.getCOLUMNS(), TABLE_NAME, MovieRepository.getTableName(), TABLE_NAME, "actorId", actorId, TABLE_NAME, "movieId", MovieRepository.getTableName(), "id");
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
            } catch (SQLException ignored) {}
        } catch (SQLException ignored) {}
        return result;
    }

    public List<Actor> getCast(int movieId) {
        List<Actor> result = new ArrayList<Actor>();
        String statement = String.format(Queries.SearchByTwo, ActorRepository.getCOLUMNS(), TABLE_NAME, ActorRepository.getTableName(), TABLE_NAME, "movieId", movieId, TABLE_NAME, "actorId", ActorRepository.getTableName(), "id");
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
            } catch (SQLException ignored) {}
        } catch (SQLException ignored) {}
        return result;
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
        return String.format(Queries.Insert, TABLE_NAME, COLUMNS, "?,?");
    }
    @Override
    protected void fillInsertValues(PreparedStatement st, Cast data) throws SQLException {
        st.setInt(1, data.getMovieId());
        st.setInt(2, data.getActorId());
    }

    @Override
    protected String getFindAllStatement() {
        return String.format(Queries.SelectAll, TABLE_NAME);
    }

    @Override
    protected Cast convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Cast(
                rs.getInt(1),
                rs.getInt(2)
        );
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
