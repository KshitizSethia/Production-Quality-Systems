package pqs.ps1.addressbook;

import java.io.Serializable;

/**
 * Immutable class that represents an email address. Contains fields for local
 * username and domain parts of a full email address. Initialization
 * with constructor will cause a validation check before the object
 * is created. Email address follows standard convention: '@' sign 
 * must be used to separate username and domain fields in the string
 * and domain must contain 1 period. <br><br>
 * 
 * The email address String is checked with the following regex:
 * ^\\w{1,64}@\\w{1,253}\\.[a-zA-Z]{1,3}$. <br><br>
 * 
 * This class is Serializable.
 * 
 * @author deweichen
 * 
 */
public class EmailAddress implements Serializable {  
  private final String username;
  private final String domain;
  private static final long serialVersionUID = 9076615051514076037L;
  
  /**
   * Constructs an EmailAddress instance
   * @param emailAddress Full email address string in format username@domain
   */
  public EmailAddress(String emailAddress) {
    String[] parts = parse(emailAddress);
    this.username = parts[0];
    this.domain = parts[1];
  }
  
  /**
   * Check to see if a string is a valid email address
   * @param emailAddress Full email address string in format username@domain
   * @return True if this is a valid email address, false if not
   */
  public static boolean validate(String emailAddress) {
    return emailAddress.matches("^\\w{1,64}@\\w{1,253}\\.[a-zA-Z]{1,3}$");
  }
  
  /**
   * Parse an email address string into its username and domain components
   * in the format username@domain. Throws IllegalArgumentException if 
   * email address string is not a valid email address (does not match the
   * email regular expression).
   * @param emailAddress Full email address string in format username@domain
   * @return A String array of 2 elements. The first element is
   * the parsed username portion of the email address. The second element
   * is the parsed domain portion of the email address.
   */
  public static String[] parse(String emailAddress) {
    if (!validate(emailAddress)) {
      throw new IllegalArgumentException("EmailAddress invalid email: " + emailAddress);
    }
    return emailAddress.split("@");
  }
  
  /**
   * Get the username portion (before @) of the EmailAddress as a String
   * @return Username portion of EmailAddress
   */
  public String getUsername() {
    return username;
  }
  
  /**
   * Get the domain portion (after @) of the EmailAddress as a String
   * @return Domain portion of EmailAddress
   */
  public String getDomain() {
    return domain;
  }
  
  @Override 
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof EmailAddress)) {
      return false;
    }
    EmailAddress email = (EmailAddress)o;
    return email.username.equals(username)
        && email.domain.equals(domain);
  }
  
  @Override 
  public int hashCode() {
    int result = 17;
    result = 31 * result + username.hashCode();
    result = 31 * result + domain.hashCode();
    return result;
  }
  
  @Override 
  public String toString() {
    return String.format("%s@%s", username, domain);
  }
}
