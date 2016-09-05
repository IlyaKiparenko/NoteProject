<%-- 
    Document   : index
    Created on : 29.08.2016, 22:01:16
    Author     : bmz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.InitialContext,NoteEjb.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Test Page</h1>
        <form action="index.jsp">
            <%
                //session.removeAttribute("done");
                String action = request.getParameter("type");
                String type = request.getParameter("type");
                if (type != null)
                    session.setAttribute("type", Integer.parseInt(type));
                Boolean b = (Boolean) session.getAttribute("done");
                Boolean b1 = (Boolean) request.getAttribute("done");
                boolean done = (b == null) ? false : b.booleanValue();
                if (done) {
                    type = String.valueOf((Integer)session.getAttribute("type"));
                    session.removeAttribute("type");
                }
                System.out.println("DONE = " + done);
                System.out.println("ACTION = " + action);
                System.out.println("B = " + b);
                System.out.println("B1 = " + b1);
                System.out.println("TYPE = " + type);
                if (action != null && !done) {
                    //response.
                    session.setAttribute("done", true);
                    //request.getSession().setAttribute("done", true);
                    System.out.println("RDONE = " + request.getAttribute("done"));
                    switch(Integer.parseInt(type)) {
                        case 1:
                        %>
                            <div>
                                <input type="number" min="1" 
                                       max="100" value="0" name="user_id">
                            </div>
                            <div>
                                <input type="text" name="text">
                            </div>
                            <div>
                                <input type="submit" name="action1" value="Save">
                            </div>
                        <% 
                        break;
                        case 2:
                            InitialContext ic = new InitialContext();
                            NoteSessionLocal bean = 
                            (NoteSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/NoteSession!NoteEjb.NoteSessionLocal");
                            String s_id = request.getParameter("id");
                            long id = (s_id == null) ? 0 : Long.parseLong(s_id);
                        %>
                            <div>
                                <input type="number" min="1" 
                                       max="100" value="0" name="id">
                            </div>
                            <div>
                                <input type="text" name="text" value="
                                    <%=((Note) bean.findPrimary(id)).getText()%>
                                ">
                            </div>    
                            <div>
                                <input type="submit" name="action1" value="Save">
                            </div>
                        <% 
                        break;
                        case 3:
                        %>
                            <div>
                                <input type="number" min="1" 
                                       max="100" value="0" name="id">
                            </div>
                            <div>
                                <input type="submit" name="action1" value="Save">
                            </div>
                        <% 
                        break;  
                    }
                } else {
                    if (done) {
                        session.setAttribute("done", false);
                        InitialContext ic = new InitialContext();
                        NoteSessionLocal bean = 
                            (NoteSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/NoteSession!NoteEjb.NoteSessionLocal");
                        switch(Integer.parseInt(type)) {
                            case 1: {
                                String text = request.getParameter("text");
                                long user_id = Long.parseLong(request.getParameter("user_id"));
                                bean.CreateNote(user_id, text);
                            } break;
                            case 2: {
                                String text = request.getParameter("text");
                                long id = Long.parseLong(request.getParameter("id"));
                                bean.ModifyNote(id, text);
                            } break;   
                            case 3: {
                                long id = Long.parseLong(request.getParameter("id"));
                                bean.DeleteNote(id);
                            } break;
                        }
                    }
                %>  
                    <div>
                        <select name="type"> 		
                            <option selected disabled>Type of Action</option>
                            <option value="1">Create</option>
                            <option value="2">Modify</option>
                            <option value="3">Delete</option>
                        </select>
                    </div>
                    <div>
                        <input type="submit" name="action" value="Do">
                    </div>
            <% 
                } 
            %>
        </form>
    </body>
</html>
