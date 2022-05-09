package ir.ac.ut.iemdb.tools.Queries;

public class Queries {
    public static final String SelectAll    = "SELECT * FROM %s;";

    public static final String SearchByOne  = "SELECT %s FROM %s " +
                                              "WHERE %s.%s = ?;";

    public static final String SearchByInt  = "SELECT %s FROM %s " +
                                              "WHERE %s.%s = %d;";

    public static final String SearchByTwo  = "SELECT %s FROM %s, %s\n" +
                                              "WHERE %s.%s = %d and %s.%s = %s.%s;";

    public static final String SearchByTwoS = "SELECT %s FROM %s, %s\n" +
                                              "WHERE %s.%s = \"%s\" and %s.%s = %s.%s;";

    public static final String Delete       = "delete from %s\n" +
                                              "where %s.%s = %d and %s.%s = %s;";

    public static final String Insert       = "INSERT IGNORE INTO %s(%s) VALUES(%s);";

    public static final String InsertUpdate = "INSERT IGNORE INTO %s(%s) VALUES(%s)" +
                                              "ON DUPLICATE KEY UPDATE %s=%s;";

    public static final String Sort         = "SELECT %s FROM %s\n" +
                                              "ORDER BY %s DESC;";

    public static final String createActor  = "CREATE TABLE IF NOT EXISTS %s(" +
                                                "id  int not null,\n" +
                                                "name  varchar(20) not null,\n" +
                                                "birthDate varchar(20),\n" +
                                                "nationality varchar(20),\n" +
                                                "image varchar(500),\n" +
                                                "PRIMARY KEY(id)" +
                                              ");";

    public static final String createCast   = "CREATE TABLE IF NOT EXISTS %s(" +
                                                "movieId int not null, \n" +
                                                "actorId int not null,\n" +
                                                "PRIMARY KEY(movieId, actorId),\n" +
                                                "FOREIGN KEY (movieId) REFERENCES %s(id),\n" +
                                                "FOREIGN KEY (actorId) REFERENCES %s(id)" +
                                                ");";

    public static final String createComment= "CREATE TABLE IF NOT EXISTS %s(" +
                                                "id int NOT NULL auto_increment,\n" +
                                                "userEmail varchar(25) NOT NULL,\n" +
                                                "movieId int not null,\n" +
                                                "commentText varchar(200) not null,\n" +
                                                "PRIMARY KEY (id),\n" +
                                                "unique(userEmail, movieId, commentText),\n" +
                                                "FOREIGN KEY (userEmail) REFERENCES %s(email),\n" +
                                                "FOREIGN KEY (movieId) REFERENCES %s(id)" +
                                                ");";

    public static final String createGenre  = "CREATE TABLE IF NOT EXISTS %s(" +
                                                "movieId int not null,\n" +
                                                "genre varchar(20) not null,\n" +
                                                "PRIMARY KEY(movieId,genre),"+
                                                "FOREIGN KEY (movieId) REFERENCES %s(id));";

    public static final String createMovie  = "CREATE TABLE IF NOT EXISTS %s(" +
                                                "id int not null,\n" +
                                                "name varchar(20) not null,\n" +
                                                "summary varchar(1000),\n" +
                                                "releaseDate varchar(20),\n" +
                                                "director varchar(20),\n" +
                                                "writers varchar(200),\n" +
                                                "imdbRate double,\n" +
                                                "duration int,\n" +
                                                "ageLimit int,\n" +
                                                "image varchar(250),\n" +
                                                "coverImage varchar(250),\n" +
                                                "PRIMARY KEY(id)" +
                                                ");";

    public static final String createUser  = "CREATE TABLE IF NOT EXISTS %s(\n" +
                                                "email varchar(25) not null,\n" +
                                                "password varchar(20) not null,\n" +
                                                "nickname varchar(20),\n" +
                                                "name  varchar(20),\n" +
                                                "birthDate varchar(10),\n" +
                                                "PRIMARY KEY(email)" +
                                                ");";

    public static final String createRates  ="CREATE TABLE IF NOT EXISTS %s(\n" +
                                                "movieId int not null,\n" +
                                                "userEmail varchar(25) not null,\n" +
                                                "rate int not null,\n" +
                                                "primary key (movieId, userEmail),\n" +
                                                "FOREIGN KEY (userEmail) REFERENCES User(email),\n" +
                                                "FOREIGN KEY (movieId) REFERENCES Movie(id)" +
                                                ");";

    public static final String createVotes  = "CREATE TABLE IF NOT EXISTS %s(" +
                                                "commentId int NOT NULL,\n" +
                                                "userEmail varchar(25) NOT NULL,\n" +
                                                "vote int not null,\n" +
                                                "PRIMARY KEY (commentId, userEmail),\n" +
                                                "FOREIGN KEY (userEmail) REFERENCES User(email)ON DELETE CASCADE,\n" +
                                                "FOREIGN KEY (commentId) REFERENCES Comments(id)ON DELETE CASCADE" +
                                                ");";

    public static final String createWatchList= "CREATE TABLE IF NOT EXISTS %s(" +
                                                    "movieId int not null,\n" +
                                                    "userEmail varchar(25) not null,\n" +
                                                    "primary key (movieId, userEmail),\n" +
                                                    "FOREIGN KEY (userEmail) REFERENCES User(email),\n" +
                                                    "FOREIGN KEY (movieId) REFERENCES Movie(id)" +
                                                    ");";

    public Queries() {}
}
