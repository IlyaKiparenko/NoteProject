<%-- 
    Document   : modify_note
    Created on : 31.08.2016, 22:26:49
    Author     : bmz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.*,UserEjb.*,NoteEjb.*,java.util.List, NotepadEjb.*" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modify Note</title>
        <%! String text; List<Notepad> notepads;%>
        <%
            String note_id = request.getParameter("note_id");
            User user = (User) session.getAttribute("user");
            Long notepad_id = (Long) session.getAttribute("create_notepad_id");
            if (user != null) {
                notepads = user.getNotepads();
                InitialContext ic = new InitialContext();
                NoteSessionLocal bean = (NoteSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/NoteSession");
                if (note_id != null) {
                    //String notepad_id = request.getParameter("notepad_id");
                    //if (notepad_id != null) {
                    //    session.setAttribute("create_notepad_id", Long.parseLong(notepad_id));
                    //}
                    Long id = Long.parseLong(note_id); 
                    session.setAttribute("modify_note_id", id);
                    Note n = bean.findPrimary(id);
                    if (n.getNotepad().getUser().equals(user)) {
                        System.out.println("ID1 = " + n.getNotepad().getUser().getId());
                        System.out.println("ID2 = " + user.getId());
                        System.out.println("YOOOOO");
                        text = n.getText();
                        notepads = user.getNotepads();
                    } else 
                        response.sendRedirect("home.jsp");
                } 
            } else 
                response.sendRedirect("home.jsp");
            
        %>
    </head>
    <body>
        <article>
            <form action="CreateNoteServlet" method="get">
                <p> <input type="text" name="text" value=" <%=text %> "> </p>
                <p> <input type="submit" value="Save"> </p>
                <% if (notepads != null) { %>
                    <p> <label> Notepad </label>
                        <select name="o_notepad"> 
                            <% 
                                for (Notepad j : notepads) {
                                %>
                                    <option value="<%= j.getId() %>"
                                            <%= (j.getId().equals(notepad_id)) ? "selected" : "" %>       
                                    > <%= j.getName() %> </option>
                                <%
                                }
                            %>    
                        </select>
                    </p>
                <% } %>
            </form>
        </article>
    </body>
</html>
