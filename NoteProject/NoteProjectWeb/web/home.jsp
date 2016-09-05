<%-- 
    Document   : home.jsp
    Created on : 30.08.2016, 20:48:08
    Author     : bmz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.*,UserEjb.*,NotepadEjb.*,NoteEjb.*,java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <%! List<Notepad> notepads; %>
        <%
            User user = (User) session.getAttribute("user");
            if (user == null)
                response.sendRedirect("register.jsp");
            else {
                notepads = user.getNotepads();
            }
        %>
    </head>
    <body>
        <h1>Notepads!</h1>
        <p></p>
        <%
            if (notepads != null) {
                for (Notepad i : notepads) {
                    %>
                    <p> 
                        Name is <%= i.getName() %>
                        <p><label> Notes:= <%= i.getNotes().size() %></label></p>
                        <p><a href="<%="modify_notepad.jsp?notepad_id="+i.getId() %>">
                            <button>Modify</button>
                        </a>
                        <a href="<%="show_notepad.jsp?notepad_id="+i.getId() %>">
                            <button>Show</button>
                        </a></p>
                    </p>
                    <%
                    for (Note j : i.getNotes()) {
                        %>
                        <p> <%=j.getId() %></p>
                        <%
                    }
                }
            }
        %>
        <p></p>
        <form action="modify_notepad.jsp">
            <input type="submit" value="Create Notepad">
        </form>
    </body>
</html>
