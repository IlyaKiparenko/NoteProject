/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NoteEjb;

import javax.ejb.Local;

/**
 *
 * @author bmz
 */
@Local
public interface NoteSessionLocal {
    public void CreateNote(long notepad_id, String text);
    public void ModifyNote(long id, String text, Long notepad_id);
    public void DeleteNote(long id);
    public Note findPrimary(long id);
}
