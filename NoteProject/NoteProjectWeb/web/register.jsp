<%-- 
    Document   : register
    Created on : 30.08.2016, 20:55:25
    Author     : bmz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <% boolean doubleUser = false, wrongData = false; %>
        <% 
           doubleUser = Boolean.parseBoolean(request.getParameter("doubleUser"));
           wrongData = Boolean.parseBoolean(request.getParameter("wrongData"));  
        %>    
    </head>
    <body>
        <% 
            if (doubleUser) {
                %> 
                <div><h3>User with this username is already existed!</h3></div>
                <%
            }
            if (wrongData) {
                %> 
                <div><h3>Username or password is wrong!</h3></div>
                <%
            }
        %>
        <div>
        <h3>Register</h3>
        <form action="CreateUserServlet" method="get">
            <input type="text" placeholder="Username" name="Username">
            <input type="password" placeholder="Password" name="Password">
            <input type="email" placeholder="me@mail.com" name="Email">
            <input type="submit" name="action" value="Register"> 
        </form>
        </div>
        <p></p>
        
        <div>
        <h3>Login</h3>
        <form action="CreateUserServlet" method="get">
            <input type="text" placeholder="Username" name="Username">
            <input type="password" placeholder="Password" name="Password">
            <input type="submit" name="action" value="Login"> 
        </form>
        </div>
        <p></p>
        <p> <form action="CreateUserServlet">
                <input type="submit" name="action" value="Exit"> 
            </form>
        </p>
            
        
    </body>
</html>
