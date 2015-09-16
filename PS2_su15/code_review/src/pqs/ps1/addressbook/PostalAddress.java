package pqs.ps1.addressbook;

import java.io.Serializable;

/**
 * An immutable postal address class. Contains street, city, state and
 * zip code fields. Street, City and State fields must be Strings,
 * there are no restrictions on their format, however. Zip code must be
 * a 5 digit integer between 00000 and 99999. This class is Serializable.
 * 
 * @author deweichen
 *
 */
public class PostalAddress implements Serializable {
  private final String street;
  private final String city;
  private final String state;
  private final int zipCode;
  private static final long serialVersionUID = 8237801832383517091L;
  
  /**
   * Constructs a PostalAddress instance by providing the
   * street name, city, state and zip code of the address.
   * @param street Street address, example: 123 Main St.
   * @param city City name, example: New York
   * @param state State name, example: NY
   * @param zipCode Postal zip code, must be between 00000-99999 otherwise 
   * IllegalArgumentException will be thrown
   */
  public PostalAddress(String street, String city, String state, int zipCode) {
    this.street = street;
    this.city = city;
    this.state = state;
    rangeCheck(zipCode, 99999);
    this.zipCode = zipCode;
  }
  
  private void rangeCheck(int val, int max) {
    if (val < 0 | val > max) {
      throw new IllegalArgumentException("PostalAddress range check fail: " + val);
    }
  }
  
  /**
   * Get the street name of a PostalAddress as a String
   * @return Street String of a PostalAddress
   */
  public String getStreet() {
    return street;
  }
  
  /**
   * Get the city name of a PostalAddress as a String
   * @return City String of a PostalAddress
   */
  public String getCity() {
    return city;
  }
  
  /**
   * Get the state name of a PostalAddress as a String
   * @return State String of a PostalAddress
   */
  public String getState() {
    return state;
  }
  
  /**
   * Get the zip code of a PostalAddress as an int
   * @return Zip code integer of a PostalAddress
   */
  public int getZipCode() {
    return zipCode;
  }

  @Override 
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof PostalAddress)) {
      return false;
    }
    PostalAddress address = (PostalAddress)o;
    return address.street.equals(street)
        && address.city.equals(city)
        && address.state.equals(state)
        && address.zipCode == zipCode;
  }
  
  @Override 
  public int hashCode() {
    int result = 17;
    result = 31 * result + street.hashCode();
    result = 31 * result + city.hashCode();
    result = 31 * result + state.hashCode();
    result = 31 * result + zipCode;
    return result;
  }
  
  @Override 
  public String toString() {
    return String.format("%s %s %s %05d", street, city, state, zipCode);
  }
}
