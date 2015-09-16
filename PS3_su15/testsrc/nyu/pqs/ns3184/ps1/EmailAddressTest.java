package nyu.pqs.ns3184.ps1;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class EmailAddressTest {

  String       email_str = "address@email.com";
  EmailAddress email     = new EmailAddress(email_str);

  @Test
  public void testConstructor() {
    assertEquals(email.getEmailaddress(), email_str);
  }

  //might be a wrong test case
  @Ignore
  @Test(expected=NullPointerException.class)
  public void testConstructor_null() {
    new EmailAddress(null);
  }
  
  @Test
  public void testSearch_invalidSearch() {
    assertFalse(email.search("invalid search string"));
  }

  @Test(expected = NullPointerException.class)
  public void testSearch_null() {
    email.search(null);
  }

  @Test
  public void testSearch_validSearch() {
    assertTrue(email.search(email_str));
  }
}
