/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import NoteEjb.NoteSessionLocal;
import UserEjb.User;
import UserEjb.UserSessionLocal;
import java.io.IOException;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bmz
 */
public class CreateNoteServlet extends HttpServlet {

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
                    NoteSessionLocal bean = 
                            (NoteSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/NoteSession");
            String text = request.getParameter("text");
            Long note_id = (Long) request.getSession().getAttribute("modify_note_id");
            System.out.println("ID = " + note_id);
            User user = (User) request.getSession().getAttribute("user");
            UserSessionLocal bean1 = 
                        (UserSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/UserSession");
            String t_notepad_id = request.getParameter("o_notepad");
            Long notepad_id = null;
            if (t_notepad_id != null)
                notepad_id = Long.parseLong(t_notepad_id);
            if (note_id != null) {
                request.getSession().removeAttribute("modify_note_id");
                bean.ModifyNote(note_id, text, notepad_id);
            } else {
                if (notepad_id == null)
                    notepad_id = (Long) request.getSession().getAttribute("create_notepad_id");
                if (notepad_id == null) {
                    notepad_id = user.default_notepad().getId();
                }
                bean.CreateNote(notepad_id, text);
            }
            request.getSession().setAttribute("user",bean1.findPrimary(user.getId()));
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
        processRequest(request, response);
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
