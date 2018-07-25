package edu.pdx.cs410J.ms24;

import static java.util.Arrays.asList;

import edu.pdx.cs410J.ParserException;
import java.io.IOException;
import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project.
 *
 * @author Zack Salah
 */

public class Project3 {
  /**
   * Main function of the program.
   * @param args
   * Arguments from the command line.
   */
  public static void main(String[] args) throws IOException, ParserException {
    List<String> list = asList(args);
    readMe(list);
    validateArguments(args);
    final PhoneBill readBill = read(list);
    PhoneBill bill = new PhoneBill(args[args.length - MINIMAL_ARGUMENTS_LENGTH], new PhoneCall(args));
    prettify(list, bill.getCustomer(), bill, readBill);
    write(list, bill);
    printOptionExecutor(list, bill);
    System.exit(0);
  }

  /**
    * If the command -pretty is given this method will
   * write or print a prettified <code>PhoneBill</code>
   * on a file or standard IO respectively.
   * @param args
   * Arguments from the command line.
   * @param customerName
   * Name of a customer of the set to the combined <code>PhoneBill</code>
   * objects
   * @param phoneBills
   * Array of <code>PhoneBill</code> object to combine.
   * @throws IOException
   * If opening or witting to file failed.
   */
  private static void prettify(final List args,final String customerName, final PhoneBill... phoneBills) throws IOException {
    int optionIndex;
    if ((optionIndex = args.indexOf("-pretty")) > -1){
      try {
        var fullBill = new PhoneBill(customerName, phoneBills);
        var prettyPrinter = new PrettyPrinter(args.get(optionIndex+1).toString());
        if (args.get(optionIndex+1).equals("-")) {
          prettyPrinter.printOnStandardIO(fullBill);
        }else
          prettyPrinter.dump(fullBill);

      }catch (Exception ex){
        System.err.println(ex.getMessage());
        System.exit(1);
      }
    }
  }

  /**
   * If the command -textFile is given this method will
   * write new PhoneBill object to file.
   * @param args
   * To check for the command -textFile and pass in the file
   * names to operate on.
   * @param newInfo
   * newly entered <code>PhoneBill</code> object to write to file
   * @throws IOException
   * To catch any error occurs in textDumber and formats it to user
   * friendly error
   */
  private static void write(final List args, final PhoneBill newInfo) throws IOException {
    int optionIndex;
    if ((optionIndex = args.indexOf("-textFile")) > -1){
      try {
        TextDumper textDumper = new TextDumper(args.get(optionIndex + 1).toString());
        textDumper.dump(newInfo);
      }catch (Exception ex){
        System.err.println(ex.getMessage());
        System.exit(1);
      }
    }
  }

    /**
   * If the command -textFile is given this method will
   * read a PhoneBill object from a file.
   * @param args
   * To check for the command -textFile and pass in the file
   * names to operate on.
   * @return <code>PhoneBill</code>
   * read and parsed <code>PhoneBill</code> object from a file.
   * @throws IOException
   * To catch any error occurs in textDumber and formats it to user
   * friendly error
   * @throws ParserException
   * If parsing error while reading from file.
   */
  private static PhoneBill read(final List args) throws IOException, ParserException {
    int optionIndex;
    if ((optionIndex = args.indexOf("-textFile")) > -1){
      try {
        TextParser textParser = new TextParser(args.get(optionIndex + 1).toString());
        return textParser.parse();
      }catch (Exception ex){
        System.err.println(ex.getMessage());
        System.exit(1);
      }
    }
    return null;
  }

  /**
   * This function execute the requested option.
   * @param args
   * The list of arguments from the command line.
   * @param bill
   * Its an object of <code >PhoneBill</code> which contain all the
   * customer's information.
   */
  private static void printOptionExecutor(final List args, final PhoneBill bill) {
    final var index = args.indexOf("-print");
    final var textIndex = args.indexOf("-textFile");
    final var prettyIndex = args.indexOf("-pretty");
    if (index > -1 && index < 5 && index-1 != (textIndex > -1?textIndex:-2)
        && index-1 != (prettyIndex > -1?prettyIndex:-2)) {
      System.out.println(bill);
      for (PhoneCall c : bill.getPhoneCalls()) {
        System.out.println(c);
      }
    }
  }

  /**
   * Prints out the README and exits if the option of README is included.
   * @param args
   * The list of arguments from the command line.
   */
  private static void readMe(final List args) {
    final var index = args.indexOf("-README");
    final var textIndex = args.indexOf("-textFile");
    final var prettyIndex = args.indexOf("-pretty");
    if (index > -1 && index < 6 && index-1 != (textIndex > -1?textIndex:-2)
        && index-1 != (prettyIndex > -1?prettyIndex:-2)) {
      System.out.println("PhoneBill Application. \n"
          + "\n"
          + "Name: Zack Salah \n"
          + "\n"
          + "This application stores a customer's phone bill information. "
          + "PhoneBill lists\nthe name of the customer and number of phone "
          + "calls made. PhoneBill also lists\na record of individual phone "
          + "calls that a customer has received or initiated.\nThe phone call "
          + "records contain the caller phone number, callee phone number,\n"
          + "start date and time of the call and the end date and time "
          + "of the call.\n"
          + "\n"
          + "Usage:\n"
          + "Project1 [Option] <Arguments>\n"
          + "\n"
          + "Arguments must be in the following order and format:\n"
          + "Name --> if contains spaces use quotes \"Name With Spaces\"\n"
          + "Caller Phone Number --> XXX-XXX-XXXX \n"
          + "Callee Phone Number --> XXX-XXX-XXXX\n"
          + "Start Date and Time --> XX/XX/XXXX XX:XX am/pm\n"
          + "End Date and Time --> XX/XX/XXXX XX:XX am/pm\n"
          + "\n"
          + "Options are in any order:\n"
          + "-pretty filename/- read/write to file/read and pretty print command line\n"
          + "-textFile filename read/write from/to file\n"
          + "-README Prints the README Information\n"
          + "-print Prints phone call information\n");
      System.exit(0);
    }
  }

  /**
   * This function ensure the validity of the command line arguments.
   * It also ensure that user friendly error print outs.
   * @param args
   * The list of arguments from the command line.
   */
  private static void validateArguments(final String[] args) {
    ArgumentsValidator validator = new ArgumentsValidator();
    if (args.length < MINIMAL_ARGUMENTS_LENGTH) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }
    try {
      if (!validator.argumentsValidator(args)) {
        System.err.println("Arguments are not ordered correctly."
            + " Please enter in this order -->[Options] Name Caller"
            + " Callee StartDate StartTime EndData EndTime");
        System.exit(1);
      }
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      System.exit(1);
    }
  }
  /**
   * The minimal lengths of arguments that a user must enter.
   */
  private static final int MINIMAL_ARGUMENTS_LENGTH = 9;

}