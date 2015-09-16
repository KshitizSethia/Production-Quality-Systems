package nyu.pqs.ns3184.ps1;

/**
 * Class for Phone number field of a contact
 * @author Narasimman
 *
 */
public class PhoneNumber {
  private String phoneNumber;

  /**
   * Constructor to create a new Phone number object
   * @param phoneNumber
   */
  public PhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  /** 
   * Search method that return true if the searchQuery is part
   * of one of the fields in this object
   * @param searchQuery
   * @return
   */
  public boolean search(String searchQuery) { 
     if(this.phoneNumber.toLowerCase().contains(searchQuery)) {
       return true;
     }
     return false;
  }
}
