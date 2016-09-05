<%-- 
    Document   : home.jsp
    Created on : 30.08.2016, 20:48:08
    Author     : bmz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.*,UserEjb.*,NoteEjb.*,NotepadEjb.*,java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Notepad Page</title>
        <%! List<Note> notes; %>
        <%
            User user = (User) session.getAttribute("user");
            String notepad_id = request.getParameter("notepad_id");
            if (user == null)
                response.sendRedirect("register.jsp");
            else {
                if (notepad_id == null)
                    response.sendRedirect("home.jsp");
                Long id = Long.parseLong(notepad_id);
                InitialContext ic = new InitialContext(); 
                NotepadSessionLocal bean = 
                        (NotepadSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/NotepadSession");
                Notepad n = bean.findPrimary(id);
                System.out.println("ID2 = " + n.getUser().getId());
                    System.out.println("ID3 = " + user.getId());
                if (n.getUser().equals(user)) {
                    notes = n.getNotes();
                    session.setAttribute("create_notepad_id", id);
                } else
                    response.sendRedirect("home.jsp");
            }
        %>
    </head>
    <body>
        <h1>Notepads! </h1>
        <p></p>
        <%
            if (notes != null) {
                %> <p> Count = <%= notes.size() %> </p> <%
                for (Note i : notes) {
                    %>
                    <p> 
                        <%= i.getModify_date() %> 
                        <a href="<%="modify_note.jsp?note_id="+i.getId() %>">
                            <button>Modify</button>
                        </a>
                        <a href="<%="CreateNoteServlet?note_id="+i.getId() %>">
                            <button>Modify</button>
                        </a>
                    </p>
                    <p>
                        <%= i.getText() %>
                    </p>
                    <p></p>
                    <%
                }
            }
        %>
        <p></p>
        <form action="modify_note.jsp">
            <input type="submit" value="Create Note">
        </form>
    </body>
</html>
