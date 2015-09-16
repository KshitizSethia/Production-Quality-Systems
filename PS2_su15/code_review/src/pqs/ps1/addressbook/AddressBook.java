package pqs.ps1.addressbook;

import java.util.ArrayList;
import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * An AddressBook contains a list of Entry instances. Users of the
 * AddressBook may take the following actions: add an Entry, delete
 * an Entry and search for an Entry. This AddressBook does not allow
 * duplicates. In other words, if an entry already exists in the 
 * address book, adding it again or another equivalent instance with the
 * same fields will not create a new entry in the AddressBook. AddressBook 
 * is serializable and can be written to and read from a binary file using 
 * the static readFile and writeFile functions.
 * 
 * @author deweichen
 *
 */
public class AddressBook implements Serializable {
  private ArrayList<Entry> entryList;
  private static final long serialVersionUID = 898202570475891598L;
  
  /**
   *  Constructs an empty AddressBook instance with no entries.
   */
  public AddressBook() {
    entryList = new ArrayList<Entry>();
  }
  
  /**
   * Add an Entry to the AddressBook. AddressBook does not allow duplicates.
   * Entry is checked to see if it exists already before added.
   * @param entry Entry object to be added to the AddressBook.
   */
  public void add(Entry entry) {
    if (!contains(entry)) {
      entryList.add(entry);
    }
  }
  
  /**
   * Removes an Entry from the AddressBook. Has no effect if the Entry
   * doesn't exist in AddressBook.
   * @param entry Entry to be removed.
   * @return true if the AddressBook contains the element to be removed,
   * otherwise false
   */
  public boolean remove(Entry entry) {
    return entryList.remove(entry);
  }
    
  /**
   * Checks if the AddressBook already contains an entry
   * @param entry Entry to look for in AddressBook
   * @return True if the Entry is found, else false
   */
  public boolean contains(Entry entry) {
    return entryList.contains(entry);
  }
 
  /**
   * Get the number of entries currently in the AddressBook
   * @return Number of entries
   */
  public int size() {
    return entryList.size();
  }
  
  /**
   * Search for an Entry in AddressBook by Name.
   * @param name Name field of the Entry to be searched for
   * @return A list of Entry objects with Name matches. An
   * Entry array of length 0 means there are no matches.
   */
  public Entry[] search(Name name) {
    ArrayList<Entry> matches = new ArrayList<Entry>();
    
    for (Entry entry : entryList) {
      if (name.equals(entry.getName())) {
        matches.add(entry);
      }
    }
    return matches.toArray(new Entry[matches.size()]);
  }
  
  /**
   * Search for an Entry in AddressBook by PostalAddress
   * @param address PostalAddress field of the Entry to be searched for
   * @return A list of Entry objects with PostalAddress matches. An
   * Entry array of length 0 means there are no matches.
   */
  public Entry[] search(PostalAddress address) {
    ArrayList<Entry> matches = new ArrayList<Entry>();
    
    for (Entry entry : entryList) {
      if (address.equals(entry.getAddress())) {
        matches.add(entry);
      }
    }
    return matches.toArray(new Entry[matches.size()]);
  }
  
  /**
   * Search for an Entry in AddressBook by PhoneNumber
   * @param phone PhoneNumber field of the Entry to be searched for
   * @return A list of Entry objects with PhoneNumber matches. An
   * Entry array of length 0 means there are no matches.
   */
  public Entry[] search(PhoneNumber phone) {
    ArrayList<Entry> matches = new ArrayList<Entry>();
    
    for (Entry entry : entryList) {
      if (phone.equals(entry.getPhone())) {
        matches.add(entry);
      }
    }
    return matches.toArray(new Entry[matches.size()]);
  }
  
  /**
   * Search for an Entry in AddressBook by EmailAddress
   * @param email EmailAddress field of the Entry to be searched for
   * @return A list of Entry objects with EmailAddress matches. An
   * Entry array of length 0 means there are no matches.
   */
  public Entry[] search(EmailAddress email) {
    ArrayList<Entry> matches = new ArrayList<Entry>();
    
    for (Entry entry : entryList) {
      if (email.equals(entry.getEmail())) {
        matches.add(entry);
      }
    }
    return matches.toArray(new Entry[matches.size()]);
  }
  
  /**
   * Search for an Entry in AddressBook by note string
   * @param note Note field of the Entry to be searched for
   * @return A list of Entry objects with note matches. An
   * Entry array of length 0 means there are no matches.
   */
  public Entry[] search(String note) {
    ArrayList<Entry> matches = new ArrayList<Entry>();
    
    for (Entry entry : entryList) {
      if (note.equals(entry.getNote())) {
        matches.add(entry);
      }
    }
    return matches.toArray(new Entry[matches.size()]);
  }
  
  /**
   * Searches all fields of entries in the AddressBook for a string 
   * and returns matches found, if any. The query string is compared 
   * against all fields of each entry by doing a substring search on 
   * the String representation of Entry. Leading and trailing spaces
   * of the query string is stripped. searchAll is not case-sensitive. 
   * This search method is designed to be a one-query-search-all, so 
   * may run slower than the other direct field search methods.
   * @param query String to be searched for in all Entry fields
   * @return A list of Entry objects whose String representation contains
   * the query String. Entry array of length 0 means there are no matches.
   */
  public Entry[] searchAll(String query) {
    ArrayList<Entry> matches = new ArrayList<Entry>();
    
    query = query.trim().toLowerCase();
    for (Entry entry : entryList) {
      if (entry.toString().toLowerCase().indexOf(query) >= 0) {
        matches.add(entry);
      }
    }
    return matches.toArray(new Entry[matches.size()]);
  }
  
  /**
   * Write an AddressBook object to a binary file on disk. FileNotFoundException
   * is thrown when fileName could not be opened. IOException is thrown when error
   * is encountered while writing the object to file.
   * @param addressBook AddressBook object to write. Has no effect when null.
   * @param fileName Path name of file where the AddressBook object is written to
   * @throws IOException
   * @throws FileNotFoundException
   */
  public static void writeFile(AddressBook addressBook, String fileName) 
      throws IOException, FileNotFoundException {
    if (addressBook == null) {
      return;
    }
    FileOutputStream fos = new FileOutputStream(fileName);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    
    oos.writeObject(addressBook);
    fos.close();
    oos.close();
  }
  
  /**
   * Read an AddressBook object from a binary file saved on disk.
   * @param fileName Path name of file to read the AddressBook object from
   * @return A new AddressBook Object read from a binary file.
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws FileNotFoundException
   */
  public static AddressBook readFile(String fileName)
      throws IOException, ClassNotFoundException, FileNotFoundException {
    FileInputStream fis = new FileInputStream(fileName);
    ObjectInputStream ois = new ObjectInputStream(fis);
    AddressBook ab = (AddressBook)ois.readObject();
    
    fis.close();
    ois.close();      
    return ab;
  }
  
  @Override 
  public String toString() {
    StringBuilder sb = new StringBuilder();
    
    for (Entry entry : entryList) {
      sb.append(entry.toString() + "\n");
    }
    return sb.toString();
  }
}
