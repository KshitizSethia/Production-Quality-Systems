package addressBook.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import addressBook.Contact;
import addressBook.IAddressBook;

public class LocalAddressBook implements IAddressBook {

  // use HashMap instead of HashSet as
  // HashMap replaces old value with new one on addition of duplicate
  protected HashMap<String, Contact> contacts;

  protected LocalAddressBook() {
    contacts = new HashMap<String, Contact>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see addressBook.IAddressBook#add(addressBook.Contact)
   */
  @Override
  public void add(Contact contact) {
    contacts.put(contact.getId(), contact);
  }

  /*
   * (non-Javadoc)
   * 
   * @see addressBook.IAddressBook#search(java.lang.String)
   */
  @Override
  public List<Contact> search(final String data) {
    final ArrayList<Contact> results =
        new ArrayList<Contact>(contacts.values());

    // predicate defines a filter for searching all contacts
    Predicate<Contact> predicate = new Predicate<Contact>() {

      // TODO can we set java compliance level to 1.6? if not, go back to 1.5
      @Override
      public boolean evaluate(Contact contact) {
        boolean result =
            contact.getName().contains(data) || contact.getPhoneNumber().contains(data)
                || contact.getAddress().contains(data)
                || contact.getEmail().contains(data) || contact.getNote().contains(data)
                || contact.getId().equals(data);

        return result;
      }
    };

    CollectionUtils.filter(results, predicate);
    return results;
  }

  /*
   * (non-Javadoc)
   * 
   * @see addressBook.IAddressBook#size()
   */
  @Override
  public int size() {
    return contacts.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see addressBook.IAddressBook#remove(java.lang.String)
   */
  @Override
  public boolean remove(String contactId) {
    boolean success = false;
    try {
      contacts.remove(contactId);
      success = true;
    } catch (Exception ignored) {
    }

    return success;
  }

  @Override
  public List<Contact> getContacts() {
    List<Contact> result = new ArrayList<Contact>(contacts.values());
    return result;
  }

}
