package edu.pdx.cs410J.ms24.client;

import edu.pdx.cs410J.AbstractPhoneCall;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This class is a representation of a <code>PhoneCall</code>
 *
 * @author Zack Salah
 */

public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>{
  /**
   * Creates an empty <code>PhoneCall</Code> Object.
   */
  public PhoneCall(){
    super();
  }

  /**
   * Creates a new <code>PhoneCall</code> Object with Information to add.
   * @param phoneCallInformation
   * Array of <code>String</code> which contains caller Phone number, callee phone number,
   * start and end date and time of phone call.
   */

  public PhoneCall(final String[] phoneCallInformation) throws Throwable {
    super();
    setPhoneCallData(phoneCallInformation);
  }

  /**
   * Gets the caller phone number.
   * @return caller phone number as a <code>String</code>
   */
  @Override
  public final String getCaller() {
    return callerNumber;
  }

  /**
   * Gets the callee phone number.
   * @return Callee phone number as a <code>String</code>
   */
  @Override
  public final String getCallee() {
    return calleeNumber;
  }

  /**
   * Gets the startTime of the phone call
   * @return
   * return the start time as a <code>Date</code> data type.
   */
  @Override
  public Date getStartTime() {
    return startTime;
  }
    /**
   * Gets the endTime of the phone call
   * @return
   * return the end time as a <code>Date</code> data type.
   */
  @Override
  public Date getEndTime() {
    return endTime;
  }
  /**
   * Gets the start date and time of call.
   * @return Start date and time as a <code>String</code>
   */
  @Override
  public final String getStartTimeString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT).format(startTime);
  }
  /**
   * Gets the end date and time of call.
   * @return End date and time as a <code>String</code>
   */
  @Override
  public final String getEndTimeString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT).format(endTime);
  }

  /**
   * This help caluacte the mnumber of minutes between start time and end time of
   * a phone call.
   * @return
   * The number of minutes between the start time and end time.
   */
  public long callDuration(){
    return TimeUnit.MINUTES.convert(Math.abs(endTime.getTime() - startTime.getTime()),TimeUnit.MILLISECONDS);
  }
  /**
   * Override the compareTo method from the <code>Comparable</code> interface to preform the right
   * Comparison. This help with sorting the  <code>PhoneCall</code> object in <code>PhoneBill</code>
   * @param o
   * Object of type <code>PhoneCall</code>
   * @return
   * return 0 on equals, 1 on bigger, and -1 on smaller
   */
  @Override
  public int compareTo(PhoneCall o) {
    if (!this.startTime.equals(o.startTime))
      return this.startTime.after(o.startTime)? 1 : -1;
    return this.callerNumber.compareTo(o.callerNumber);
  }
  /**
   * This function gets all the information from the array of <code>String</code>s
   * to correctly store it in object's variables.
   * @param phoneCallInformation
   * Is an array of <code>String</code>s containing information about the phone call
   * caller and callee phone numbers and start and end date and time of phone call.
   */
  private void setPhoneCallData(final String[] phoneCallInformation) throws Throwable {
    var length = phoneCallInformation.length;
    validateDateAndTime(phoneCallInformation);
    calleeNumber = phoneCallInformation[length-7];
    callerNumber = phoneCallInformation[length-8];
  }

  /**
   * This function validate the date and time from the command line arguments
   * and parser it than store them into the private members.
   * @param phoneCallInformation
    * @param phoneCallInformation
   * Array of <code>String</code> which contains caller Phone number, callee phone number,
   * start and end date and time of phone call. This array is passed from the command line
   * argument
   * @throws Exception
   * If the date in odd format and the <code>SimpleDateFormat</code> failed.
   */
  private void validateDateAndTime(String[] phoneCallInformation) throws Throwable {
    var len = phoneCallInformation.length;
    var endTime = phoneCallInformation[len-3]+" "
        +phoneCallInformation[len-2]+" "
        +phoneCallInformation[len-1];
    var startTime = phoneCallInformation[len-6]+" "
        +phoneCallInformation[len-5]+" "
        +phoneCallInformation[len-4];

    var timeAndDateValidator = new SimpleDateFormat("MM/dd/yyyy h:mm a");
    timeAndDateValidator.setLenient(false);
    try {
      this.startTime = timeAndDateValidator.parse(startTime);
      this.endTime = timeAndDateValidator.parse(endTime);
    }catch (Exception ex){
      throw new Throwable("Wrong inputs");
    }

    if (!this.startTime.before(this.endTime))
      throw new Throwable("Start date and time can not be equals or after the end date and "
          + "time of the phone call");
  }
  /**
   * The caller phone number.
   */
  private String callerNumber;
  /**
   * The callee phone number.
   */
  private String calleeNumber;
  /**
   * The start date and time for the phone call.
   */
  private Date startTime;
  /**
   * The end date and time for the phone call.
   */
  private Date endTime;

}