package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.PhoneBillDumper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class prettifies a <code>PhoneBill</code> Object to either print
 * ont Standard IO or writing the pretty <code>PhoneBill</code> information
 * one specified file.
 *
 * @author Zack Salah
 */
public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {

  /**
   * This creates <code>PrettyPrinter</code> object.
   * @param filename
   * @throws IOException
   */
  PrettyPrinter(String filename) throws IOException {
    super();
    var pathValidator = new FileAndDirectoryGenerator();
    path = pathValidator.pathValidator(filename);
    pathValidator.createDirectoryAndFile(path);
    if(filename.equals("-")){
      File f = new File(filename+".txt");
      f.deleteOnExit();
    }
  }

  /**
   * This method writes a prettified verizon of <code>PhoneBill</code>
   * to a specified file.
   * @param bill
   * <code>PhoneBill</code> to prettify and write to a file.
   * @throws IOException
   * If something went wrong with opening or witting to a file
   * @throws IllegalArgumentsException
   * When the specified file is -.txt
   */
  @Override
  public void dump(PhoneBill bill) throws IOException {
    if (!path.equals("-.txt")) {
      File file = new File(path);
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(prettifyPhoneBill(bill));
      fileWriter.flush();
      fileWriter.close();
    } else {
      throw new IllegalArgumentException("File given is not supported");
    }
  }

  /**
   * This method print prettified <code>cPhoneBill</code>
   * to standard IO
   * @param phoneBill
   * <code>PhoneBill</code> to prettify
   */
  final public void printOnStandardIO(PhoneBill phoneBill){
    System.out.println(prettifyPhoneBill(phoneBill));
  }

  /**
   * This method prettifies a <code>PhoneBill</code> content and format it as a <code>String</code>
   * @param phoneBill
   * <code>PhoneBill</code> to prettify
   * @return
   * A prettified <code>PhoneBill</code> as a <code>String</code>
   *
   */
  private final String prettifyPhoneBill(PhoneBill phoneBill){
    var size = phoneBill.getPhoneCalls().size();
    int counter = size;
    String pretty = "Customer: "+phoneBill.getCustomer()+"\nNumber of phone calls: "+size+"\n";
    pretty += "#     Caller Phone Number     Callee Phone Number     Call Started At     Call Ended At     Call Duration\n";
    pretty += "---------------------------------------------------------------------------------------------------------\n";
    for (PhoneCall c : phoneBill.getPhoneCalls()) {
      pretty += String.format("%-8d %-23s %-19s %-19s %-18s %d Minutes\n",size - --counter, c.getCaller(),c.getCallee(),
          c.getStartTimeString(), c.getEndTimeString(), c.callDuration());
    }
    return pretty;
  }

  /**
   * Path used when writing a prettified <code>PhoneBill</code> to file
   * by calling <code>dumb</code> method.
   */
  final private String path;
}
