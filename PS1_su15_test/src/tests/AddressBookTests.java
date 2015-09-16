package tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import addressBook.Contact;
import addressBook.IAddressBook;

public class AddressBookTests {

  private static String csvPath = "test.csv";

  private void removeCsv() throws IOException {
    Files.deleteIfExists(Paths.get(csvPath));
  }

  // TODO add searching tests

  @Test
  public void test_addOne_SearchAndMatch() {
    IAddressBook book = addressBook.local.Helper.createNew();

    String name = "abc", phoneNumber = "1234567890", address = "flat no. 1", email =
        "a@b.com", note = "my note";
    Contact mrAbc =
        new Contact.Builder().name(name).phoneNumber(phoneNumber)
            .address(address).email(email).note(note).build();
    book.add(mrAbc);

    Contact retrieved = book.search(name).get(0);

    assertTrue("address book size", book.size() == 1);

    assertTrue("contact equality", retrieved.equals(mrAbc));
    assertTrue("contact name", retrieved.getName() == mrAbc.getName());
    assertTrue("phone number mismatch", retrieved.getPhoneNumber() == phoneNumber);
    assertTrue("address mismatch", retrieved.getAddress() == address);
    assertTrue("email mismatch", retrieved.getEmail() == email);
    assertTrue("note mismatch", retrieved.getNote() == note);
  }

  @Test
  public void test_addDuplicates() {
    IAddressBook book = addressBook.local.Helper.createNew();

    String name = "abc";
    Contact mrAbc = new Contact.Builder().name(name).build();
    book.add(mrAbc);

    String fullName = "abc def";
    Contact mrAbcsDuplicate =
        new Contact.Builder().id(mrAbc.getId()).name(fullName).build();
    book.add(mrAbcsDuplicate);

    assertTrue("address book size", book.size() == 1);

    Contact retrieved = book.search("abc").get(0);
    assertTrue("retrieved contact", retrieved.equals(mrAbcsDuplicate));
    assertTrue("retrieved contact's name", retrieved.getName() == fullName);
  }

  @Test
  public void test_addAndRemove() {
    IAddressBook book = addressBook.local.Helper.createNew();

    Contact mrAbc = new Contact.Builder().name("abc").build();
    book.add(mrAbc);

    assertTrue(book.remove(mrAbc.getId()));
    assertTrue("book size", book.size() == 0);
  }

  @Test
  public void test_csvWriteOneFullRecord() throws IOException {
    IAddressBook bookToFile = addressBook.local.Helper.createNew();

    String name = "abc", phoneNumber = "1234567890", address = "flat no. 1", email =
        "a@b.com", note = "my note";
    Contact mrAbc =
        new Contact.Builder().name(name).phoneNumber(phoneNumber)
            .address(address).email(email).note(note).build();
    bookToFile.add(mrAbc);

    addressBook.local.Helper.saveToFile(bookToFile, csvPath);

    IAddressBook bookfromFile = addressBook.local.Helper.loadFromFile(csvPath);
    assertTrue("book size", bookfromFile.size() == 1);

    Contact readContact = bookfromFile.search(name).get(0);
    assertTrue("id mismatch", readContact.getId().equals(mrAbc.getId()));
    assertTrue("name mismatch", readContact.getName().equals(name));
    assertTrue("phone number mismatch",
        readContact.getPhoneNumber().equals(phoneNumber));
    assertTrue("address mismatch", readContact.getAddress().equals(address));
    assertTrue("email mismatch", readContact.getEmail().equals(email));
    assertTrue("note mismatch", readContact.getNote().equals(note));

    removeCsv();
  }

  @Test
  public void test_csvWriteOnePartialRecord() throws IOException {
    IAddressBook bookToFile = addressBook.local.Helper.createNew();

    String name = "abc", phoneNumber = "1234567890";
    Contact mrAbc =
        new Contact.Builder().name(name).phoneNumber(phoneNumber).build();
    bookToFile.add(mrAbc);

    addressBook.local.Helper.saveToFile(bookToFile, csvPath);

    IAddressBook bookfromFile = addressBook.local.Helper.loadFromFile(csvPath);
    assertTrue("book size", bookfromFile.size() == 1);
    Contact readContact = bookfromFile.search(name).get(0);
    assertTrue("id mismatch", readContact.getId().equals(mrAbc.getId()));
    assertTrue("name mismatch", readContact.getName().equals(name));
    assertTrue("phone number mismatch",
        readContact.getPhoneNumber().equals(phoneNumber));
    assertTrue("address mismatch", readContact.getAddress() == null);
    assertTrue("email mismatch", readContact.getEmail() == null);
    assertTrue("note mismatch", readContact.getNote() == null);

    removeCsv();
  }

}
