package edu.pdx.cs410J.ms24.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.Date;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

  /**
   * Returns the a dummy Phone Bill
   */
  String addPhoneCall(String PhoneBillInfo[], Boolean print) throws Throwable;

  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  String searchPhoneCalls(String customer,Date start, Date end) throws Throwable;

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  String prettyPrint(String customer) throws Throwable;

}
