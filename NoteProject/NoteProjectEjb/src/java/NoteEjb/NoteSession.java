/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NoteEjb;

import NotepadEjb.Notepad;
import java.io.File;
import java.io.FileWriter;
import java.sql.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author bmz
 */
@Stateless
public class NoteSession implements NoteSessionLocal {
    @PersistenceContext //(unitName="Note")
    EntityManager em;
    
    public static String path_to_folder;
    
    static {
        Note t = new Note();
        String u = t.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        path_to_folder = u.substring(1, u.indexOf("t/NoteProject") + 2);
    }
    
    public NoteSession() { }
    
   
    
    @Override
    public void CreateNote(long notepad_id, String text) {

        System.out.println(path_to_folder);
        
        Note n = new Note();
        Date d = new Date((new java.util.Date()).getTime());
        n.setNotepad(em.find(Notepad.class, notepad_id));
        //n.getNotepad().getNotes().add(n);
        n.setCreate_date(d);
        n.setModify_date(d);
        em.persist(n);
        n.setNotepad(em.merge(n.getNotepad()));
        
        try {
            File f = new File(path_to_folder + "Notes/N" + n.getId());
            f.mkdir();
            File f1 = new File(f.getAbsolutePath(), "text.txt");
            f1.createNewFile();
            FileWriter w = new FileWriter(f1);
            w.write(text);
            w.close();
            System.out.println("DADA");
        } catch (Exception e) {
            e.printStackTrace();
        }
               
    }
    
    public void ModifyNote(long id, String text, Long notepad_id) {
        Note n = em.find(Note.class, id);
        Date d = new Date((new java.util.Date()).getTime());
        n.setModify_date(d);
        if (notepad_id != null) {
            n.getNotepad().removeNote(n);
            n.setNotepad(em.find(Notepad.class, notepad_id));
            //n.getNotepad().getNotes().add(n);
        }
        em.merge(n);
        //em.merge(n.g)
        try {
            FileWriter w = new FileWriter(
                    new File(path_to_folder + "Notes/N" + n.getId(),"text.txt"), false);
            w.write(text);
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void DeleteNote(long id) {
        Note n = em.find(Note.class, id);
        
        em.getTransaction().begin();
        em.remove(n);
        em.getTransaction().commit();
        
        File f = new File(path_to_folder + "Notes/N" + id);
        for (File f1 : f.listFiles())
            f1.delete();
        f.delete();
    }
    
    public Note findPrimary(long id) {
        return em.find(Note.class, id);
    }
}
