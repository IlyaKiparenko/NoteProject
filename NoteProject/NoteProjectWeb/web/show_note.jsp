<%-- 
    Document   : show_note
    Created on : 31.08.2016, 22:00:55
    Author     : bmz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.*,NoteEjb.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Note</title>
        <%! String text; boolean error = true; java.util.Date create_date;%>
        <%
            String note_id = request.getParameter("note_id");
            Long user_id = (Long) session.getAttribute("user_id");
            if (note_id != null) {
                InitialContext ic = new InitialContext();
                NoteSessionLocal bean = (NoteSessionLocal) ic.lookup("java:global/NoteProjectEjb/NoteSession");
                Note n = bean.findPrimary(Long.parseLong(note_id));
                if (n.getNotepad().getUser_id() == user_id || n.getPublicity()) {
                    error = false;
                    text = n.getText();
                    create_date = n.getCreate_date();
                } else 
                    response.sendRedirect("home.jsp");
            } else 
                response.sendRedirect("home.jsp");
            
        %>
    </head>
    <body>
        <div>Date Creation <label> <%= create_date.toString() %> </label> </div>
        <p></p>
        <article>
            <h6>
                <%= text %>
            </h6>
        </article>
    </body>
</html>
