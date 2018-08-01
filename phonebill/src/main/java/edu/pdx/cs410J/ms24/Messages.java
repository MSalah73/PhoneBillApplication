package edu.pdx.cs410J.ms24;

import java.util.Date;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{

  /**
   * This help to display user-friendly error when missing an argument
   * @param parameterName
   * The missed argument
   * @return
   * Formatted and user-friendly error
   */
  public static String missingRequiredParameter( String parameterName )
  {
    return String.format("The required parameter \"%s\" is missing", parameterName);
  }

  /**
   * This method format a string after <code>PhoneCall</code> addition to the server
   * @param call
   * Fhe <code>PhoneCall</code> added to the server
   * @param print
   * Option to print <code>PhoneCall</code> detail or not
   * @return
   * Formatted and user-friendly message
   */
  public static String phoneCallAdded(final PhoneCall call,String print){
    if(print == null || !print.equals("print"))
      return "Phone call have been added";
    else
      return "Phone call have been added:\n"+call.toString();
  }

  /**
   * This method when all the arguments are valid but requested
   * customer does not exist in the server
   * @param customer
   * Request customer name
   * @return
   * Formatted and user-friendly error
   */
  public static String customerDoesNotExist(final String customer){
    return "\""+customer + "\" does not have a registered phone bill";
  }
  /**
   * This method call prettify on a passed in <code>PhoneBill</code>
   * @param phoneBill
   * <code>PhoneBill</code> to prettify
   * @return
   * Formatted pretty <code>PhoneBill</code> message
   */
  public static String prettyPrintPhoneBill(PhoneBill phoneBill){
    PrettyPrinter prettyPrinter = new PrettyPrinter();
    return prettyPrinter.prettifyPhoneBill(phoneBill);
  }

  /**
   * This method call search <code>PhoneCall</code>s in a
   * <code>PhoneBill</code> and prettify the output of the
   * <code>PhoneCall</code>s
   * @param phoneBill
   * <code>PhoneBill</code> to search
   * @param startDate
   * Start date of the search
   * @param endDate
   * End date of the search
   * @return
   * Formatted and pretty found <code>PhoneCall</code>s
   */
  public static String searchPhoneBill(PhoneBill phoneBill, Date startDate, Date endDate){
    return phoneBill.searchPhoneCalls(startDate, endDate);
  }

  /**
   * Inform that all the phone bills have been deleted
   * @return
   * Formatted message
   */
  public static String allPhoneBillsEntriesDeleted(){
    return "All phone bills entries have been deleted";
  }
}
