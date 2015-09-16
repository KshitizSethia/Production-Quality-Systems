package pqs.ps1.addressbook;

import java.io.Serializable;

/**
 * This is an immutable phone number class that contains fields for
 * the area code, prefix and line number of the phone number. This
 * class assumes that phone number is in format: ###-###-####. The
 * first 3 digits is defined as the area code. The next 3 digits is
 * the prefix. Last 4 digits is the line number. The allowed range 
 * for area code and prefix fields are 000-999, and the allowed 
 * range for line number is 0000-9999. This class is serializable.
 * 
 * @author deweichen
 * 
 */
public class PhoneNumber implements Serializable {
  private final int areaCode;
  private final int prefix;
  private final int lineNumber;
  private static final long serialVersionUID = -3640339513793145478L;
  
  /**
   * Constructs a PhoneNumber instance
   * @param areaCode Area code portion of the phone number, an integer between 000-999
   * @param prefix Prefix number portion of the phone number, an integer between 000-999
   * @param lineNumber Line number portion of the phone number, an integer between 0000-9999
   */
  public PhoneNumber(int areaCode, int prefix, int lineNumber) {
    rangeCheck(areaCode, 999);
    rangeCheck(prefix, 999);
    rangeCheck(lineNumber, 9999);
    this.areaCode = areaCode;
    this.prefix = prefix;
    this.lineNumber = lineNumber;
  }

  private void rangeCheck(int val, int max) {
    if (val < 0 || val > max) {
      throw new IllegalArgumentException("PhoneNumber range check fail: " + val);
    }
  }
  
  /**
   * Get the area code of a PhoneNumber
   * @return Area code portion of the PhoneNumber as an int
   */
  public int getAreaCode() {
    return areaCode;
  }
  
  /**
   * Get the 3 digits after the area code of a phone number.
   * For example, the prefix of PhoneNumber 123-456-7890 is 456.
   * @return Prefix portion of the PhoneNumber as an int
   */
  public int getPrefix() {
    return prefix;
  }
  
  /**
   * Get the last 4 digits of a phone number.
   * @return Line number portion of the PhoneNumber as an int
   */
  public int getLineNumber() {
    return lineNumber;
  }
  
  @Override 
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof PhoneNumber)) {
      return false;
    }
    PhoneNumber number = (PhoneNumber)o;
    return number.areaCode == areaCode
        && number.prefix == prefix
        && number.lineNumber == lineNumber;
  }
  
  @Override 
  public int hashCode() {
    int result = 17;
    result = 31 * result + areaCode;
    result = 31 * result + prefix;
    result = 31 * result + lineNumber;
    return result;
  }
  
  @Override 
  public String toString() {
    return String.format("%03d-%03d-%04d", areaCode, prefix, lineNumber);
  }
}
