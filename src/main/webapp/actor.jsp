<%@page import="app.db.MovieDB"%>
<%@page import="app.db.UserDB"%>
<%@page import="app.db.ActorDB"%>
<%@page import="app.model.Movie"%>
<%@page import="app.model.User"%>
<%@page import="app.model.Actor"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Movie</title>
    <style>
      li, td, th {
        padding: 5px;
      }
    </style>
  </head>
  <%
    long id = (long)request.getAttribute("id");
    Actor actor = ActorDB.getInstance().getActorById(id);
  %>
  <body>
    <a href="home">Home</a>
    <p id="email">email: <%=UserDB.getInstance().getCurrentUser().getEmail()%></p>
    <ul>
        <li id="name">name: <%=actor.getName()%></li>
        <li id="birthDate">birthDate: <%=actor.getBirthDate()%></li>
        <li id="nationality">nationality: <%=actor.getNationality()%></li>
        <li id="tma">Total movies acted in: <%=actor.getMovies().size()%></li>
    </ul>
    <p><%
    List<Movie> movies = actor.getMovies();
    %></p>
    <table>
            <tr>
                <th>Movie</th>
                <th>imdb Rate</th>
                <th>rating</th>
            </tr>
        <% for(int i = 0; i < movies.size(); i+=1) { %>
            <tr>
                <td><a href="../movies/<%=movies.get(i).getId()%>"><%=movies.get(i).getName()%><a></td>
                <td><%=movies.get(i).getImdbRate()%></td>
                <td><%=movies.get(i).getRating()%></td>
            </tr>
        <% } %>
    </table>
</body>
</html>