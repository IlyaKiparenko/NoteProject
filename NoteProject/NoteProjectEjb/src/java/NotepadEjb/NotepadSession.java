/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NotepadEjb;


import NoteEjb.Note;
import UserEjb.User;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author bmz
 */
@Stateless
public class NotepadSession implements NotepadSessionLocal {
    @PersistenceContext //(unitName="Notepad")
    EntityManager em;
    
    public Notepad findPrimary(long id) {
        return em.find(Notepad.class, id);
    }
    
    public void CreateNotepad(User user, String name) {
        Notepad n = new Notepad();
        //user.setNotepads(new ArrayList<>());
        //user.getNotepads().add(n);
        n.setName(name);
        n.setUser(user);
        //n.setNotes(new ArrayList<Note>());
        em.persist(n);
        //user = em.merge(user);
    }
    
    public void ModifyNotepad(long id, String name) {
        Notepad n = em.find(Notepad.class, id);
        //em.g
        n.setName(name);
        
        em.merge(n);
        //n.setUser(em.merge(n.getUser()));
    }
    
    public void RemoveNotepad(long id) {
        Notepad n = em.find(Notepad.class, id);
        em.remove(n);
    }
}