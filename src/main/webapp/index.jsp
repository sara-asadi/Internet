<%@page import="app.db.UserDB"%>
<%@page import="app.model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
    <ul>
        <li id="email">email: <%=UserDB.getInstance().getCurrentUser().getEmail()%></li>

        <li>
            <a href="movies.jsp">Movies</a>
        </li>
        <li>
            <a href="watchlist">Watch List</a>
        </li>
        <li>
            <a href="logout">Log Out</a>
        </li>
    </ul>
</body>
</html>