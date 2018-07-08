package edu.pdx.cs410J.ms24;
import static java.util.Arrays.asList;

/**
 * The main class for the CS410J Phone Bill Project
 *
 * @author Zack Salah
 */
public class Project1 {

    public static void main(String[] args) {
      readMe(args);
      validateArguments(args);
      PhoneBill bill = new PhoneBill(args[args.length - 7], new PhoneCall(args));
      System.out.println(bill);
      optionExecutor(args, bill);
      System.exit(0);
    }

    /**
     * This function execute the requested option.
     * @param args
     * The list of arguments from the command line.
     * @param bill
     * Its an object of <code >PhoneBill</code> which contain all the customer's information.
     */
    private static void optionExecutor(String[] args, PhoneBill bill) {
        if(asList(args).contains("-print") && asList(args).indexOf("-print") < 2)
            for(PhoneCall c: bill.getPhoneCalls())
                System.out.println(c);
    }

    /**
     * Prints out the README and exits if the option of README is included.
     * @param args
     * The list of arguments from the command line.
     */
    private static void readMe(String[] args) {
        if(asList(args).contains("-README") && asList(args).indexOf("-README") < 2) {
            System.out.println("PhoneBill Application. \n" +
            "\n" +
            "This application stores a customer's phone bill information. PhoneBill lists the name of the" +
            "customer and number of phone calls made.\nPhoneBill also lists a record of individual phone calls" +
            "that a customer has received or initiated. The phone call records contain\nthe caller phone number," +
            "callee phone number, start date and time of the call and the end date and time of the call.\n" +
            "\n" +
            "Usage:\n" +
            "Project1 [Option] <Arguments>\n" +
            "\n" +
            "Arguments must be in the following order and format:\n" +
            "Name --> if contains spaces use quotes \"Name With Spaces\"\n" +
            "Caller Phone Number --> XXX-XXX-XXXX \n" +
            "Callee Phone Number --> XXX-XXX-XXXX\n" +
            "Start Date and Time --> XX/XX/XXXX XX:XX\n" +
            "End Date and Time --> XX/XX/XXXX XX:XX\n" +
            "\n" +
            "Options are in any order:\n" +
            "-print Prints phone call information\n" +
            "-README Prints the README Information");
            System.exit(0);
        }
    }

    /**
     * This function ensure the validity of the command line arguments.
     * It also ensure that user friendly error print outs.
     * @param args
     * The list of arguments from the command line.
     */
    private static void validateArguments(String[] args) {
    ArgumetsValidator validitor = new ArgumetsValidator();
    if(args.length < 7) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }
    try{
      if(!validitor.argumentsValidator(args)) {
        System.err.println("Arguments are not ordered correctly. Please enter in this order -->"
                        +  "[Options] Name Caller Callee StartDate StartTime EndData EndTime");
        System.exit(1);
      }
    }catch (Exception ex){
      System.err.println(ex.getMessage());
      System.exit(1);
    }
  }

}