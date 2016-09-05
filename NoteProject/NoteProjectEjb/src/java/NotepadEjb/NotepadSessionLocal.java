/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NotepadEjb;

import UserEjb.User;
import javax.ejb.Local;

/**
 *
 * @author bmz
 */
@Local
public interface NotepadSessionLocal {
    public Notepad findPrimary(long id);
    public void CreateNotepad(User user, String name);
    public void ModifyNotepad(long id, String name);
}
