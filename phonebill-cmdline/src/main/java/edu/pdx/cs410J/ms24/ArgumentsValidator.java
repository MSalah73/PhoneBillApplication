package edu.pdx.cs410J.ms24;

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
   * <code>validateOrder</code>
   * @param args
   * Arguments pass from the command line
   * @return True if both function return true. it return false if <code>validateOrder</code>
   * return false. if checkArgumentValidator is false it throw an exception.
   */
  final boolean argumentsValidator(final String[] args) {
    return checkArgumentsValidity(args);
  }

  /**
   * This function checks the order of the arguments.
   * @param args
   * Arguments pass from the command line.
   * @return true if all matches the regex. Otherwise return false
   */
  @Deprecated
  final boolean validateOrder(final String[] args) {
    var length = args.length;   boolean correctOrder;
    correctOrder = args[--length].matches("^.*?:.*?$") && args[--length].matches("^(.*?/){2}.*?$");
    correctOrder = correctOrder && args[--length].matches("^.*?:.*?$") && args[--length].matches("^(.*?/){2}.*?$");
    correctOrder = correctOrder && args[--length].matches("^(.*?-){2}.*?$") && args[--length].matches("^(.*?-){2}.*?$");
    correctOrder = correctOrder && args[--length].matches("^[^-].*?$");
    return correctOrder;
  }

  /**
   * This function checks if the arguments passed in matches the formats they expected to be in.
   * @param args
   * Arguments pass from the command line
   * @return True if all functions return true. Otherwise it throw an exception
   */
  final boolean checkArgumentsValidity(final String[] args) {
    int customerArgsEndIndex = args.length;     boolean correctOrder;
    correctOrder = validatePM_AM(args[--customerArgsEndIndex]);
    correctOrder = correctOrder && validateTime(args[--customerArgsEndIndex]) && validateDate(args[--customerArgsEndIndex]);
    correctOrder = correctOrder && validatePM_AM(args[--customerArgsEndIndex]);
    correctOrder = correctOrder && validateTime(args[--customerArgsEndIndex]) && validateDate(args[--customerArgsEndIndex]);
    correctOrder = correctOrder && validatePhoneNumber(args[--customerArgsEndIndex]) && validatePhoneNumber(args[--customerArgsEndIndex]);
    --customerArgsEndIndex;
    if(customerArgsEndIndex > 0)
      correctOrder = validateOptions(args, customerArgsEndIndex);

    return correctOrder;
  }

  /**
  * check if the arguments has valid options.
  * @param args
  * Arguments from the command line.
  * @param customerArgsEndIndex
  * The number of options in the arguments.
  * @return True if matches the regex.
  * @throws IllegalArgumentException if passed in option does not match regex.
  */
  final boolean validateOptions(final String[] args, int customerArgsEndIndex){
    var isValid = false;
    for (--customerArgsEndIndex; customerArgsEndIndex > -1; --customerArgsEndIndex) {
      var option = args[customerArgsEndIndex];
      if (option.matches("^-(print|README)$") &&
          (customerArgsEndIndex > 0? !args[customerArgsEndIndex-1].matches("^-(textFile|pretty)$"):true)) {
        isValid = true;
      } else if (option.matches("^-(textFile|pretty)$") && args.length == 10) {
        throw new IllegalArgumentException("-textFile or -pretty option is missing a file name.");
      } else if (customerArgsEndIndex > 0 && args[customerArgsEndIndex - 1].matches("^-(textFile|pretty)$")) {
        isValid = true;
        --customerArgsEndIndex;
      } else
        throw new IllegalArgumentException(
            "Option must be either -print, -README, -textFile filename, or -pretty filename in" +
                " any order before customer's information e.g [Option] <args>");
    }
    return isValid;
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
  final boolean validatePM_AM(final String marker){
    if (marker.matches(moringEveningFormat))
      return true;
    throw new IllegalArgumentException("Marker must be either am or pm");
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
