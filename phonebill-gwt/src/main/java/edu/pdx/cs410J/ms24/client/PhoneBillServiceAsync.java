package edu.pdx.cs410J.ms24.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.Date;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {

  /**
   * Returns the a dummy Phone Bill
   */
  void addPhoneCall(String PhoneBillInfo[], Boolean print, AsyncCallback<String> async);

  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  void searchPhoneCalls(String customer, Date start, Date end, AsyncCallback<String> async);

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void prettyPrint(String customer, AsyncCallback<String> async);
}
