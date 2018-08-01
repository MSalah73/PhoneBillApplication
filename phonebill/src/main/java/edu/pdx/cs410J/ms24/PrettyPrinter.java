package edu.pdx.cs410J.ms24;


/**
 * This class prettifies a <code>PhoneBill</code> Object to send to client
 * @author Zack Salah
 */
public class PrettyPrinter {

  /**
   * This creates <code>PrettyPrinter</code> object.
   */
  PrettyPrinter(){
    super();
  }

  /**
   * This method prettifies a <code>PhoneBill</code> content and format it as a <code>String</code>
   * @param phoneBill
   * <code>PhoneBill</code> to prettify
   * @return
   * A prettified <code>PhoneBill</code> as a <code>String</code>
   */
  public final String prettifyPhoneBill(PhoneBill phoneBill){
    var size = phoneBill.getPhoneCalls().size();
    int counter = size;
    String pretty = "Customer: "+phoneBill.getCustomer()+"\nNumber of phone calls: "+size+"\n";
    pretty += "#     Caller Phone Number     Callee Phone Number     Call Started At     Call Ended At     Call Duration\n";
    pretty += "---------------------------------------------------------------------------------------------------------\n";
    for (PhoneCall c : phoneBill.getPhoneCalls()) {
      pretty += String.format("%-8d %-23s %-19s %-19s %-18s %d Minutes\n",size - --counter, c.getCaller(),c.getCallee(),
          c.getStartTimeString(), c.getEndTimeString(), c.callDuration());
    }
    return pretty;
  }
}
