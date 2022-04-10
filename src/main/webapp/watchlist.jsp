<%@page import="app.db.MovieDB"%>
<%@page import="app.db.ActorDB"%>
<%@page import="app.db.UserDB"%>
<%@page import="app.model.User"%>
<%@page import="app.model.Movie"%>
<%@page import="app.model.Actor"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
    <head>
    <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <title>WatchList</title>
    </head>
    <body>
        <nav class="navbar default-navbar">
              <div class="container-fluid">
                <div class="navbar-header navbar-left">
                   <a href="home"><img src="images/template.png" class="logo"></a>
                </div>
                <!--div class="nav navbar-nav navbar-right col-md-1 user">
                  <li><a href="#"><span class="glyphicon glyphicon-user user-icon"></span></a></li>
                </div-->
              </div>
            </nav>
        <% User user = UserDB.getInstance().getCurrentUser(); %>
        <% List<Movie> movies = user.getWatchListMovies(); %>
        <%List<Movie> recoms = user.getRecommendationList();%>
        <div class="container">
            <div class="row">
                <div class="watch-list col-md-6">
                    <% for(int i = 0; i < movies.size(); i+=1) { %>
                        <div class="watch-list-item row">
                            <div class="col-md-3">
                                <a href="movies/<%=movies.get(i).getId()%>">
                                    <img src="images/movies/<%=movies.get(i).getName()%>.jpg" alt="<%=movies.get(i).getName()%>" class="poster">
                                </a>
                            </div>
                            <div class="film-title col-md-3">
                                <p><%=movies.get(i).getName()%></p>
                            </div>
                            <div class="rate col-md-3">
                                <row>
                                <!--span>امتیاز imdb:</span-->
                                    <%=movies.get(i).getImdbRate()%>
                                </row>
                                <row class="row">
                                <!--span>امتیاز کاربران:</span-->
                                    <%=movies.get(i).getRating()%>
                                </row>
                            </div>
                            <div class="info col-md-3">
                                <row>
                                    <form action="remove_from_watchlist" method="POST" >
                                        <input id="form_movie_id" type="hidden" name="movie_id" value="<%=movies.get(i).getId()%>">
                                        <button class="btn btn-link" type="submit"><i class="glyphicon glyphicon-trash"></i></button>
                                    </form>
                                </row>
                                <row><%=movies.get(i).getReleaseDate()%></row>
                                <row><%=movies.get(i).getDirector()%></row>
                                <row><%=movies.get(i).getGenres()%></row>
                                <row><%=movies.get(i).getDuration()%></row>
                            </div>
                        </div>
                    <% } %>
                </div>
            </div>
            <div class="row">
               <div class="recommends">
                   <div class="recommend-movie col-md-7">
                       <% for(int i = 0; i < recoms.size(); i+=1) { %>
                       <a href="movies/<%=recoms.get(i).getId()%>"><img src="images/movies/<%=recoms.get(i).getName()%>.jpg" alt="<%=recoms.get(i).getName()%>" class="poster"><a>
                       <% } %>
                   </div>
               </div>
            </div>
        </div>
    </body>
</html>
