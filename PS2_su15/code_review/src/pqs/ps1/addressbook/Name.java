package pqs.ps1.addressbook;

import java.io.Serializable;

/**
 * Immutable class representing a person's name. Contains first and last 
 * names as String fields. This class is Serializable.
 * 
 * @author deweichen
 */
public class Name implements Serializable {
  private final String first;
  private final String last;
  private static final long serialVersionUID = -5275960457539321668L;
  
  /**
   * Constructs a Name instance from first and last name Strings
   * @param first First name
   * @param last Last name
   */
  public Name(String first, String last) {
    this.first = first;
    this.last = last;
  }
  
  /**
   * Get the first name as a String
   * @return First name field of Name
   */
  public String getFirst() {
    return first;
  }
  
  /**
   * Get the last name as a String
   * @return Last name field of Name
   */
  public String getLast() {
    return last;
  }
  
  @Override 
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Name)) {
      return false;
    }
    Name name = (Name)o;
    return name.first.equals(first)
        && name.last.equals(last);
  }
  
  @Override 
  public int hashCode() {
    int result = 17;
    result = 31 * result + first.hashCode();
    result = 31 * result + last.hashCode();
    return result;
  }
  
  @Override 
  public String toString() {
    return first + " " + last;
  }
}
