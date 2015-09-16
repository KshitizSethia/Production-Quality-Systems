package nyu.pqs.ns3184.ps1;

/**
 * class for Email address of a contact
 * @author Narasimman
 *
 */
public class EmailAddress {
  private String emailaddress;

  public EmailAddress(String emailId) {
    this.emailaddress = emailId;
  }

  /**
   * @return the emailaddress
   */
  public String getEmailaddress() {
    return emailaddress;
  }
  
  /** 
   * Search method that return true if the searchQuery is part
   * of one of the fields in this object
   * @param searchQuery
   * @return
   */
  public boolean search(String searchQuery) { 
     if(this.emailaddress.toLowerCase().contains(searchQuery)) {
       return true;
     }
     return false;
  }
}
