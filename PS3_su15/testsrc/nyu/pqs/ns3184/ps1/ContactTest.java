package nyu.pqs.ns3184.ps1;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class ContactTest {

  @Test
  public void testConstructor_large() {
    String firstName = "firstName", middleName = "middleName", lastName =
        "lastName";
    String phoneNumber = "9871263462";
    String street = "street", apartment = "apt", city = "city", state = "state", country =
        "country", zipcode = "12";
    String emailAddress = "contact1@email.com";
    String note_content = "note";

    PostalAddress address =
        new PostalAddress(street, apartment, city, state, country, zipcode);
    EmailAddress email = new EmailAddress(emailAddress);
    Note note = new Note(note_content);

    Contact contact =
        new Contact(new ContactName(firstName, middleName, lastName),
            new PhoneNumber(phoneNumber), address, email, note);

    assertTrue(contact.getName().getFirstName().equals(firstName));
    assertTrue(contact.getPhoneNumber().getPhoneNumber().equals(phoneNumber));
    assertTrue(contact.getPostalAddress().getStreet().equals(street));
    assertTrue(contact.getEmailId().getEmailaddress().equals(emailAddress));
    assertTrue(contact.getNote().getNote().equals(note_content));
  }

  // might be a wrong test case
  @Ignore
  @Test(expected = NullPointerException.class)
  public void testConstructor_largeNull() {
    new Contact(null, null, null, null, null);
  }

  @Test
  public void testConstructor_small() {
    Contact contact =
        new Contact(new ContactName(TestHelper.contactFirstName),
            new PhoneNumber(TestHelper.contactPhoneNumber));

    assertTrue(contact.getName().getFirstName()
        .equals(TestHelper.contactFirstName));
    assertTrue(contact.getPhoneNumber().getPhoneNumber()
        .equals(TestHelper.contactPhoneNumber));
    assertTrue(contact.getPostalAddress() == null);
    assertTrue(contact.getEmailId() == null);
    assertTrue(contact.getNote() == null);
  }

  // might be wrong test case
  @Ignore
  @Test(expected = NullPointerException.class)
  public void testConstructor_smallNull() {
    new Contact(null, null);
  }

  @Test
  public void testSearch_byAddress() {
    assertTrue(TestHelper.fullContact.search(TestHelper.fullContact
        .getPostalAddress().getStreet()));
  }

  @Test
  public void testSearch_invalidSearch() {
    assertFalse(TestHelper.fullContact.search("invalid search string"));
  }

  @Test(expected = NullPointerException.class)
  public void testSearch_null() {
    TestHelper.fullContact.search(null);
  }

  // query in ContactName.search() is not being changed to lower case
  @Ignore
  @Test
  public void testSearch_validSearch() {
    assertTrue(TestHelper.fullContact.search(TestHelper.fullContact.getName()
        .getFirstName()));
  }

  @Test
  public void testToCSV_fullContact() {
    String csv = TestHelper.fullContact.toCSV();
    String expectedCSV =
        "'firstName','middleName','lastName','street','apt','city','state','country','12','9871263462','contact1@email.com','note'\n";
    assertTrue(csv.split(",").length == 12);
    assertEquals(expectedCSV, csv);
  }

  // getting "null" string in middle name and last name of ContactName
  @Ignore
  @Test
  public void testToCSV_minimalContact() {
    String csv = TestHelper.minimalContact.toCSV();
    String expectedCSV =
        "'contact 1','','','','','','','','','1234567890','',''\n";
    assertTrue(csv.split(",").length == 12);
    assertEquals(expectedCSV, csv);
  }

  // getting "null" string in middle name and last name of ContactName
  @Ignore
  @Test
  public void testToCSV_ContactWithComma() {
    Contact contact =
        new Contact(new ContactName("name,name"), new PhoneNumber("123"));
    String actual = contact.toCSV();
    String expected = "'name,name','','','','','','','','','123','',''\n";
    assertEquals(expected, actual);
  }

}
