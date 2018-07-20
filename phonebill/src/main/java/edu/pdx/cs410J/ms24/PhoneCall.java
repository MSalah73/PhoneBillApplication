package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.AbstractPhoneCall;
/**
 * This class is a representation of a <code>PhoneCall</code>
 *
 * @author Zack Salah
 */

public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>{
  /**
   * Creates an empty <code>PhoneCall</Code> Object.
   */
  PhoneCall(){
    super();
  }

  /**
   * Creates a new <code>PhoneCall</code> Object with Information to add.
   * @param phoneCallInformation
   * Array of <code>String</code> which contains caller Phone number, callee phone number,
   * start and end date and time of phone call.
   */

  PhoneCall(final String[] phoneCallInformation){
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
   * Gets the start date and time of call.
   * @return Start date and time as a <code>String</code>
   */
  @Override
  public final String getStartTimeString() {
    return startTime;
  }
  /**
   * Gets the end date and time of call.
   * @return End date and time as a <code>String</code>
   */
  @Override
  public final String getEndTimeString() {
    return endTime;
  }

  /**
   * This function gets all the information from the array of <code>String</code>s
   * to correctly store it in object's variables.
   * @param phoneCallInformation
   * Is an array of <code>String</code>s containing information about the phone call
   * caller and callee phone numbers and start and end date and time of phone call.
   */
  private void setPhoneCallData(final String[] phoneCallInformation) {
    var length = phoneCallInformation.length;
    endTime = phoneCallInformation[--length-1] + " " + phoneCallInformation[--length+1];
    startTime = phoneCallInformation[--length-1] + " " + phoneCallInformation[--length+1];
    calleeNumber = phoneCallInformation[--length];
    callerNumber = phoneCallInformation[--length];
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
  private String startTime;
  /**
   * The end date and time for the phone call.
   */
  private String endTime;

  @Override
  public int compareTo(PhoneCall o) {
    return 0;
  }
}