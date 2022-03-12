<%@page import="app.db.MovieDB"%>
<%@page import="app.model.Movie"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Movies</title>
    <style>
      li, td, th {
        padding: 5px;
      }
    </style>
</head>
<body>
       <a href="index.jsp">Home</a>
       <br><br>
       <form action="" method="POST">
           <label>Search:</label>
           <input type="text" name="search" value="">
           <button type="submit" name="action" value="search">Search</button>
           <button type="submit" name="action" value="clear">Clear Search</button>
       </form>
       <br><br>
       <form action="" method="POST">
           <label>Sort By:</label>
           <button type="submit" name="action" value="sort_by_imdb">imdb Rate</button>
           <button type="submit" name="action" value="sort_by_date">releaseDate</button>
       </form>
       <br>
       <%
       		List<Movie> movies = new ArrayList<>();
       		movies = MovieDB.getInstance().getMovies();
       	%>
       <table>
           		<c:forEach items="${movies}" var="movie">
                   <p${movie.name} - ${movie.summary}</p>
                </c:forEach>
       </table>
    </body>
</html>
