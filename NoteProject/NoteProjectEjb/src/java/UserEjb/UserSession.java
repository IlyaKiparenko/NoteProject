/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserEjb;

import NotepadEjb.Notepad;
import NotepadEjb.NotepadSessionLocal;
import java.sql.Connection;
import javax.ejb.Stateless;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 *
 * @author bmz
 */
@Stateless
public class UserSession implements UserSessionLocal {
    @PersistenceContext //(unitName="User")
    EntityManager em;
    //@Resource(name="jdbc/OracleHR")
    DataSource ds; //ConnectionPool
    Connection conn;
    PreparedStatement get_users;
    public UserSession() {
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/OracleHR");
           // ds = (DataSource)((Context)ic.lookup("java:comp/env")).lookup("jdbc/OracleHR");
            if (ds == null)
                System.out.println("DS IS NULL");
            //ic.
            conn = ds.getConnection(); //getPooledConnection().
            get_users = conn.prepareStatement("select id, password from users where name=?");
        } catch (Exception e) {
            e.printStackTrace();            
        }
    }
    public User CreateUser(String username, String password, String email) {
        try {
            get_users.setString(1, username);
            ResultSet rs = get_users.executeQuery();
            if (!rs.next()) {
  
                User u = new User();
                u.setName(username);
                u.setPassword(password);
                u.setEmail(email);
                //u.setNotepads(new ArrayList<Notepad>());
                
                em.persist(u);
                
                InitialContext ic = new InitialContext();
                    NotepadSessionLocal bean = 
                            (NotepadSessionLocal) ic.lookup("java:global/NoteProject/NoteProjectEjb/NotepadSession");
                bean.CreateNotepad(u, "(Default)");
                //u.default_notepad = u.getNotepads().get(0);
                return u;
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User CheckUser(String username, String password) {
        try {
            get_users.setString(1, username);
            ResultSet rs = get_users.executeQuery();
            if (rs.next()) {
                if (password.equals(rs.getString(2))) {
                    
                    return em.find(User.class, rs.getLong(1));
                }
                return null;
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User findPrimary(long id) {
        return em.find(User.class, id);
    }
}
