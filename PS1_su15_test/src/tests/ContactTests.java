package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import addressBook.Contact;

public class ContactTests {

  @Test
  public void test_SameContacts() {
    Contact mrAbc = new Contact.Builder().name("abc").build();

    Contact mrAbcsDuplicate = new Contact.Builder().id(mrAbc.getId()).name("abc def").build();

    assertTrue("IDs", mrAbc.getId().equals(mrAbcsDuplicate.getId()));
    assertTrue("equality comparison", mrAbc != mrAbcsDuplicate);
    assertTrue("equals", mrAbc.equals(mrAbcsDuplicate));
    assertTrue("hash code", mrAbc.hashCode() == mrAbcsDuplicate.hashCode());
  }

  // TODO add reflexivity, transitivity, non-nullity tests

  @Test
  public void test_toStringFull() {
    String name = "abc", phoneNumber = "1234567890", address = "flat no. 1", email =
        "a@b.com", note = "my note";
    Contact mrAbc =
        new Contact.Builder().name(name).phoneNumber(phoneNumber)
            .address(address).email(email).note(note).build();
    String expectedString =
        "ID: " + mrAbc.getId() + ", Name: " + name + ", Address: " + address
            + ", Phone: " + phoneNumber + ", email: " + email + ", Note: "
            + note;
    assertTrue("string values", mrAbc.toString().equals(expectedString));
  }

  @Test
  public void test_toStringPartial() {
    String name = "abc";
    Contact mrAbc = new Contact.Builder().name(name).build();
    String expectedString = "ID: " + mrAbc.getId() + ", Name: " + name;
    assertTrue("string values", expectedString.equals(mrAbc.toString()));
  }
}
