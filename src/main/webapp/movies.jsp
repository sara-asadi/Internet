<%@page import="app.db.MovieDB"%>
<%@page import="app.model.Movie"%>
<%@page import="app.model.Actor"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Movies</title>
</head>
<body>
    <nav class="navbar default-navbar">
      <div class="container-fluid">
        <div class="navbar-header navbar-left col-md-2">
           <a href="home"><img src="images/template.png" class="logo"></a>
        </div>
        <ul class="nav navbar-form col-md-2">
          <li class="dropdown">
              <button class="btn dropdown-toggle" type="button" data-toggle="dropdown">جستجو بر اساس
              <span class="caret"></span></button>
              <ul class="dropdown-menu">
                <li><a href="#">Page 1-1</a></li>
                <li><a href="#">Page 1-2</a></li>
                <li><a href="#">Page 1-3</a></li>
              </ul>
            </li>
        </ul>
        <form action="movies" method="POST" class="navbar-form col-md-3" >
          <div class="input-group">
            <input type="text" class="form-control" placeholder="Search" name="search">
            <div class="input-group-btn">
              <button class="btn btn-search" type="submit" name="action" value="search">
                <i class="glyphicon glyphicon-search"></i>
              </button>
            </div>
          </div>
        </form>
        <!--div class="nav navbar-nav navbar-right col-md-1 user">
          <li><a href="#"><span class="glyphicon glyphicon-user user-icon"></span></a></li>
        </div-->
      </div>
    </nav>
    <%
        List<Movie> movies = new ArrayList<>();
        movies = MovieDB.getInstance().getMoviesF();
    %>
    <div class="container">
        <div class="movies col-md-8">
            <% for(int i = 0; i < movies.size(); i+=1) { %>
            <a href="movies/<%=movies.get(i).getId()%>"><img src="images/movies/<%=movies.get(i).getName()%>.jpg" alt="<%=movies.get(i).getName()%>" class="poster"><a>
            <% } %>
        </div>
        <span class="col-md-4 sort-bar">
            <label class="title">:رتبه بندی بر اساس</label>
            <div class="sort">
                <form action="movies" method="POST" class="sort-form">
                   <button type="submit" name="action" value="sort_by_date" class="btn btn-link">تاریخ</button>
                   <button type="submit" name="action" value="sort_by_imdb" class="btn btn-link">امتیاز imdb</button>
                </form>
            </div>
        </span>
    </div>
</body>
</html>
