package edu.pdx.cs410J.ms24.server;

import org.junit.FixMethodOrder;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
@FixMethodOrder()
public class PhoneBillServiceImplTest {
  PhoneBillServiceImpl service = new PhoneBillServiceImpl();
  public PhoneBillServiceImplTest() throws Throwable {
   String[] agrs = new String[]{"Zack","111-111-1111","222-222-2222","11/11/1111","11:11","am","11/11/1111","11:11","pm"};
    String s = service.addPhoneCall(agrs,false);
  }
  @Test
  public void addingPhoneBill() throws Throwable {
    String[] agrs = new String[]{"Zack","111-111-1111","222-222-2222","11/11/1111","11:11","am","11/11/1111","11:11","pm"};
    String s = service.addPhoneCall(agrs,false);
    assertThat("it should add the phone call",s.equals("Phone call has been added\n"));
  }
  @Test(expected = Throwable.class)
  public void addWithUnformatedDate() throws Throwable {
    String[] agrs = new String[]{"Zack","111-111-1111","222-222-2222","11/11/1111","11:11","am","11/11/1111","11:11","am"};
    String s = service.addPhoneCall(agrs,false);
    assertThat("it should add the phone call",s.equals("Phone call has been added\n"));
  }
  @Test
  public void searchCalls() throws Throwable {
    String before = "11/11/1111 11:11 am";
    String after = "11/11/1111 11:11 pm";
    String s = service.searchPhoneCalls("Zack",before,after);
    assertThat("It should find the redefined phone call",s.contains("Customer: Zack"));
  }
  @Test(expected = Throwable.class)
  public void invaildSearchCalls() throws Throwable {
    String[] agrs = new String[]{"Zack","111-111-1111","222-222-2222","11/11/1111","11:11","am","11/11/1111","11:11","am"};
    String s = service.addPhoneCall(agrs,false);
    assertThat("it should add the phone call",s.equals("Phone call has been added\n"));
  }
  @Test(expected = Throwable.class)
  public void PrettyPrintInvalidPhoneBill() throws Throwable {
    String s = service.prettyPrint("lol");
    assertThat("User \"lol\" should not be registered",s.contains("Customer: lol"));
  }
  @Test
  public void prettyPrintValidPhoneBill() throws Throwable {
    String s = service.prettyPrint("Zack");
    assertThat("User \"Zack\" should be registered",s.contains("Customer: Zack"));
  }

}
