package edu.pdx.cs410J.ms24;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL. I have modified this class
 * to add phone call, search phone calls, print all phone calls in a phone bill.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{

  /**
   * Set web directory
   */
  private static final String WEB_APP = "phonebill";
  /**
   * Set ser sub directory
   */
  private static final String SERVLET = "calls";

  /**
   * The url in which host,port, servlet, and web_app is formatted and stored
   */
  private final String url;


  /**
   * Creates a client to the Phone Bil REST service running on the given host and port
   * @param hostName The name of the host
   * @param port The port
   */
  public PhoneBillRestClient( String hostName, int port )
  {
    this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
  }

  /**
   * this method does a post request to send command line arguments
   * to the server to manipulated and turn it into a phone call
   * @param option
   * Option to print added <code>PhoneCall</code>
   * @param phoneCallInfo
   * command line arguments which contain <code>PhoneCall</code> information
   * @param size
   * the size of the command line argument
   * @return
   * <code>Response</code> modified from the server
   * @throws IOException
   * if the code of the <code>Response</code> is not 200
   */
  public Response addPhoneCallEntry(final boolean option, final String[] phoneCallInfo, int size) throws IOException {
    size = size - 9;
    Response response = postToMyURL(option?"print":"", option?"print":"","customer",phoneCallInfo[size],
        "caller", phoneCallInfo[++size], "callee",phoneCallInfo[++size],
        "startdate",phoneCallInfo[++size], "starttime",phoneCallInfo[++size],
        "startmarker",phoneCallInfo[++size], "enddate",phoneCallInfo[++size],
        "endtime",phoneCallInfo[++size], "endmarker",phoneCallInfo[++size]);
    return throwExceptionIfNotOkayHttpStatus(response);
  }

  /**
   * This method does a get request that send command line arguments
   * to manipulate the data to search <code>PhoneBill</code>'s <code>PhoneCall</code>s
   * to pretty print
   * @param searchInfo
   * command line arguments which contain information to search <code>PhoneCall</code>s
   * @param size
   * the size of the command line argument
   * @return
   * <code>Response</code> modified from the server
   * @throws IOException
   * if the code of the <code>Response</code> is not 200
   */
  public Response searchWithinDates(final String[] searchInfo, int size) throws IOException{
    size = size - 7;
    Response response = get(this.url,"search","search","customer",searchInfo[size],
        "startdate",searchInfo[++size], "starttime",searchInfo[++size],
        "startmarker",searchInfo[++size], "enddate",searchInfo[++size],
        "endtime",searchInfo[++size], "endmarker",searchInfo[++size]);
    return throwExceptionIfNotOkayHttpStatus(response);
  }

  /**
   * this method does a get request that send command line arguments
   * to manipulate the data to pretty <code>PhoneBill</code> to print
   * @param customer
   * customer's name from the command line argument
   * @return
   * <code>Response</code> modified from the server
   * @throws IOException
   * if the code of the <code>Response</code> is not 200
   */
  public Response displayPhoneBill(final String customer) throws IOException{
    Response response = get(this.url,"printall","printall","customer",customer);
    return throwExceptionIfNotOkayHttpStatus(response);
  }

  /**
   * this method simulate post operation
   * @param PhonebillArgsEntries
   * string array that contain all the required command
   * for searching
   * @return
   * <code>Response</code> modified from the server
   * @throws IOException
   * if the url is bad
   */
  @VisibleForTesting
  Response postToMyURL(String... PhonebillArgsEntries) throws IOException {
    return post(this.url, PhonebillArgsEntries);
  }

  /**
   * this does a delete get request on url to remove all <code>PhoneBill</code>s
   * @throws IOException
   * if the code of the <code>Response</code> is not 200
   */
  public void removeAllPhoneBillsEntries() throws IOException {
    Response response = delete(this.url);
    throwExceptionIfNotOkayHttpStatus(response);
  }

  /**
   * this method ensure that codes other then 200 is an error
   * @param response
   * to check the code of the <code>Response</code>
   * @return
   * if the code of the <code>Response</code> is 200
   */
  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      throw new PhoneBillRestException(code, response.getContent().replace("?","\n"));
    }
    return response;
  }

  /**
   * class for PhoneBillsException
   */
  private class PhoneBillRestException extends RuntimeException {

    /**
     * this method display the error with <code>Response</code> code
     * other than 200 and it display the reason on why the code is not 200
     * @param httpStatusCode
     * code status of <code>Response</code>
     * @param message
     * message of <code>Response</code>
     */
    public PhoneBillRestException(int httpStatusCode, String message) {
      super("Got an HTTP Status Code of " + httpStatusCode+"\n\nReason:\n"+message);
    }
  }

}
