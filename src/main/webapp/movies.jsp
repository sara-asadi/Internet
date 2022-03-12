<%@page import="app.db.MovieDB"%>
<%@page import="app.model.Movie"%>
<%@page import="app.model.Actor"%>
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
       <p><%
       		List<Movie> movies = new ArrayList<>();
       		movies = MovieDB.getInstance().getMovies();
       %></p>
       <table>
                <tr>
                   <th>name</th>
                   <th>summary</th>
                   <th>releaseDate</th>
                   <th>director</th>
                   <th>writers</th>
                   <th>genres</th>
                   <th>cast</th>
                   <th>imdb Rate</th>
                   <th>rating</th>
                   <th>duration</th>
                   <th>ageLimit</th>
               </tr>
            <% for(int i = 0; i < movies.size(); i+=1) { %>
                <tr>
                    <td><a href="/movies"><%=movies.get(i).getName()%><a></td>
                    <td><%=movies.get(i).getSummary()%></td>
                    <td><%=movies.get(i).getReleaseDate()%></td>
                    <td><%=movies.get(i).getDirector()%></td>
                    <td><%=movies.get(i).getWriters()%></td>
                    <td><%=movies.get(i).getGenres()%></td>
                    <td> <%
                    List<Actor> actors = new ArrayList<>();
                    actors = movies.get(i).getCast();
                    for(int j = 0; j < actors.size(); j+=1) { %>
                    <a href="/actors"><%=actors.get(j).getName()%></a>,
                    <% } %>...
                    </td>
                    <td><%=movies.get(i).getImdbRate()%></td>
                    <td><%=movies.get(i).getRating()%></td>
                    <td><%=movies.get(i).getDuration()%></td>
                    <td><%=movies.get(i).getAgeLimit()%></td>
                </tr>
            <% } %>
       </table>
    </body>
</html>
