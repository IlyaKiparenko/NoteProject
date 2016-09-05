/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NoteEjb;

import NotepadEjb.Notepad;
import java.io.File;
import java.io.Serializable;
import java.sql.Date;
import java.util.Scanner;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author bmz
 */
@Entity
@Table(name = "Notes")
public class Note implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PUBLICITY")
    private Boolean publicity;
    @Column(name = "CREATE_DATE")
    private Date create_date;
    @Column(name = "MODIFY_DATE")
    private Date modify_date;
    //@Column(name = "NOTEPAD_ID")
    //private Long notepad_id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NOTEPAD_ID", nullable = false)
    private Notepad notepad;

    //public Long getNotepad_id() {
    //    return notepad_id;
    //}

    public Boolean getPublicity() {
        return publicity;
    }

    public void setPublicity(Boolean publicity) {
        this.publicity = publicity;
    }

    //public void setNotepad_id(Long notepad_id) {
    //    this.notepad_id = notepad_id;
    //}

    public Notepad getNotepad() {
        return notepad;
    }

    public void setNotepad(Notepad notepad) {
        this.notepad = notepad;
        notepad.addNote(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Note)) {
            return false;
        }
        Note other = (Note) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NoteEjb.Note[ id=" + id + " ]";
    }
    
    public String getText() {
        try {
            Scanner s = new Scanner(new File(NoteSession.path_to_folder
                    + "Notes/N" + id, "text.txt"))
                    .useDelimiter("\\Z");
            return s.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
