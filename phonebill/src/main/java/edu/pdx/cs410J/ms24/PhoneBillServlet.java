package edu.pdx.cs410J.ms24;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet provides a REST API for working with an
 * <code>PhoneBill</code>. It adds, search, and print all
 * requested <code>PhoneBill</code>s.
 */
public class PhoneBillServlet extends HttpServlet
{

  /**
   * Parameter for print option
   */
  static final String PRINTOPTION_PARAMETER = "print";
  /**
   * Parameter for search option
   */
  static final String SEARCHOPTION_PARAMETER = "search";
  /**
   * Parameter for print all option
   */
  static final String PRINTALLOPTION_PARAMETER = "printall";
  /**
   * Parameter for adding a <code>PhoneCall</code>
   */
  static final String[] PHONECALL_PARAMETER ={
      "customer","caller","callee",
      "startdate","starttime", "startmarker",
      "enddate","endtime","endmarker"};

  /**
   * Date structure to store all <code>PhoneBill</code>s
   */
  private final Map<String, PhoneBill> Phonebills = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by either search and write <code>PhoneBill</code>s
   * or writing all the content of <code>PhoneBill</code> to HTTP parameter to the HTTP response.
   * If customer not found than it writes error messages to HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
    response.setContentType( "text/plain" );
    String search = getParameter(SEARCHOPTION_PARAMETER, request);
    String printall = getParameter(PRINTALLOPTION_PARAMETER, request);

    if (search != null)
      processSearch(request, response);
    else if(printall != null)
      processPrintAll(request, response);
    else
      missingRequiredParameter(response, printall +" or "+search);
  }


  /**
   * Handles an HTTP POST request by storing the <code>PhoneCall</code> entry for the
   * <code>PhoneCallInfo</code> parameter and print option. depending on the option passed in
   * writes different message to the HTTP response.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
    response.setContentType( "text/plain" );
    ArrayList<String> list = new ArrayList<>();
    var Print = getParameter(PRINTOPTION_PARAMETER, request );
    PhoneCall phoneCall = null;
    for(String parameter: PHONECALL_PARAMETER)
      list.add(getParameter(parameter, request ));

    for (int i = 0; i < list.size();++i) {
      if(list.get(i) == null) {
        missingRequiredParameter(response, PHONECALL_PARAMETER[i]);
        return;
      }
    }

    try {
      phoneCall = new PhoneCall(list.toArray(new String[0]));
    } catch (InvalidParameterException e) {
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage());
      return;
    }

    if(this.Phonebills.get(list.get(0)) == null)
      this.Phonebills.put(list.get(0), new PhoneBill(list.get(0),phoneCall));
    else {
      var to_add = this.Phonebills.get(list.get(0));
      to_add.addPhoneCall(phoneCall);
      this.Phonebills.put(list.get(0), to_add);
    }

    messageWithOK(response, Messages.phoneCallAdded(phoneCall, Print));
  }

  /**
   * Handles an HTTP DELETE request by removing all <code>PhoneBill</code>s.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/plain");

      this.Phonebills.clear();

      messageWithOK(response,Messages.allPhoneBillsEntriesDeleted());
  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
    String message = Messages.missingRequiredParameter(parameterName);
    response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }
   /**
   * Writes an error message if the requested customer's <code>PhoneBill</code> is not found
   * to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#customerDoesNotExist(String)}}
   */
  private void customerDoesNotExist( HttpServletResponse response, String customer )
      throws IOException
  {
    String message = Messages.customerDoesNotExist(customer);
    response.sendError(HttpServletResponse.SC_NOT_FOUND, message);
  }
   /**
   * Writes an the message from the post, get, or delete request to the response with code 200
   */
  private void messageWithOK(HttpServletResponse response, final String message) throws IOException
  {
    PrintWriter pw = response.getWriter();
    pw.println(message);

    pw.flush();

    response.setStatus( HttpServletResponse.SC_OK );
  }

  /**
   * This method does the search on all stored <code>PhoneBill</code>s from the requested customer' name
   * start date and end date.
   * @throws IOException
   * if the start date is after end date
   */
  private final void processSearch(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    ArrayList<String> list = new ArrayList<>();

    for(String parameter: PHONECALL_PARAMETER) {
      if(parameter == "caller" || parameter == "callee")
        continue;
      list.add(getParameter(parameter, request));
    }
    for (int i = 0; i < list.size();++i)
      if(list.get(i) == null) {
        missingRequiredParameter(response, i == 0?PHONECALL_PARAMETER[i]:PHONECALL_PARAMETER[i+2]);
        return;
      }

    PhoneBill phonebill = Phonebills.get(list.get(0));
    if(phonebill == null) {
      customerDoesNotExist(response, list.get(0));
      return;
    }

    var startDate = new Date(list.get(1)+" "+list.get(2)+" "+list.get(3));
    var endDate = new Date(list.get(4)+" "+list.get(5)+" "+list.get(6));
    String message = null;
    try {
      message = Messages.searchPhoneBill(phonebill,startDate,endDate);
    }catch (InvalidParameterException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
      return;
    }

    messageWithOK(response, message);
  }

  /**
   * This method does the writes all the information a <code>PhoneBill</code>s from the requested customer' name
   * @throws IOException
   * if the requested name does not have registered <code>PhoneBill</code>
   */
  private final void processPrintAll(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String customer = getParameter(PHONECALL_PARAMETER[0], request);
    if(customer == null) {
      missingRequiredParameter(response, PHONECALL_PARAMETER[0]);
      return;
    }
    PhoneBill phonebill = Phonebills.get(customer);
    if(phonebill == null) {
      customerDoesNotExist(response, customer);
      return;
    }
    messageWithOK(response, Messages.prettyPrintPhoneBill(phonebill));
  }
  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }
}
