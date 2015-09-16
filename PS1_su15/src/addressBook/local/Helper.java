package addressBook.local;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import addressBook.Contact;
import addressBook.IAddressBook;

/**
 * Helper class which: creates new address book, writes and reads
 * {@link LocalAddressBook} to/from file
 */
public class Helper {

  private Helper() {
    // private constructor to prevent instantiation
  }

  /**
   * create a blank address book
   */
  public static IAddressBook createNew() {
    return new LocalAddressBook();
  }

  /*
   * todo use google's format for CSV file:
   * 
   * https://productforums.google.com/forum/#!topic/gmail/MbHTlFI4of4 //
   * http://beta.beantin.se/how-import-google-contacts-csv-template/
   * 
   * desired format: "Name", "Notes", "E-mail 1 - Value", "Phone 1 - Value",
   * "Address 1 - Formatted"
   */

  /*
   * writing CSV files with headers, this facilitates reordering and addition of
   * columns with ease
   * 
   * referred links for reading/writing csv files with headers:
   * http://www.codejava
   * .net/coding/super-csv-writing-pojos-to-csv-file-using-csvbeanwriter
   * http://super-csv.github.io/super-csv/examples_writing.html
   * http://super-csv.github.io/super-csv/cell_processors.html
   */

  /**
   * File header for CSV file
   */
  private static final String[]        header     = new String[] { "id",
      "name", "note", "email", "phoneNumber", "address" };
  /**
   * {@link CellProcessor} array string constraints on columns in CSV file
   */
  private static final CellProcessor[] processors = new CellProcessor[] {
      new NotNull(), new Optional(), new Optional(), new Optional(),
      new Optional(), new Optional()             };

  /**
   * Load an {@link LocalAddressBook} from file
   * 
   * @param path
   *          of file
   * @return {@link LocalAddressBook} object
   * @throws IOException
   */
  public static IAddressBook loadFromFile(String path) throws IOException {

    // using map reader instead of bean reader as Contact doesn't have public
    // setter functions
    ICsvMapReader fileReader =
        new CsvMapReader(new FileReader(path),
            CsvPreference.STANDARD_PREFERENCE);

    // ignore header line
    fileReader.read(header, processors);

    IAddressBook result = createNew();

    // copy all contacts to address book
    Map<String, Object> entry;
    while ((entry = fileReader.read(header, processors)) != null) {

      String id = (String) entry.get("id");
      String name = (String) entry.get("name");
      String note = (String) entry.get("note");
      String email = (String) entry.get("email");
      String phoneNumber = (String) entry.get("phoneNumber");
      String address = (String) entry.get("address");

      Contact contact =
          new Contact.Builder().id(id).name(name).note(note).email(email)
              .phoneNumber(phoneNumber).address(address).build();
      result.add(contact);
    }

    fileReader.close();
    return result;
  }

  /**
   * Writes {@link LocalAddressBook} to file
   * 
   * @param book
   *          to be written to file
   * @param path
   *          of file
   * @throws IOException
   */
  public static void saveToFile(IAddressBook book, String path)
      throws IOException {

    // CsvBeanWriter converts objects to string using reflection
    ICsvBeanWriter fileWriter =
        new CsvBeanWriter(new FileWriter(path),
            org.supercsv.prefs.CsvPreference.STANDARD_PREFERENCE);

    fileWriter.writeHeader(header);
    for (Contact contact : book.getContacts()) {
      fileWriter.write(contact, header, processors);
    }
    fileWriter.flush();
    fileWriter.close();
  }
}
