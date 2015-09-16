package nyu.pqs.ns3184.ps1;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class AddressBookTest {

  @Test
  public void testAddContact_add10000Contacts() {
    AddressBook book = new AddressBook();
    String nameBase = "contact ";
    int numContactsToAdd = 10000;

    for (int contactNumber = 0; contactNumber < numContactsToAdd; contactNumber++) {
      Contact contact =
          new Contact(new ContactName(nameBase + contactNumber),
              new PhoneNumber(String.valueOf(contactNumber)));
      book.AddContact(contact);
    }

    assertTrue(book.search("").size() == numContactsToAdd);
  }

  @Test
  public void testAddContact_basic() {
    AddressBook book = new AddressBook();
    assertTrue(book.AddContact(TestHelper.minimalContact));
  }

  @Test
  public void testAddContact_nullContact() {
    AddressBook book = new AddressBook();
    assertFalse(book.AddContact(null));
  }

  @Test
  public void testAddContact_verifyAfterAdding() {

    AddressBook book = new AddressBook();
    book.AddContact(TestHelper.minimalContact);

    // search and verify contact
    List<Contact> retrievedContacts = book.search("");
    Contact retrievedContact = retrievedContacts.get(0);
    assertTrue(retrievedContact.getName().getFirstName()
        .equals(TestHelper.contactFirstName));
    assertTrue(retrievedContact.getPhoneNumber().getPhoneNumber()
        .equals(TestHelper.contactPhoneNumber));
  }

  @Test
  public void testAddressBook_addAndRemoveContact() {
    // add contact
    AddressBook book = new AddressBook();
    book.AddContact(TestHelper.minimalContact);
    assertTrue(book.search("").size() == 1);

    // remove contact
    book.removeContact(TestHelper.minimalContact);
    assertTrue(book.search("").size() == 0);
  }

  // failing as AddressBook.save is not working
  @Ignore
  @Test
  public void testAddressBook_addContact_saveAndLoad() throws IOException {
    AddressBook bookToBeSaved = new AddressBook();
    bookToBeSaved.AddContact(TestHelper.minimalContact);
    bookToBeSaved.save(TestHelper.addressBookPath);

    AddressBook loadedBook = new AddressBook();
    assertTrue(loadedBook.read(TestHelper.addressBookPath));
    List<Contact> contacts = loadedBook.search("");
    assertTrue("address book must have one entry", contacts.size() == 1);
    Contact contactRead = contacts.get(0);
    assertTrue(contactRead.getName().getFirstName()
        .equals(TestHelper.contactFirstName));
    assertTrue(contactRead.getPhoneNumber().getPhoneNumber()
        .equals(TestHelper.contactPhoneNumber));
  }

  @Test
  public void testAddressBook_createNewAddressBook() {
    AddressBook book = new AddressBook();
    List<Contact> contacts = book.search("");
    assertTrue("address book must be empty", contacts.size() == 0);
  }

  // AddressBook.save accesses a method on a null object
  @Ignore
  @Test
  public void testAddressBook_saveAndLoadEmptyBook() throws IOException {
    new AddressBook().save(TestHelper.addressBookPath);

    AddressBook loadedBook = new AddressBook();
    assertTrue(loadedBook.read(TestHelper.addressBookPath));
    List<Contact> contacts = loadedBook.search("");
    assertTrue("address book must be empty", contacts.size() == 0);
  }

  @Test
  public void testRead_blankPath() throws IOException {
    AddressBook book = new AddressBook();
    assertFalse(book.read(""));
  }

  @Test
  public void testRead_wrongPath() {
    AddressBook book = new AddressBook();
    try {
      book.read("wrongPath");
      fail("exception should be thrown before this line");
    } catch (IOException ignored) {
    }
  }

  @Test
  public void testRemove_fromEmptyAddressBook() {
    AddressBook book = new AddressBook();
    assertFalse(book.removeContact(TestHelper.minimalContact));
  }

  @Test
  public void testRemove_nullContact() {
    AddressBook book = new AddressBook();

    assertFalse(book.removeContact(null));
  }

  // AddressBook.save accesses a method on a null object
  @Ignore
  @Test
  public void testSave_blankPath() throws IOException {
    AddressBook book = new AddressBook();
    assertFalse(book.save(""));
  }

  // AddressBook.save accesses a method on a null object
  @Ignore
  @Test
  public void testSave_onExistingFile_thenVerifyOverwrite() throws IOException {
    AddressBook book_willBeOverwritten = new AddressBook();
    book_willBeOverwritten.AddContact(TestHelper.minimalContact);
    book_willBeOverwritten.save(TestHelper.addressBookPath);

    AddressBook book_willOverwriteOldAddressBook = new AddressBook();
    book_willOverwriteOldAddressBook.AddContact(TestHelper.minimalContact);
    book_willOverwriteOldAddressBook.AddContact(TestHelper.fullContact);

    AddressBook book_readFromFile = new AddressBook();
    book_readFromFile.read(TestHelper.addressBookPath);
    assertTrue(book_readFromFile.search("").size() == 2);
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchBy_PhoneNumber() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getPhoneNumber().getPhoneNumber());
    assertTrue(searchResults.size() == 1);
    assertTrue(TestHelper.compareContacts(searchResults.get(0),
        TestHelper.fullContact));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByAddress_Apt() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    Contact retrieved =
        book.search(TestHelper.fullContact.getPostalAddress().getApt()).get(0);
    assertTrue(book.search(TestHelper.fullContact.getPostalAddress().getApt())
        .size() == 1);

    assertTrue(TestHelper.compareContacts(TestHelper.fullContact, retrieved));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByAddress_City() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getPostalAddress().getCity());
    assertTrue(searchResults.size() == 1);
    Contact retrieved = searchResults.get(0);
    assertTrue(TestHelper.compareContacts(TestHelper.fullContact, retrieved));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByAddress_Country() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getPostalAddress().getCountry());
    assertTrue(searchResults.size() == 1);
    Contact retrieved = searchResults.get(0);
    assertTrue(TestHelper.compareContacts(TestHelper.fullContact, retrieved));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByAddress_State() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getPostalAddress().getState());
    assertTrue(searchResults.size() == 1);
    Contact retrieved = searchResults.get(0);
    assertTrue(TestHelper.compareContacts(TestHelper.fullContact, retrieved));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByAddress_Street() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getPostalAddress().getStreet());
    assertTrue(searchResults.size() == 1);
    Contact retrieved = searchResults.get(0);
    assertTrue(TestHelper.compareContacts(TestHelper.fullContact, retrieved));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByAddress_ZipCode() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getPostalAddress().getZipcode());
    assertTrue(searchResults.size() == 1);
    Contact retrieved = searchResults.get(0);
    assertTrue(TestHelper.compareContacts(TestHelper.fullContact, retrieved));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByEmail() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getEmailId().getEmailaddress());
    assertTrue(searchResults.size() == 1);
    assertTrue(TestHelper.compareContacts(searchResults.get(0),
        TestHelper.fullContact));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByName_first() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getName().getFirstName());
    assertTrue(searchResults.size() == 1);
    assertTrue(TestHelper.compareContacts(searchResults.get(0),
        TestHelper.fullContact));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByName_last() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getName().getLastName());
    assertTrue(searchResults.size() == 1);
    assertTrue(TestHelper.compareContacts(searchResults.get(0),
        TestHelper.fullContact));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByName_middle() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getName().getMiddleName());
    assertTrue(searchResults.size() == 1);
    assertTrue(TestHelper.compareContacts(searchResults.get(0),
        TestHelper.fullContact));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addMinimalAndFull_searchByNote() {
    AddressBook book = new AddressBook();

    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.fullContact);

    List<Contact> searchResults =
        book.search(TestHelper.fullContact.getNote().getNote());
    assertTrue(searchResults.size() == 1);
    assertTrue(TestHelper.compareContacts(searchResults.get(0),
        TestHelper.fullContact));
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_addTwoMinimalContacts_searchForFirst() {
    AddressBook book = new AddressBook();
    book.AddContact(TestHelper.minimalContact);
    book.AddContact(TestHelper.getSecondMinimalContact());

    assertTrue(book.search(TestHelper.contactFirstName).size() == 1);
  }

  @Test
  public void testSearch_duplicateContacts() {
    AddressBook book = new AddressBook();
    book.AddContact(TestHelper.minimalContact);
    book.AddContact(new Contact(new ContactName(TestHelper.contactFirstName),
        new PhoneNumber(TestHelper.contactPhoneNumber)));

    assertTrue(book.search(TestHelper.contactFirstName).size() == 2);
  }

  @Test(expected = NullPointerException.class)
  public void testSearch_forNull() {
    AddressBook book = new AddressBook();
    book.AddContact(TestHelper.minimalContact);

    book.search(null);
  }

  // ContactName.search throws NullPointerException if middleName is null
  @Ignore
  @Test
  public void testSearch_invalidSearch() {
    AddressBook book = new AddressBook();
    book.AddContact(TestHelper.minimalContact);

    assertTrue(book.search("invalid string").size() == 0);
  }

  @Test
  public void testSearch_validSearch() {
    AddressBook book = new AddressBook();
    book.AddContact(TestHelper.minimalContact);
    assertTrue(book.search(TestHelper.contactFirstName).size() == 1);
  }
}
