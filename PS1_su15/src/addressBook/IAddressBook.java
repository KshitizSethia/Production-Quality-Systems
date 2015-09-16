package addressBook;

import java.util.List;

/**
 * Add, remove and search contacts on an {@link IAddressBook} object
 */
public interface IAddressBook {

  /**
   * Add new {@link Contact} to the address book
   * 
   * @param contact
   *          to add
   */
  public abstract void add(Contact contact);

  /**
   * Search address book by string for any field
   * 
   * @param data
   *          to look for
   * @return Iterator on {@link Contact}s which match the search criteria
   */
  public abstract List<Contact> search(String data);

  /**
   * size of address book
   * 
   * @return number of contacts in address book
   */
  public abstract int size();

  // TODO what's the ideal way to return status?
  /**
   * remove {@link Contact} from address book
   * 
   * @param contactId
   *          {@link Contact#getId()} of contact to be removed
   * @return true: successfully removed contact, false: failure in removing
   *         contact
   */
  public abstract boolean remove(String contactId);

  public abstract List<Contact> getContacts();

}