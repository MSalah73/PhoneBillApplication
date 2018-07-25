package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.ParserException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
public class PrettyPrintTest {
  @Test
  public void testPrintAndWriteToFileAtSameTime() throws IOException {
    PhoneBill namedPhoneBill = new PhoneBill("Bad News");
    PhoneCall Phone2 ,Phone3, Phone4, Phone5, Phone6, Phone7, Phone7a, Phone7b,Phone7c;
    namedPhoneBill.addPhoneCall(Phone2 = new PhoneCall(new String[]{"374-973-3874","888-888-8888", "11/20/2018", "3:21","pm",
        "11/22/2018", "2:22","am"}));
    namedPhoneBill.addPhoneCall(Phone3 = new PhoneCall(new String[]{"373-484-3982","888-888-8888", "5/22/2018", "9:19","pm",
        "3/21/2019", "2:22","am"}));
    namedPhoneBill.addPhoneCall(Phone4 = new PhoneCall(new String[]{"475-383-2836","888-888-8888", "3/12/2018", "12:30","pm",
        "3/21/2020", "2:22","pm"}));
    namedPhoneBill.addPhoneCall(Phone5 = new PhoneCall(new String[]{"384-384-2938","888-888-8888", "2/16/2018", "11:50","pm",
        "3/21/2020", "2:22","am"}));
    namedPhoneBill.addPhoneCall(Phone6 = new PhoneCall(new String[]{"283-487-3948","888-888-8888", "9/27/2018", "3:22","pm",
        "3/21/2020", "2:22","am"}));
    PrettyPrinter prettyPrinter = new PrettyPrinter( "a");
    prettyPrinter.printOnStandardIO(namedPhoneBill);
    prettyPrinter.dump(namedPhoneBill);
  }
}
