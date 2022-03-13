<%@page import="app.db.MovieDB"%>
<%@page import="app.db.ActorDB"%>
<%@page import="app.db.UserDB"%>
<%@page import="app.model.User"%>
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
    <% User user = UserDB.getInstance().getCurrentUser(); %>
    <p id="email">email: <%=user.getEmail()%></p>
    <ul>
        <li id="name">name: <%=user.getName()%></li>
        <li id="nickname">nickname: @<%=user.getNickname()%></li>
        <li id="age">age: <%=user.getAge()%></li>
    </ul>
    <h2>Watch List</h2>
       <p><%
       		List<Movie> movies = user.getWatchListMovies();
       %></p>
       <% if(movies.size()==0){%>
       <p>your watchlist is empty!</P>
       <% } else {%>
       <table>
                <tr>
                   <th>name</th>
                   <th>releaseDate</th>
                   <th>director</th>
                   <th>genres</th>
                   <th>imdb Rate</th>
                   <th>rating</th>
                   <th>duration</th>
                   <th></th>
               </tr>
            <% for(int i = 0; i < movies.size(); i+=1) { %>
                <tr>
                    <td><a href="movies/<%=movies.get(i).getId()%>"><%=movies.get(i).getName()%><a></td>
                    <td><%=movies.get(i).getReleaseDate()%></td>
                    <td><%=movies.get(i).getDirector()%></td>
                    <td><%=movies.get(i).getGenres()%></td>
                    <td><%=movies.get(i).getImdbRate()%></td>
                    <td><%=movies.get(i).getRating()%></td>
                    <td><%=movies.get(i).getDuration()%></td>
                    <td>
                        <form action="remove_from_watchlist" method="POST" >
                            <input id="form_movie_id" type="hidden" name="movie_id" value="<%=movies.get(i).getId()%>">
                            <button type="submit">Remove</button>
                        </form>
                    </td>
                </tr>
            <% } %>
       </table>
       <% } %>
       <h2>Recommendation List</h2>
       <%List<Movie> recoms = user.getRecommendationList();%>
              <% if(recoms.size()==0){%>
              <p>no recommendations available!</P>
              <% } else {%>
              <table>
                       <tr>
                          <th>Movie</th>
                          <th>imdb Rate</th>
                      </tr>
                   <% for(int i = 0; i < recoms.size(); i+=1) { %>
                       <tr>
                           <td><a href="movies/<%=recoms.get(i).getId()%>"><%=recoms.get(i).getName()%><a></td>
                           <td><%=recoms.get(i).getImdbRate()%></td>
                       </tr>
                   <% } %>
              </table>
              <% } %>
    </body>
</html>
