package nyu.pqs.ns3184.ps1;

/**
 * Contact object contains fields Name, Postal address, Phone Number, 
 * email address and note.
 * @author Narasimman
 *
 */
public class Contact {
  private ContactName name;
  private PostalAddress postalAddress;
  private PhoneNumber phoneNumber;
  private EmailAddress emailId;
  private Note note;

  /**
   * Constructor with mandatory fields
   * @param name
   * @param phoneNumber
   */
  public Contact(ContactName name, PhoneNumber phoneNumber) {
    this(name, phoneNumber, null, null, null);
  }

  /**
   * Core constructor to create a new Contact object
   * @param name
   * @param postalAddress
   * @param phoneNumber
   * @param emailId
   * @param note
   */
  public Contact(ContactName name, PhoneNumber phoneNumber,
      PostalAddress postalAddress, EmailAddress emailId, Note note) {
    this.name = name;
    this.postalAddress = postalAddress;
    this.phoneNumber = phoneNumber;
    this.emailId = emailId;
    this.note = note;
  }
  
  /**
   * @return the name
   */
  public ContactName getName() {
    return name;
  }
  
  /**
   * @return the postalAddress
   */
  public PostalAddress getPostalAddress() {
    return postalAddress;
  }
  
  /**
   * @return the phoneNumber
   */
  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }
  
  /**
   * @return the emailId
   */
  public EmailAddress getEmailId() {
    return emailId;
  }
  
  /**
   * @return the note
   */
  public Note getNote() {
    return note;
  }
  
  public boolean search(String searchQuery) {
    if(name.search(searchQuery)
        || postalAddress.search(searchQuery)
        || phoneNumber.search(searchQuery)
        || emailId.search(searchQuery)
        || note.search(searchQuery)) {
      return true;
    }
    return false;
  } 
  
  /**
   * Creates comma separated values of all the fields in a contact.
   * The returned value can be used to store into a file.
   * @return contact as CSV
   */
  public String toCSV() {
    StringBuilder contact = new StringBuilder();

    if(name != null) {
      contact.append("'" + name.getFirstName() + "',")
      .append("'" + name.getMiddleName() + "',")
      .append("'" + name.getLastName() + "',");
    } else {
      contact.append("'','','',"); 
    }
    
    if(postalAddress != null) {
      contact.append("'" + postalAddress.getStreet() + "',")
      .append("'" + postalAddress.getApt() + "',")
      .append("'" + postalAddress.getCity() + "',")
      .append("'" + postalAddress.getState() + "',")
      .append("'" + postalAddress.getCountry() + "',")
      .append("'" + postalAddress.getZipcode() + "',");
    } else {
      contact.append("'','','','','','',"); 
    }
    
    if(phoneNumber != null) {
      contact.append("'" + phoneNumber.getPhoneNumber() + "',");
    } else {
      contact.append("'',"); 
    }
    
    if(emailId != null) {
      contact.append("'" + emailId.getEmailaddress() + "',");
    } else {
      contact.append("'',"); 
    }
    
    if(note != null) {
      contact.append("'" + note.getNote() + "'");
    } else {
      contact.append("''"); 
    }
    contact.append("\n");
    return contact.toString();
  }
}