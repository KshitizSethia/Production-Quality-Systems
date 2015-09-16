package nyu.pqs.ns3184.ps1;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class ContactNameTest {

  String      firstName   = TestHelper.contactFirstName;
  String      middleName  = "middleName";
  String      lastName    = "lastName";
  ContactName fullName    = new ContactName(firstName, middleName, lastName);
  ContactName minimalName = new ContactName(TestHelper.contactFirstName);

  @Test
  public void testConstructor_full() {
    assertEquals(fullName.getFirstName(), firstName + "");
    assertEquals(fullName.getMiddleName(), middleName + "");
    assertEquals(fullName.getLastName(), lastName + "");
  }
  
  //might not be a valid test case
  @Ignore
  @Test(expected=NullPointerException.class)
  public void testConstructor_fullNull() {
    new ContactName(null, null, null);
  }

  @Test
  public void testConstructor_minimal() {
    assertEquals(minimalName.getFirstName(), TestHelper.contactFirstName);
    assertNull(minimalName.getMiddleName());
    assertNull(minimalName.getLastName());
  }

  //might not be a valid test case
  @Ignore
  @Test(expected=NullPointerException.class)
  public void testConstructor_minimalNull() {
    new ContactName(null);
  }
  

  @Test
  public void testSearch_invalidSearch() {
    assertFalse(fullName.search("invalid search string"));
  }

  @Test
  public void testSearch_minimalContactName() {
    assertTrue(minimalName.search(TestHelper.contactFirstName));
  }

  @Test(expected = NullPointerException.class)
  public void testSearch_null() {
    fullName.search(null);
  }

  @Test
  public void testSearch_validSearch() {
    assertTrue(fullName.search(firstName));
  }
}
