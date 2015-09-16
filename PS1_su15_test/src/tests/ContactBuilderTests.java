package tests;

import junit.framework.TestCase;

import org.junit.Test;

import addressBook.Contact;

public class ContactBuilderTests extends TestCase {

  @Test
  public void testContactBuilder_onlyName() {
    Contact mrAbc = new Contact.Builder().name("abc").build();
    assertTrue("contact name not set", mrAbc.getName() == "abc");
    assertTrue("contact number set", mrAbc.getPhoneNumber() == null);
    assertFalse("contact number not null", mrAbc.getPhoneNumber() == "");

    // TODO figure out a way to assert non compilation of some code
    /*
     * mrAbc.name = "abd"; assertFalse("contact name not immutable", mrAbc.getName()
     * == "abd");
     */
  }

  @Test
  public void testContactBuilder_allProps() {
    String name = "abc", phoneNumber = "1234567890", address = "flat no. 1", email =
        "a@b.com", note = "my note";
    Contact mrAbc =
        new Contact.Builder().name(name).phoneNumber(phoneNumber)
            .address(address).email(email).note(note).build();
    assertTrue("name not set", mrAbc.getName() == name);
    assertTrue("phone number not set", mrAbc.getPhoneNumber() == phoneNumber);
    assertTrue("address not set", mrAbc.getAddress() == address);
    assertTrue("email not set", mrAbc.getEmail() == email);
    assertTrue("note not set", mrAbc.getNote() == note);
  }

  @Test
  public void testContactBuilder_twoContacts() {
    String name = "abc", phoneNumber = "1234567890", address = "flat no. 1", email =
        "a@b.com", note = "my note";
    new Contact.Builder().name(name).phoneNumber(phoneNumber).address(address)
        .email(email).note(note).build();

    String name2 = "abc2", phoneNumber2 = "2234567890", address2 = "flat no. 2", email2 =
        "a@b2.com", note2 = "my note 2";
    Contact mrAbc2 =
        new Contact.Builder().name(name2).address(address2)
            .phoneNumber(phoneNumber2).email(email2).note(note2).build();
    assertTrue("name not set", mrAbc2.getName() == name2);
    assertTrue("phone number not set", mrAbc2.getPhoneNumber() == phoneNumber2);
    assertTrue("address not set", mrAbc2.getAddress() == address2);
    assertTrue("email not set", mrAbc2.getEmail() == email2);
    assertTrue("note not set", mrAbc2.getNote() == note2);
  }

  /**
   * Checks if calling {@link addressBook.Contact.Builder} multiple times
   * creates a mess
   */
  @Test
  public void testContactBuilder_twoContacts_secondOnlyName() {
    String name = "abc", phoneNumber = "1234567890", address = "flat no. 1", email =
        "a@b.com", note = "my note";
    new Contact.Builder().name(name).phoneNumber(phoneNumber).address(address)
        .email(email).note(note).build();

    String name2 = "abc2";
    Contact mrAbc2 = new Contact.Builder().name(name2).build();
    assertTrue("name not set", mrAbc2.getName() == name2);
    assertTrue("phone number not set", mrAbc2.getPhoneNumber() == null);
    assertTrue("address not set", mrAbc2.getAddress() == null);
    assertTrue("email not set", mrAbc2.getEmail() == null);
    assertTrue("note not set", mrAbc2.getNote() == null);
  }
  
  @Test
  public void testContactBuilder_noDetails_nullContact(){
    Contact thisShouldBeNull = new Contact.Builder().build();
    assertNull(thisShouldBeNull);
  }
}
