package edu.pdx.cs410J.ms24.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.ms24.client.PhoneBill;
import edu.pdx.cs410J.ms24.client.PhoneBillService;
import edu.pdx.cs410J.ms24.client.PhoneCall;
import edu.pdx.cs410J.ms24.client.PrettyPrinter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{
  private final Map<String, PhoneBill> phonebills = new HashMap<>();
  @Override
  public String addPhoneCall(String[] phoneBillInfo, Boolean print) throws Throwable {
    if (phoneBillInfo.length != 9)
      throw new Throwable("Missing Infromation");
    PhoneBill phoneBill = phonebills.get(phoneBillInfo[0]);
    PhoneCall phoneCall;
    try {
      phoneCall = new PhoneCall(phoneBillInfo);
    }catch (Throwable throwable) {
      throw new Throwable(throwable.getMessage());
    }
    try {

      if (phoneBill == null)
        phonebills.put(phoneBillInfo[0], new PhoneBill(phoneBillInfo[0], phoneCall));
      else {
        phoneBill.addPhoneCall(phoneCall);
        phonebills.put(phoneBillInfo[0], phoneBill);
      }
    }catch (Throwable throwable){
      throw new Throwable(throwable.getMessage());
    }
    return "Phone call has been added\n"+(print?phoneCall.toString():"");
  }

  @Override
  public String searchPhoneCalls(String customer, Date start, Date end) throws Throwable{
    PhoneBill phonebill = phonebills.get(customer);
    if (phonebill == null)
      throw new Throwable("Requested customer \""+customer+"\" does not have a registered Phone Bill");
    return phonebill.searchPhoneCalls(start,end);
  }

  @Override
  public String prettyPrint(String customer) throws Throwable {
    PhoneBill phoneBill = phonebills.get(customer);
    PrettyPrinter prettyPrinter = new PrettyPrinter();
    if (phoneBill == null)
      throw new Throwable("Requested customer \""+customer+"\" does not have a registered Phone Bill");
    return prettyPrinter.prettifyPhoneBill(phoneBill);
  }
}
