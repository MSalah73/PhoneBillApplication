package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.PhoneBillDumper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * This class is simulate writing a <code>PhoneBill</code> Object to a file.
 *
 * @author Zack Salah
 */

public class TextDumper implements PhoneBillDumper<PhoneBill> {

   /**
   * Creates a new <code>TestDumber</code> object.
   * @param file
   * Name of the file from the command line arguments
   * @throws IOException
   * if reading a file failed.
   */
  TextDumper(final String file) throws IOException {
    super();
    var fileAndDirectoryGenerator = new FileAndDirectoryGenerator();
    path = new String(fileAndDirectoryGenerator.pathValidator(file));
    fileAndDirectoryGenerator.createDirectoryAndFile(path);
  }

  /**
   * Writing out the passed in <code>PhoneBill</code> object
   * to a file which contains the same customer's name or if
   * there no file exist for passed in <code>PhoneBill</code>'s
   * customer make a new file.
   * @param bill
   * Passed in <code>PhoneBill</code> object to write to file.
   * @throws IOException
   * if write failed, an exception will be thrown.
   */
  @Override
  public void dump(final PhoneBill bill) throws IOException {
    if(bill == null || bill.isEmpty())
      throw new InvalidParameterException("File write failed: Customer's PhoneBill can not be empty");
    isSameCustomer(bill.getCustomer());

    final String pipe = "|";
    File file = new File(path);
    FileWriter fileWriter = new FileWriter(file, true);
    var phoneCalls = bill.getPhoneCalls();

    fileWriter.append(file.length() == 0 ? bill.getCustomer(): "");
    for(PhoneCall phoneCall: phoneCalls)
      fileWriter.append(
          "\n" + phoneCall.getCaller()
          + pipe + phoneCall.getCallee()
          + pipe + phoneCall.getStartTimeString().replace(" ", "|")
          + pipe + phoneCall.getEndTimeString().replace(" ", "|"));
    fileWriter.flush();
    fileWriter.close();
  }

  /**
   * Ensure that the the passed in <code>PhoneBill</code> which
   * has a customer's name matches the files customer's name.
   * @param customer
   * Customer's name from the passed in <code>PhoneBill</code> object.
   * @throws IOException
   * An exception will be thrown if reading from file failed.
   */
  private final void isSameCustomer(final String customer) throws IOException {
    final BufferedReader read = new BufferedReader(new FileReader(path));
    final String readName = read.readLine();
    if (readName == null && customer == null)
      throw new InvalidParameterException("File write failed: PhoneBill has no customer name");
    else if(readName != null && !readName.equals(customer))
      throw new InvalidParameterException("File write failed: File information and customer's phone bill does not match");
    read.close();
  }
  /**
   * Preset directory to place the customer's files
   */
  @Deprecated
  private final String directory = "CustomerPhoneBillFiles";
  /**
   * The Path in which a customer file resides
   */
  private final String path;
}
