package nyu.pqs.ns3184.ps1;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;


public class NoteTest {

  String note_str = "note";
  Note note = new Note(note_str);
  
  @Test
  public void testConstructor() {
    assertEquals(note.getNote(), note_str+"");
  }

  //might not be a valid test case
  @Ignore
  @Test(expected=NullPointerException.class)
  public void testConstructor_null() {
    new Note(null);
  }
  
  @Test
  public void testSearch_invalid() {
    assertFalse(note.search("invalid search string"));
  }

  @Test(expected=NullPointerException.class)
  public void testSearch_null() {
    note.search(null);
  }

  @Test
  public void testSearch_valid() {
    assertTrue(note.search(note_str));
  }

}
