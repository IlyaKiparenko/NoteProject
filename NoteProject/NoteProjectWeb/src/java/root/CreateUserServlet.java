/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import UserEjb.User;
import UserEjb.UserSessionLocal;
import java.io.IOException;
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
@WebServlet(name = "CreateUserServlet", urlPatterns = {"/CreateUserServlet"})
public class CreateUserServlet extends HttpServlet {

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
        
        System.out.println("Servlet Start");
        String action = request.getParameter("action");
            if (action != null) {
                boolean doubleUser = false, wrongData = false;
                try {
                    InitialContext ic = new InitialContext(); 
                    UserSessionLocal bean = 
                        (UserSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/UserSession");
                    String username = request.getParameter("Username");
                    String password = request.getParameter("Password");
                    String email = request.getParameter("Email");
                    User user;
                    if (action.equals("Register")) {
                        user = bean.CreateUser(username, password, email);
                        if (user != null)
                            request.getSession().setAttribute("user", user);
                        else
                            doubleUser = true;
                    } else {
                        if (action.equals("Login")) {
                            user = bean.CheckUser(username, password);
                            if (user != null)
                                request.getSession().setAttribute("user", user);
                            else
                                wrongData = true;
                        } else
                            if (action.equals("Exit"))
                                request.getSession().removeAttribute("user");
                    }
                    if (!doubleUser && !wrongData)
                        response.sendRedirect("home.jsp");
                    else {
                        String data = String.format("doubleUser=%s&wrongData=%s"
                                , doubleUser, wrongData);
                        response.sendRedirect("register.jsp?" + data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
