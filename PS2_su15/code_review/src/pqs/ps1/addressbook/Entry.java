package pqs.ps1.addressbook;

import java.io.Serializable;

/**
 * The Entry class represents a single item inside an AddressBook. An Entry object 
 * must have at least a Name field. Optional fields are PostalAddress, PhoneNumber, 
 * EmailAddress and note (as a String). This class is serializable. <br><br>
 * 
 * An Entry instance must be contructed using the static Builder class. Once a builder is 
 * obtained, the default fields of an Entry object is initialized. Call its methods to 
 * initialize the optional fields. After fields are initialized, build() must be called 
 * on the Builder class to return the Entry instance. <br><br> 
 * 
 * Example invocation by chaining Builder methods: <br>
 * Entry e = new Entry.Builder(name, address).note("A note")
 * .phone(new PhoneNumber(123, 456, 7890)).build(); <br><br>
 * 
 * The Builder pattern inspired by Effective Java book and is used to enhance readability. 
 * It also simulates the named optional parameters as in Python or Ada.
 *
 * @author deweichen
 * 
 */
public class Entry implements Serializable {  
  private final Name name;
	private final PostalAddress address;
	private final PhoneNumber phone;
	private final EmailAddress email;
	private final String note;
  private static final long serialVersionUID = 6532156108373410268L;
	
	private Entry(Builder builder) {
    name = builder.name;
    address = builder.address;
    phone = builder.phone;
    email = builder.email;
    note = builder.note;
  }
	
	/**
	 * The Builder class will initialze an entry's required fields and set up its optional 
	 * fields when its setter methods are called. Setter methods are conveniently named to
	 * represent the field names of the Entry class that it sets. Once the desired fields 
	 * of an Entry has been set with setter methods in Builder, use the build() method 
	 * to construct a new instance of Entry containing those field values.
	 * 
	 * @author deweichen
	 *
	 */
	public static class Builder {
		// Required entry field
		private final Name name;
		// Optional entry fields
		private PostalAddress address = null;
		private PhoneNumber phone = null;
		private EmailAddress email = null;
		private String note = null;
		
		/**
		 * Constructs a Builder object containing required fields of 
		 * the Entry instance which it will build.
		 * @param name Required Name field of entry
		 */
		public Builder(Name name) {
		  this.name = name;
		}
		
		/**
		 * Sets the PostalAddress field of the Entry created by Builder
		 * @param address PostalAddress to set in the Builder
		 * @return This Builder instance with PostalAddress field set to address
		 */
		public Builder address(PostalAddress address) {
		  this.address = address;
		  return this;
		}
		
		/**
		 * Sets the PhoneNumber field of the Entry created by Builder
		 * @param phone PhoneNumber to set in the Builder
		 * @return This Builder instance with PhoneNumber field set to phone
		 */
		public Builder phone(PhoneNumber phone) {
		  this.phone = phone;
		  return this;
		}
		
		/**
		 * Sets the EmailAddress field of the Entry created by Builder
		 * @param email EmailAddress to set in the Builder
		 * @return This Builder instance with EmailAddress field set to email
		 */
		public Builder email(EmailAddress email) {
		  this.email = email;
		  return this;
		}
		
		/**
		 * Sets the note field of the Entry created by Builder
		 * @param note Note String to set in the Builder
		 * @return This Builder instance with note String field set to note
		 */
		public Builder note(String note) {
		  this.note = note;
		  return this;
		}
		
		/**
		 * This method must be called at end of calling the setter methods on Builder. Creates
		 * an Entry object that has the exact same initialized fields of the Builder object. If no
		 * setter methods are called, the Entry object returned will contain only the required
		 * fields initialized by the Builder constructor.
		 * @return A new Entry instance containing initialized fields of this Builder.
		 */
		public Entry build() {
		  return new Entry(this);
		}
	}
	
	/**
	 * Get Name field of the AddressBook Entry
	 * @return Name field of the Entry
	 */
	public Name getName() {
	  return name;
	}
	
	/**
	 * Get PostalAddress field of the AddressBook Entry
	 * @return PostalAddress field of the Entry 
	 */
	public PostalAddress getAddress() {
	  return address;
	}
	
	/**
	 * Get PhoneNumber field of the AddressBook Entry
	 * @return PhoneNumber field of the Entry
	 */
	public PhoneNumber getPhone() {
	  return phone;
	}
	
	/**
	 * Get Email field of the AddressBook Entry
	 * @return Email field of the Entry
	 */
	public EmailAddress getEmail() {
	  return email;
	}
	
	/**
	 * Get note field of the AddressBook Entry
	 * @return The note String field of the Entry
	 */
	public String getNote() {
	  return note;
	}
	
	@Override 
	public boolean equals(Object o) {
	  if (o == this) {
	    return true;
	  }
		if (!(o instanceof Entry)) {
		  return false;
		}
		Entry entry = (Entry)o;
		return entry.name.equals(name)
		    && entry.address.equals(address)
		    && entry.phone.equals(phone)
		    && entry.email.equals(email)
		    && entry.note.equals(note);
	}
	
	@Override 
	public int hashCode() {
	  int result = 17;
	  result = 31 * result + name.hashCode();
	  result = 31 * result + address.hashCode();
	  result = 31 * result + phone.hashCode();
	  result = 31 * result + email.hashCode();
	  result = 31 * result + note.hashCode();
	  return result;
	}
	
	@Override 
	public String toString() {
	  StringBuilder sb = new StringBuilder();
	  
	  sb.append(name + " ");
	  sb.append(address + " ");
	  if (phone != null) {
	    sb.append(phone + " ");  
	  }
	  if (email != null) {
	    sb.append(email + " ");
	  }
	  if (note != null) {
	    sb.append(note + " ");
	  }
	  return sb.toString();
	}
}
