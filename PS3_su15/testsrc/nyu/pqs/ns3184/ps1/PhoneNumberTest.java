package nyu.pqs.ns3184.ps1;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class PhoneNumberTest {

  String      number_str = TestHelper.contactPhoneNumber;
  PhoneNumber phNum      = new PhoneNumber(number_str);

  @Test
  public void testConstructor() {
    assertEquals(phNum.getPhoneNumber(), number_str);
  }

  // might be a wrong test case
  @Ignore
  @Test(expected = NullPointerException.class)
  public void testConstructor_null() {
    new PhoneNumber(null);
  }

  @Test
  public void testSearch_invalid() {
    assertFalse(phNum.search("invalid search string"));
  }

  @Test(expected = NullPointerException.class)
  public void testSearch_null() {
    phNum.search(null);
  }

  @Test
  public void testSearch_valid() {
    assertTrue(phNum.search(number_str));
  }
}
