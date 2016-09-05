<%-- 
    Document   : createNotepad
    Created on : 01.09.2016, 17:11:03
    Author     : bmz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.*,NotepadEjb.*,UserEjb.*" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="styles.css" media="screen" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modify Notepad Page</title>
        <%! String name;%>
        <%
            String notepad_id = request.getParameter("notepad_id");
            System.out.println("ID = " + notepad_id);
            User user = (User) session.getAttribute("user");
            if (user != null) {
                
                InitialContext ic = new InitialContext();
                NotepadSessionLocal bean = 
                        (NotepadSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/NotepadSession");
                if (notepad_id != null) {
                    Long id = Long.parseLong(notepad_id); 
                    //session.setAttribute("modify", true);
                    session.setAttribute("modify_notepad_id", id);
                    Notepad n = bean.findPrimary(id);
                    System.out.println("ID2 = " + n.getUser().getId());
                    System.out.println("ID3 = " + user.getId());
                    if (n.getUser().equals(user)) {
                        System.out.println("get me");
                        name = n.getName();
                    } else 
                        response.sendRedirect("home.jsp");
                } 
            } else 
                response.sendRedirect("home.jsp");
            
        %>
    </head>
    <body>
        <article>
            <form action="CreateNotepadServlet" method="get">
                <p> <input type="text" name="name" value=" <%=name %> "> </p>
                <p> <input type="submit" value="Save"> </p>        
            </form>
        </article>
    </body>
</html>
