/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import NotepadEjb.NotepadSessionLocal;
import UserEjb.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bmz
 */
@WebServlet(name = "CreateNotepadServlet", urlPatterns = {"/CreateNotepadServlet"})
public class CreateNotepadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try {
            InitialContext ic = new InitialContext();
                    NotepadSessionLocal bean = 
                            (NotepadSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/NotepadSession");
            String name = request.getParameter("name");
            Long notepad_id = (Long) request.getSession().getAttribute("modify_notepad_id");
            System.out.println("ID = " + notepad_id);
            User user = (User) request.getSession().getAttribute("user");
            if (notepad_id != null) {
                request.getSession().removeAttribute("modify_notepad_id");
                System.out.println("NAME = " + name);
                bean.ModifyNotepad(notepad_id, name);
                //Notepad n = bean.findPrimary(notepad_id);
                //n.setName(name);
                //bean.refresh(n);
                System.out.println("NAME2 = " + bean.findPrimary(notepad_id).getName());
            } else {
                bean.CreateNotepad(user, name);
            }
            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateNotepadServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateNotepadServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
