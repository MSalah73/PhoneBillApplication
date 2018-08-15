package edu.pdx.cs410J.ms24.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.Date;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {

  /**
   * Returns the a string message about the added phone call
   */
  void addPhoneCall(String PhoneBillInfo[], Boolean print, AsyncCallback<String> async);

  /**
   * Return a String that contain the pretty printed phone calls
   */
  void searchPhoneCalls(String customer, Date start, Date end, AsyncCallback<String> async);

  /**
   * Return a String that contain the pretty printed phone bill
   */
  void prettyPrint(String customer, AsyncCallback<String> async);
}
