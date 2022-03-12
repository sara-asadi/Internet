<%@page import="app.db.MovieDB"%>
<%@page import="app.db.UserDB"%>
<%@page import="app.model.Movie"%>
<%@page import="app.model.Comment"%>
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
    Movie movie = new Movie();
    movie = MovieDB.getInstance().getMovieById(id);
  %>
  <body>
    <a href="/">Home</a>

    <ul>
      <li id="name">name: <%=movie.getName()%></li>
      <li id="summary">
        summary: <%=movie.getSummary()%>
      </li>
      <li id="releaseDate">releaseDate: <%=movie.getReleaseDate()%></li>
      <li id="director">director: <%=movie.getDirector()%></li>
      <li id="writers">writers: <%=movie.getWriters()%></li>
      <li id="genres">genres: <%=movie.getGenres()%></li>
      <li id="imdbRate">imdb Rate: <%=movie.getImdbRate()%></li>
      <li id="rating">rating: <%=movie.getRating()%></li>
      <li id="duration">duration: <%=movie.getDuration()%> minutes</li>
      <li id="ageLimit">ageLimit: <%=movie.getAgeLimit()%></li>
    </ul>
    <h3>Cast</h3>
    <table>
      <tr>
        <th>name</th>
        <th>age</th>
      </tr>
      <%
                List<Actor> actors = new ArrayList<>();
                actors = movie.getCast();
                for(int j = 0; j < actors.size(); j+=1) { %>
      <tr>
        <td>
        <a href="actors/<%=actors.get(j).getId()%>"><%=actors.get(j).getName()%></a>
        </td>
        <td>
        <p><%=actors.get(j).getAge()%></p>
        </td>
      </tr>
      <% } %>
    </table>
    <br>
    <form action="" method="POST">
      <label>Rate(between 1 and 10):</label>
      <input type="number" id="quantity" name="quantity" min="1" max="10">
      <input type="hidden" id="form_action" name="action" value="rate">
      <input type="hidden" id="form_movie_id" name="movie_id" value="01">
      <button type="submit">rate</button>
    </form>
    <br>
    <form action="" method="POST">
      <input type="hidden" id="form_action" name="action" value="add">
      <input type="hidden" id="form_movie_id" name="movie_id" value="01">
      <button type="submit">Add to WatchList</button>
    </form>
    <br />
    <table>
      <tr>
        <th>nickname</th>
        <th>comment</th>
        <th></th>
        <th></th>
      </tr>
      <%
      List<Comment> comments = new ArrayList<>();
      comments = movie.getComments();
      for(int j = 0; j < comments.size(); j+=1) { %>
        <tr>
          <td>
          <p><%=UserDB.getInstance().getUserByEmail(comments.get(j).getUserEmail()).getNickname()%></a>
          </td>
          <td>
          <p><%=comments.get(j).getText()%></p>
          </td>
          <td>
            <form action="" method="POST">
              <label for=""><%=comments.get(j).getLikes()%></label>
              <input
                id="form_comment_id"
                type="hidden"
                name="comment_id"
                value="<%=comments.get(j).getId()%>"
              />
              <input type="hidden" id="form_action" name="action" value="like">
              <input type="hidden" id="form_movie_id" name="movie_id" value="<%=id%>">
              <button type="submit">like</button>
            </form>
          </td>
          <td>
            <form action="" method="POST">
              <label for=""><%=comments.get(j).getDisLikes()%></label>
              <input
                id="form_comment_id"
                type="hidden"
                name="comment_id"
                value="<%=comments.get(j).getId()%>"
              />
              <input type="hidden" id="form_action" name="action" value="dislike">
              <input type="hidden" id="form_movie_id" name="movie_id" value="<%=id%>">
              <button type="submit">dislike</button>
            </form>
          </td>
        </tr>
        <% } %>
      </table>
    <br><br>
    <form action="" method="POST">
      <label>Your Comment:</label>
      <input type="text" name="comment" value="">
      <input type="hidden" id="form_action" name="action" value="comment">
      <input type="hidden" id="form_movie_id" name="movie_id" value="<%=id%>">
      <button type="submit">Add Comment</button>
    </form>
  </body>
</html>
