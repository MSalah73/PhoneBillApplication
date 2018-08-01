package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 *
 * @author Zack Salah
 */
public class Project4 {

  /**
   * String with missing arguments for simplifying outputs
   */
  public static final String MISSING_ARGS = "Missing command line arguments";
  /**
   * Main function of the program.
   * @param args
   * Arguments from the command line.
   */
  public static void main(String... args) throws IOException {
    ArgumentsValidator argumentsValidator = new ArgumentsValidator();
    ArrayList<String> list = new ArrayList<>();
    list.addAll(List.of(args));
    readMe(list);
    try {
      argumentsValidator.argumentsValidator(args);
    } catch (Exception e) {
      usage(e.getMessage());
    }
    var port = list.get(list.indexOf("-port") + 1);
    var host = list.get(list.indexOf("-host") + 1);
    list.remove("-host");
    list.remove("-port");
    list.remove(host);
    list.remove(port);

    PhoneBillRestClient client = new PhoneBillRestClient(host, Integer.parseInt(port));

    try {
      if (list.size() == 1)
        System.out.println(client.displayPhoneBill(list.get(0)).getContent());
      else if (list.removeAll(Collections.singleton("-search")))
        System.out.println(
            client.searchWithinDates(list.toArray(new String[0]), list.size()).getContent());
      else
        System.out.println(client.addPhoneCallEntry(list.removeAll(Collections.singleton("-print"))
            , list.toArray(new String[0]), list.size()).getContent());
    } catch (UnknownHostException e) {
      error("host \""+e.getMessage()+"\" is either down or it does not exist");
    } catch (ConnectException e) {
      error("Connection refused - Port \""+port+"\" is not open for connection.");
    } catch (Exception e){
      error(e.getMessage());
    }
    System.exit(0);
 }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
      if (response.getCode() != code) {
        error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                            response.getCode(), response.getContent()));
      }
    }

  /**
   * For simplifying error display
   * @param message
   * message to display as an error
   */
  private static void error( String message )
    {
      PrintStream err = System.err;
      err.println("** " + message);

      System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message ) {
      PrintStream err = System.err;
      err.println("** " + message);
      err.println();
      err.println("usage: edu.pdx.cs410J.ms24.Project4 [options] <args> ");
      err.println("args are (in this order):");
      err.println("  customer              Customer's name");
      err.println("  caller                Caller Phone number - XXX-XXX-XXXX ");
      err.println("  callee                Callee Phone number - XXX-XXX-XXXX ");
      err.println("  startTime             The start Date and time of the phone call - XX/XX/XXXX XX:XX am/pm");
      err.println("  endTime               The end Date and time of the phone call - XX/XX/XXXX XX:XX am/pm");
      err.println();
      err.println("option may appear in any order");
      err.println("  -host                 Host of web server");
      err.println("  -port                 Port of web server");
      err.println("  -print                Print newly added phone call");
      err.println("  -search               Search phone calls within specified start date and end date");
      err.println("  -README               Print information about the usage and a description");
      err.println();
      err.println("Note: ");
      err.println("1 - host nd port must be included together");
      err.println("2 - Do not include caller and callee with -search option" );
      err.println("3 - -print and -search options can not be specified together" );
      err.println("4 - To print all phone bill information - specify only customer's name");
      err.println();

      System.exit(1);
    }
  /**
   * Prints out the README and exits if the option of README is included.
   * @param args
   * The list of arguments from the command line.
   */
    private static void readMe(final List args) {
      final var index = args.indexOf("-README");
      final var portIndex = args.indexOf("-port");
      final var hostIndex = args.indexOf("-host");
      if (index > -1 && index < 7 && index-1 != (portIndex > -1?portIndex:-2)
          && index-1 != (hostIndex > -1?hostIndex:-2)) {
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
            + "Project4 [Option] <Arguments>\n"
            + "\n"
            + "Arguments must be in the following order and format:\n"
            + "Name --> if contains spaces use quotes \"Name With Spaces\"\n"
            + "Caller Phone Number --> XXX-XXX-XXXX \n"
            + "Callee Phone Number --> XXX-XXX-XXXX\n"
            + "Start Date and Time --> XX/XX/XXXX XX:XX am/pm\n"
            + "End Date and Time --> XX/XX/XXXX XX:XX am/pm\n"
            + "\n"
            + "Options are in any order:\n"
            + "-host    -     Host of the web server\n"
            + "-port    -     Port of the web server\n"
            + "-README  -     Prints the README Information\n"
            + "-print   -     Prints newly added phone call information\n"
            + "-search  -     Search phone calls within a specified start date and end date\n"
            + "\n"
            + "Note: "
            + "1 - host nd port must be included together\n"
            + "2 - Do not include caller and callee with -search option\n"
            + "3 - -print and -search options can not be specified together\n"
            + "4 - To print all phone bill information - specify only customer's name");
        System.exit(0);
      }
    }
}