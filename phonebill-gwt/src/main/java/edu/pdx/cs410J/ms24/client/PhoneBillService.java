package edu.pdx.cs410J.ms24.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.Date;

/**
 * A GWT remote service that returns a string message
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

  /**
   * Return a String message about the added Phone call
   */
  String addPhoneCall(String PhoneBillInfo[], Boolean print) throws Throwable;

  /**
   * Return a String that contain the pretty printed phone calls
   */
  String searchPhoneCalls(String customer,Date start, Date end) throws Throwable;

  /**
   * Return a String that contain the pretty printed phone bill
   */
  String prettyPrint(String customer) throws Throwable;

}
