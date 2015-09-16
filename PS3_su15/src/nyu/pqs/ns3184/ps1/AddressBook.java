package nyu.pqs.ns3184.ps1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * AddressBook library that allows users to create and empty addressbook,
 * add, remove, search an entry, Write entries to a file, Read entries from a file. 
 * @author Narasimman
 */
public class AddressBook {
  private List<Contact> contactList;
  private File file;
  
  /**
   * Constructor to create an empty address book
   */
  public AddressBook() {
    // Create only one instance of the objects
    if(contactList == null) {
      contactList = new ArrayList<Contact>();
    }
  }
  
  /**
   * Adds a new contact entry into the address book. 
   * @param contact
   * @return boolean if successful
   */
  public boolean AddContact(Contact contact) {
    if(contact == null) {
      return false;
    }
    return contactList.add(contact);
  }

  /**
   * 
   * @param contact
   * @return boolean if successful
   */
  public boolean removeContact(Contact contact) {
    if(contact == null) {
      return false;
    }
    return contactList.remove(contact);
  }
  
  /**
   * Save the contactList into the file after 
   * converting the Contact Object into a string format.
   * @param absolute filepath to which the contacts will be stored.
   * @return
   * @throws IOException 
   */
  public boolean save(String filePath) throws IOException {
    if(!file.exists()){
      file.createNewFile();     
    }
    writeContactsToFile(file);
    return true;
   
  }
  
  /**
   * This method takes in the absolute file path from which 
   * the contact details will be populated into the list. 
   * @param file
   * @return
   * @throws IOException
   */
  public boolean read(String filePath) throws IOException {
    if(filePath == "") {
      return false;
    }
    file = new File(filePath);
    return readContactsFromFile(file);
}
  
  /**
   * Searches all the fields of a contacts
   * @param searchQuery
   * @return resultList - List of contacts that matches the search query
   */
  public List<Contact> search(String searchQuery) {
    List<Contact> resultList = new ArrayList<Contact>();
    for(Contact contact : contactList) {
      if(contact.search(searchQuery.toLowerCase())) {
        resultList.add(contact);
      }
    }
    return resultList;
  } 
  /**
   * Read Comma Separated values of contacts from the csv 
   * and build the contact list
   * @return
   * @throws IOException
   */
  private boolean readContactsFromFile(File file) throws IOException {
    FileInputStream filein = new FileInputStream(file);
    InputStreamReader in = new InputStreamReader(filein);
    
    Scanner scanner = new Scanner(in);
    StringTokenizer contactDetails = null;
    while (scanner.hasNextLine()) {
      contactDetails = new StringTokenizer(scanner.nextLine(), "','");
      String firstName = contactDetails.nextToken().trim().substring(1);
      String middleName = contactDetails.nextToken().trim();
      String lastName = contactDetails.nextToken().trim();
      String phone = contactDetails.nextToken().trim();
      String apt = contactDetails.nextToken().trim();
      String street = contactDetails.nextToken().trim();
      String city = contactDetails.nextToken().trim();
      String state = contactDetails.nextToken().trim();
      String zip = contactDetails.nextToken().trim();
      String country = contactDetails.nextToken().trim();
      String emailId = contactDetails.nextToken().trim();
      String noteText = contactDetails.nextToken().trim();
  
      // Create Contact fields objects
      ContactName name = new ContactName(firstName, lastName, middleName);
      PhoneNumber phoneNumber = new PhoneNumber(phone);
      PostalAddress postalAddress = new PostalAddress(street, apt, city, state, country, zip);
      EmailAddress email = new EmailAddress(emailId);
      Note note = new Note(noteText);
      Contact contact = new Contact(name, phoneNumber, postalAddress, email, note);
      
      contactList.add(contact);
    }
    scanner.close();
    return true;
  }
  
  /**
   * Internal function that iterates through the in-memory contact list
   * and writes it to a csv file.
   * @throws IOException
   */
  private void writeContactsToFile(File file) throws IOException {       
    FileOutputStream fos = new FileOutputStream(file);
    OutputStreamWriter osw = new OutputStreamWriter(fos);

    for(Contact contact : contactList) {
      osw.write(contact.toCSV());
    }
    osw.close();
    fos.close();
  }
}
