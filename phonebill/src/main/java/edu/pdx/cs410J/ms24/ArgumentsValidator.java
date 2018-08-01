package edu.pdx.cs410J.ms24;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * This class Validate the Arguments passed from the command line.
 *
 * @author Zack Salah
 */
class ArgumentsValidator {
  /**
   * Create Arguments validator object.
   */
  ArgumentsValidator() {
  }

  /**
   * Validate Arguments pass from command line by calling two functions.
   * <code>argumentsValidator</code>
   * @param args
   * Arguments pass from the command line
   * @return True if both function return true. it return false if <code>validateOrder</code>
   * return false. if checkArgumentValidator is false it throw an exception.
   */
  final boolean argumentsValidator(final String[] args) {
    var list = Arrays.asList(args);
    var hash = new HashSet(Arrays.asList(args));
    var portIndex = list.indexOf("-port");
    var hostIndex = list.indexOf("-host");
    var missingArgs = new IllegalArgumentException("Missing command line Arguments");
    if(list.size() < 5 ){ throw missingArgs;}
    if (hash.contains("-print") && hash.contains("-search"))
      throw new IllegalArgumentException("Search and print option can not be applied together");
    if (!hash.contains("-print") && !hash.contains("-search") && list.size() == 5) {
      if(!(hostIndex % 2 == 0 && portIndex % 2 == 0)){throw new IllegalArgumentException("command line arguments not ordered correctly");}
      return validateHostAndPort(list); //add ex from caller wrong order
    }else if(hash.contains("-search")) {
      if (list.size() > 11 == false) {throw missingArgs;}
      return validateHostAndPort(list) && checkArgumentsValidity(args, true);
    }else {
      if (list.size() > (hash.contains("-print") ? 13 : 12) == false){throw missingArgs;}
      return validateHostAndPort(list) && checkArgumentsValidity(args, false);
    }
  }

  /**
   * This function checks if the arguments passed in matches the formats they expected to be in.
   * @param args
   * Arguments pass from the command line
   * @return True if all functions return true. Otherwise it throw an exception
   */
  final boolean checkArgumentsValidity(final String[] args, final boolean isSearch) {
    int customerArgsEndIndex = args.length;     boolean correctOrder;     var list = Arrays.asList(args);

    correctOrder = validatePM_AM(args[--customerArgsEndIndex]);
    correctOrder = correctOrder && validateTime(args[--customerArgsEndIndex]) && validateDate(args[--customerArgsEndIndex]);
    correctOrder = correctOrder && validatePM_AM(args[--customerArgsEndIndex]);
    correctOrder = correctOrder && validateTime(args[--customerArgsEndIndex]) && validateDate(args[--customerArgsEndIndex]);
    if(!isSearch)
      correctOrder = correctOrder && validatePhoneNumber(args[--customerArgsEndIndex]) && validatePhoneNumber(args[--customerArgsEndIndex]);
    return correctOrder && validateOptions(args, customerArgsEndIndex - 2);
  }

  /**
  * check if the arguments has valid options.
  * @param args
  * Arguments from the command line.
  * @param customerArgsEndIndex
  * The number of options in the arguments
  * @return True if matches the regex.
  * @throws IllegalArgumentException if passed in option does not match regex.
  */
  final boolean validateOptions(final String[] args, int customerArgsEndIndex){
    for (var i = customerArgsEndIndex; i > 0; --i) {
      if (args[i].matches("^-(print|search)$"))
        continue;
      else if (i > 0 && args[i - 1].matches("^-(host|port)$")) {
        --i;
        continue;
      } else
        throw new IllegalArgumentException("Command line argument \""+args[i]+"\" is misplaced");
    }
    return true;
  }
  /**
  * Checks if the passed in phone number is valid.
  * @param phoneNumber
  * The phone number can be either their phone number of callee phone number.
  * @return True if matches the regex.
  * @throws IllegalArgumentException if passed in option does not match regex.
  */
  final boolean validatePhoneNumber(final String phoneNumber){
    if(phoneNumber.matches(phoneFormat))
      return true;
    throw new IllegalArgumentException("Phone number must in this format XXX-XXX-XXXX where X represent a number");
  }

  /**
  * Check is passed time from the arguments is valid.
  * @param time
  * Time of either if start or end of the phone call.
  * @return True if matches the regex.
  * @throws IllegalArgumentException if passed in option does not match regex.
  */
  final boolean validateTime(final String time) {
    if (time.matches(newTimeFormat))
      return true;
    throw new IllegalArgumentException("Time must be in this format HH:MM where H and M represent a number");
  }

  /**
   * This method validate the am/pm marker
   * @param marker
   * passed in from the command line arguments to validate
   * @return
   * true on valid marker
   * @throws IllegalArgumentException
   * On invalid marker
   */
  final boolean validatePM_AM(final String marker){
    if (marker.matches(moringEveningFormat))
      return true;
    throw new IllegalArgumentException("Marker must be either am or pm");
  }

  /**
   * This method validate the host and port options
   * @param args
   * command line arguments as <code>list</code>
   * @return
   * true if both host and port option are valid
   * @throws IllegalArgumentException
   * On missing host or/and port, -port or -host option set to port or host,
   * command line arguments has multiple host or ports, or port option contains non-number value
   */
  final boolean validateHostAndPort(final List<String> args){
    int hostIndex = args.indexOf("-host");
    int portIndex = args.indexOf("-port");
    if(portIndex < 0 || hostIndex < 0)
      throw new IllegalArgumentException("Missing: \n"+(portIndex < 0?"port\n":"")+(hostIndex < 0?"host":""));
    else if(Collections.frequency(args,"-port") != 1 || Collections.frequency(args,"-host") != 1)
      throw new IllegalArgumentException("Command line arguments can not have duplicates port or host");
    else if(args.get(hostIndex+1).matches("^-(port|print|search)$"))
      throw new IllegalArgumentException("-host option can not have \"-port\" as a Host");
    try {
      Integer.parseInt(args.get(portIndex + 1));
    }catch (NumberFormatException ex){
      throw new IllegalArgumentException("Port \"" + args.get(portIndex+1) + "\" must be an integer");
    }
    return true;
  }

  /**
  * Check is passed date from the arguments is valid.
  * @param date
  * Date of either if start or end of the phone call.
  * @return True if matches the regex.
  * @throws IllegalArgumentException if passed in option does not match regex.
  */
  final boolean validateDate(final String date){
    if(date.matches(newDateFormat))
      return true;
    throw new IllegalArgumentException("Date must be in this format MM/DD/YYYY - M,D, and Y represent a number.");
  }

  /**
   * This is a regex to check if entered phone number is in correct format.
   */
  private String phoneFormat = "^(\\d{3}-){2}\\d{4}$";
  /**
   * This is a regex to check if entered time is in correct format.
   */
  @Deprecated
  private String timeFormat = "^((00)|(0?[1-9])|(1\\d)|(2[0-3])):[0-5]\\d$";

  private String newTimeFormat = "^\\d{1,2}:\\d{1,2}$";
  /**
   * This is a regex to check if entered date is in correct format.
   */
  @Deprecated
  private String dateFormat = "^(((0?[13578]|1[02])/(0?[1-9]|[12]\\d|3[01]))|"
      + "((0?[469]|11)/(0?[1-9]|[12]\\d|30))|(0?2/(0?[1-9]|1\\d|2[0-8])))/\\d{4}$";

  private String newDateFormat = "^(\\d{1,2}/){2}\\d{4}$";

  /**
   * This is a regex to check pm/am
   */
  private String moringEveningFormat = "^[PpAa][Mm]$";

}
