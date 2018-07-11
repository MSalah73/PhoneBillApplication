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
    return validateOrder(args) && chackArgumentsValidity(args);
  }

  /**
   * This function checks the order of the arguments.
   * @param args
   * Arguments pass from the command line.
   * @return true if all matches the regex. Otherwise return false
   */
  final boolean validateOrder(final String[] args) {
    var length = args.length;   boolean correctOrder;
    correctOrder = args[--length].matches("^[\\d:]+?$") && args[--length].matches("^[\\d/]+?$");
    correctOrder = correctOrder && args[--length].matches("^[\\d:]+?$") && args[--length].matches("^[\\d/]+?$");
    correctOrder = correctOrder && args[--length].matches("^[\\d-]+?$") && args[--length].matches("^[\\d-]+?$");
    correctOrder = correctOrder && args[--length].matches("^[^-].*?$");
    return correctOrder;
  }

  /**
   * This function checks if the arguments passed in matches the formats they expected to be in.
   * @param args
   * Arguments pass from the command line
   * @return True if all functions return true. Otherwise it throw an exception
   */
  final boolean chackArgumentsValidity(final String[] args) {
    int customerArgsEndIndex = args.length;     boolean correctOrder;
    correctOrder = validateTime(args[--customerArgsEndIndex]) && validateDate(args[--customerArgsEndIndex]);
    correctOrder = correctOrder && validateTime(args[--customerArgsEndIndex]) && validateDate(args[--customerArgsEndIndex]);
    correctOrder = correctOrder && validatePhoneNumber(args[--customerArgsEndIndex]) && validatePhoneNumber(args[--customerArgsEndIndex]);
    --customerArgsEndIndex;
    if(customerArgsEndIndex > 0)
      for (--customerArgsEndIndex; customerArgsEndIndex > -1; --customerArgsEndIndex)
        correctOrder = validateOption((args[customerArgsEndIndex]));

    return correctOrder;
  }

  /**
  * Check is passed option from the arguments is valid.
  * @param option
  * Option begins with - from the command arguments
  * @return True if matches the regex.
  * @throws IllegalArgumentException if passed in option does not match regex.
  */
  final boolean validateOption(final String option){
    if(option.matches("^-(print|README)$"))
      return true;
    throw new IllegalArgumentException("Option must be either -print or -README in" +
        " any order before customer's information e.g [Option] <args>");
  }

  /**
  * Checks if the passed in phone number is valid.
  * @param phoneNumber
  * The phone number can be eithr their phone number of callee phone number.
  * @return True if matches the regex.
  * @throws IllegalArgumentException if passed in option does not match regex.
  */
  final boolean validatePhoneNumber(final String phoneNumber){
    if(phoneNumber.matches(phoneFormat))
      return true;
    throw new IllegalArgumentException("Phone number must in this format XXX-XXX-XXXX");
  }

  /**
  * Check is passed time from the arguments is valid.
  * @param time
  * Time of either if start or end of the phone call.
  * @return True if matches the regex.
  * @throws IllegalArgumentException if passed in option does not match regex.
  */
  final boolean validateTime(final String time){
    if(time.matches(timeFormat))
      return true;
    throw new IllegalArgumentException("Time must be in this format XX:XX where the first" +
        " XX is between 00 to 23 and the second XX is between 00 to 59");
  }

  /**
  * Check is passed date from the arguments is valid.
  * @param date
  * Date of either if start or end of the phone call.
  * @return True if matches the regex.
  * @throws IllegalArgumentException if passed in option does not match regex.
  */
  final boolean validateDate(final String date){
    if(date.matches(dateFormat))
      return true;
    throw new IllegalArgumentException("Date must be in this format XX/XX/XXXX");
  }

  /**
   * This is a regex to check if entered phone number is in correct format.
   */
  private String phoneFormat = "^\\d{3}-\\d{3}-\\d{4}$";
  /**
   * This is a regex to check if entered time is in correct format.
   */
  private String timeFormat = "^((00)|(0?[1-9])|(1\\d)|(2[0-3])):[0-5]\\d$";
  /**
   * This is a regex to check if entered date is in correct format.
   */
  private String dateFormat = "^(((0?[13578]|1[02])/(0?[1-9]|[12]\\d|3[01]))|"
      + "((0?[469]|11)/(0?[1-9]|[12]\\d|30))|(0?2/(0?[1-9]|1\\d|2[0-8])))/\\d{4}$";

}
