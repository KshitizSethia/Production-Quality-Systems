package nyu.pqs.ns3184.ps1;

/**
 * class for Notes field of a contact
 * @author Narasimman
 *
 */
public class Note {
  private String note;

  /**
   * Constructor 
   * @param note
   */
  public Note(String note) {
    this.note = note;
  }

  /**
   * @return the note
   */
  public String getNote() {
    return note;
  }
  
  /** 
   * Search method that return true if the searchQuery is part
   * of one of the fields in this object
   * @param searchQuery
   * @return
   */
  public boolean search(String searchQuery) { 
     if(this.note.toLowerCase().contains(searchQuery)) {
       return true;
     }
     return false;
  }
}
