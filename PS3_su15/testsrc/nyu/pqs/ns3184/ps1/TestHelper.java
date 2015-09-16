package nyu.pqs.ns3184.ps1;

public final class TestHelper {

  static final String        addressBookPath    = "./testingAddressBook.bin";
  static final String        contactFirstName        = "contact 1";
  static final String        contactPhoneNumber = "1234567890";
  static final Contact       minimalContact     = new Contact(new ContactName(
                                                    contactFirstName),
                                                    new PhoneNumber(
                                                        contactPhoneNumber));
  static final PostalAddress address            = new PostalAddress("street",
                                                    "apt", "city", "state",
                                                    "country", "12");
  static final EmailAddress  email              = new EmailAddress(
                                                    "contact1@email.com");
  static final Note          note               = new Note("note");
  static final Contact       fullContact        = new Contact(new ContactName(
                                                    "firstName", "middleName",
                                                    "lastName"),
                                                    new PhoneNumber(
                                                        "9871263462"), address,
                                                    email, note);

  private TestHelper() {
    // initialization not needed
  }

  private static boolean compareOptional(Object one, Object two) {
    if (one == null)
      return two == null;
    return two != null && one.equals(two);
  }

  static boolean compareContacts(Contact one, Contact two) {

    return compareName(one.getName(), two.getName())
        && comparePhoneNumber(one.getPhoneNumber(), two.getPhoneNumber())
        && comparePostalAddress(one.getPostalAddress(), two.getPostalAddress())
        && compareEmail(one.getEmailId(), two.getEmailId())
        && compareNote(one.getNote(), two.getNote());
  }

  private static boolean compareNote(Note note, Note note2) {
    if (note == null)
      return note2 == null;
    return note2 != null && note.getNote().equals(note2.getNote());
  }

  private static boolean compareEmail(EmailAddress emailId,
      EmailAddress emailId2) {
    if (emailId == null)
      return emailId2 == null;
    return emailId2 != null
        && emailId.getEmailaddress().equals(emailId2.getEmailaddress());
  }

  private static boolean comparePostalAddress(PostalAddress one,
      PostalAddress two) {
    if (one == null)
      return two == null;
    boolean result =
        two != null && one.getApt().equals(two.getApt())
            && one.getCity().equals(two.getCity())
            && one.getCountry().equals(two.getCountry())
            && one.getState().equals(two.getState())
            && one.getStreet().equals(two.getStreet())
            && one.getZipcode().equals(two.getZipcode());

    return result;
  }

  private static boolean comparePhoneNumber(PhoneNumber one, PhoneNumber two) {
    return one.getPhoneNumber().equals(two.getPhoneNumber());
  }

  private static boolean compareName(ContactName one, ContactName two) {
    return (one.getFirstName().equals(two.getFirstName())
        && (compareOptional(one.getMiddleName(), two.getMiddleName()))
        && (compareOptional(one.getLastName(), two.getLastName())));
  }

  static Contact getSecondMinimalContact() {
    return new Contact(new ContactName("contact 2"), new PhoneNumber(
        "1029384231"));
  }
}
