package nyu.pqs.ns3184.ps1;

/**
 * Class for postal address of a contact
 * @author Narasimman
 *
 */
public class PostalAddress {
  private String street;
  private String apt;
  private String city;
  private String state;
  private String country;
  private String zipcode;

  /**
   * 
   * @param street
   * @param apt
   * @param city
   * @param state
   * @param country
   * @param zipcode
   */
  public PostalAddress(String street, String apt, String city, String state,
      String country, String zipcode) {
    this.street = street;
    this.apt = apt;
    this.city = city;
    this.state = state;
    this.country = country;
    this.zipcode = zipcode;
  }

  /**
   * @return the street
   */
  public String getStreet() {
    return street;
  }

  /**
   * @return the apt
   */
  public String getApt() {
    return apt;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @return the zipcode
   */
  public String getZipcode() {
    return zipcode;
  }
  
  /** 
   * Search method that return true if the searchQuery is part
   * of one of the fields in this object
   * @param searchQuery
   * @return
   */
  public boolean search(String searchQuery) { 
     if(this.apt.toLowerCase().contains(searchQuery)
         || this.city.toLowerCase().contains(searchQuery)
         || this.street.toLowerCase().contains(searchQuery)
         || this.state.toLowerCase().contains(searchQuery)
         || this.country.toLowerCase().contains(searchQuery)
         || this.zipcode.toLowerCase().contains(searchQuery)) {
       return true;
     }
     return false;
  }
}
