/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserEjb;

import javax.ejb.Local;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bmz
 */
@Local
public interface UserSessionLocal {
    public User CreateUser(String username, String password, String email);
    public User CheckUser(String username, String password);
    public User findPrimary(long id);
}
