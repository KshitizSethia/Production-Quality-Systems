package addressBook;

/**
 * stores contact details, instantiate using {@link Contact.Builder}
 */
public class Contact {

  private final String         name;
  private final String         address;
  private final String         phoneNumber;
  private final String         email;
  private final String         note;
  private final java.util.UUID uuid;

  /**
   * Builder pattern for making a contact object
   */
  public static class Builder {

    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String note;
    private String id;

    
    /**
     * new instance, use as Builder pattern objects are used
     */
    public Builder(){
      
    }
    
    /**
     * @param name
     *          of {@link Contact}
     * @return modified {@link Builder} instance
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * setter
     * 
     * @param address
     *          of {@link Contact}
     * @return modified {@link Builder} instance
     */
    public Builder address(String address) {
      this.address = address;
      return this;
    }

    /**
     * setter
     * 
     * @param phoneNumber
     *          of {@link Contact}
     * @return modified {@link Builder} instance
     */
    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    /**
     * setter
     * 
     * @param email
     *          of {@link Contact}
     * @return modified {@link Builder} instance
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * setter
     * 
     * @param note
     *          in {@link Contact}
     * @return modified {@link Builder} instance
     */
    public Builder note(String note) {
      this.note = note;
      return this;
    }

    /**
     * setter, supply the {@link Contact#getId()} here to make changes to an
     * existing contact
     * 
     * @param id
     *          of {@link Contact}
     * @return modified {@link Builder} instance
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Build a {@link Contact} with the values supplied to the {@link Builder}
     * instance
     * 
     * @return {@link Contact} with specified attributes, {@link <code>null</code>}
     *         if no contact details given to {@link Builder}
     */
    public Contact build() {
      if (name == null && phoneNumber == null && email == null
          && address == null && note == null) {
        return null;
      } else {
        return new Contact(this);
      }
    }
  }

  private Contact(Builder builder) {
    this.name = builder.name;
    this.address = builder.address;
    this.phoneNumber = builder.phoneNumber;
    this.email = builder.email;
    this.note = builder.note;

    if (builder.id == null) {
      this.uuid = java.util.UUID.randomUUID();
    } else {
      this.uuid = java.util.UUID.fromString(builder.id);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Contact)) {
      return false;
    }
    Contact contact = (Contact) obj;
    return contact.uuid.equals(this.uuid);
  }

  @Override
  public int hashCode() {
    return uuid.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder sbr = new StringBuilder();

    sbr.append("ID: " + uuid.toString());
    if (name != null) {
      sbr.append(", Name: " + name);
    }
    if (address != null) {
      sbr.append(", Address: " + address);
    }
    if (phoneNumber != null) {
      sbr.append(", Phone: " + phoneNumber);
    }
    if (email != null) {
      sbr.append(", email: " + email);
    }
    if (note != null) {
      sbr.append(", Note: " + note);
    }
    return sbr.toString();
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getNote() {
    return note;
  }

  // keep id public, so that underlying implementation of id can be changed as
  // needed
  /**
   * Uniquely identifies a {@link Contact}
   */
  public String getId() {
    return uuid.toString();
  }
}
