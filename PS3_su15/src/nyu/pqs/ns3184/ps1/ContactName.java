package nyu.pqs.ns3184.ps1;

/**
 * Class for name field of a contact
 * @author Narasimman
 *
 */
public class ContactName {
  private String firstName;
  private String lastName;
  private String middleName;

  /**
   * Constructor with the mandatory field - firstName
   * @param firstName
   */
  public ContactName(String firstName) {
    this(firstName, null, null);
  }

  /**
   * Constructor to build the ContactName object
   * @param firstName
   * @param middleName
   * @param lastName
   */
  public ContactName(String firstName, String middleName, String lastName) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
  }
  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the middleName
   */
  public String getMiddleName() {
    return middleName;
  }
  
  /** 
   * Search method that return true if the searchQuery is part
   * of one of the fields in this object
   * @param searchQuery
   * @return
   */
  public boolean search(String searchQuery) { 
     if(this.firstName.toLowerCase().contains(searchQuery)
         || this.lastName.toLowerCase().contains(searchQuery)
         || this.middleName.toLowerCase().contains(searchQuery)) {
       return true;
     }
     return false;
  }
}
