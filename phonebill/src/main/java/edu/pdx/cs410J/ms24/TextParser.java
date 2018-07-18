package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is simulate reading a <code>PhoneBill</code> Object from a file.
 *
 * @author Zack Salah
 */

public class TextParser implements PhoneBillParser<PhoneBill> {

  /**
   * Creates a new <code>TestParser</code> object.
   * @param file
   * Name of the file from the command line arguments
   * @throws IOException
   * if reading a file failed.
   */
  TextParser(final String file) throws IOException {
    super();
    var fileAndDirectoryGenerator = new FileAndDirectoryGenerator();
    path = new String(fileAndDirectoryGenerator.pathGenetaror(file,directory));
  }

  /**
   * Validate the read lines from a file and place the them into
   * one <code>PhoneBill</code> object to return.
   * @return <code>PhoneBill</code>
   * return a validated <code>PhoneBill</code> object filled with
   * <code>PhoneCall</code> Objects from the file. If the file is
   * empty or nonexistent file it returns a null.
   * @throws ParserException
   * Throws a paring error if the file not content not formatted
   * correctly.
   */
  @Override
  public PhoneBill parse() throws ParserException {
    final List<String> list;
    PhoneBill readPhoneBill = null;
    ArgumentsValidator argumentsValidator = new ArgumentsValidator();

    try {
      if((list = readFile()) == null)
        return readPhoneBill;
    } catch (IOException e) {
      throw new ParserException("Read from file failed");
    }

    for(String readPhoneCallInfo: list){
      if(readPhoneCallInfo == list.get(0))
        readPhoneBill = new PhoneBill(readPhoneCallInfo);
      else{
        String[] args = readPhoneCallInfo.split("\\|");

        try {
          argumentsValidator.checkArgumentsValidity(args);
        } catch (IllegalArgumentException ex){
          throw new ParserException("Read from file failed. file contains wrong format");
        }

        readPhoneBill.addPhoneCall(new PhoneCall(args));
      }
    }

     return readPhoneBill;
  }

  /**
   * Reads the lines from a specified file.
   * @return List
   * List contains a list of lines read from a file
   * @throws IOException
   * For any reading error.
   */
  public final List readFile() throws IOException{
    File file = new File(path);
    BufferedReader read = null;
    List<String> listOfReadLines = new ArrayList<>();
    String stringReference = null;

    if(!file.exists() || file.length() == 0)
      return null;
    read = new BufferedReader(new FileReader(file));

    while((stringReference = read.readLine()) != null){
      listOfReadLines.add(stringReference);
    }

    return listOfReadLines;
  }

  /**
   * Preset directory to place the customer's files
   */
  private final String directory = "CustomerPhoneBillFiles";
  /**
   * The Path in which a customer file resides
   */
  private final String path;
}
