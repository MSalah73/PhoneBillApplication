package edu.pdx.cs410J.ms24;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Test;


/**
 * Unit tests for the {@link PhoneBill} class.
 */
public class PhoneBillTest {

  public PhoneBillTest() throws ParseException {
  }

  @Test
  public void testInitializationOfCollectionUponObjectCreation (){
    assertThat(emptyAndUnnamedPhoneBill.getPhoneCalls(), is(notNullValue()));
  }
  @Test
  public void addingPhoneCallToEmptyPhoneBillObjectAndCheckIfTheContentIsEqualToObjectInserted(){
    emptyAndUnnamedPhoneBill.addPhoneCall(validPhoneCall);
    assertThat("Size of phonecalls collection",emptyAndUnnamedPhoneBill.getPhoneCalls().size(), is(1));
    assertTrue("ValidPhonecall equals object inserted to phonebill object",
        emptyAndUnnamedPhoneBill.getPhoneCalls().contains(validPhoneCall));
  }
  @Test
  public void addingMultiplePhoneCallToEmptyPhoneBillObjectAndCheckIfTheContentIsEqualToObjectInserted()
      throws ParseException {
    PhoneCall anotherValidPhoneCall = new PhoneCall(new String[]{"777-222-2232","888-888-8888",
        "1/1/2011", "9:12","am","3/2/2011", "10:22","am"});
    emptyAndUnnamedPhoneBill.addPhoneCall(validPhoneCall);
    emptyAndUnnamedPhoneBill.addPhoneCall(anotherValidPhoneCall);
    assertThat("Size of phonecalls collection",emptyAndUnnamedPhoneBill.getPhoneCalls().size(), is(2));
    assertTrue("ValidPhonecall equals object inserted to phonebill object",
        emptyAndUnnamedPhoneBill.getPhoneCalls().contains(validPhoneCall));
    assertTrue("ValidPhonecall equals object inserted to phonebill object",
        emptyAndUnnamedPhoneBill.getPhoneCalls().contains(anotherValidPhoneCall));
  }
  @Test
  public void addingACustomerNameAndCheckingIfItsStoredCorrectly(){
    PhoneBill namedPhoneBill = new PhoneBill("Bad News");
    assertTrue(namedPhoneBill.getCustomer().contentEquals("Bad News"));
  }
  @Test
  public void checkingIfPhoneCallsSortedCorrectlyWithIdenticalPhoneNumbers() throws ParseException {
    PhoneBill namedPhoneBill = new PhoneBill("Bad News", validPhoneCall);
    PhoneCall Phone2 ,Phone3, Phone4, Phone5, Phone6,Phone6p, Phone7;
    namedPhoneBill.addPhoneCall(Phone2 = new PhoneCall(new String[]{"899-999-9999","888-888-8888", "2/22/5555", "11:12","Pm","3/21/5555", "12:22","Pm"}));
    namedPhoneBill.addPhoneCall(Phone3 = new PhoneCall(new String[]{"899-999-9999","888-888-8888", "2/22/4444", "11:12","Pm","3/21/4444", "12:22","Pm"}));
    namedPhoneBill.addPhoneCall(Phone4 = new PhoneCall(new String[]{"899-999-9999","888-888-8888", "2/22/3333", "11:12","Pm","3/21/3333", "12:22","Pm"}));
    namedPhoneBill.addPhoneCall(Phone5 = new PhoneCall(new String[]{"899-999-9999","888-888-8888", "2/22/1111", "11:12","Pm","3/21/1111", "12:22","Pm"}));
    namedPhoneBill.addPhoneCall(Phone6 = new PhoneCall(new String[]{"899-999-9999","888-888-8888", "2/22/6666", "11:12","Pm","3/21/6666", "12:22","Pm"}));
    namedPhoneBill.addPhoneCall(Phone6p = new PhoneCall(new String[]{"899-999-9999","888-888-8888", "2/22/1111", "11:12","Pm","3/21/1111", "12:22","Pm"}));
    namedPhoneBill.addPhoneCall(Phone7 = new PhoneCall(new String[]{"899-999-9999","888-888-8888", "2/22/2222", "11:12","Pm","3/21/2222", "12:22","Pm"}));
    var sortedList = new ArrayList(namedPhoneBill.getPhoneCalls());
    assertThat("This PhoneCall:\n"+Phone5+"\nMust be in index 0",sortedList.indexOf(Phone5) == 0);
    assertThat("This PhoneCall:\n"+Phone6p+"\nMust be in index 1",sortedList.indexOf(Phone6p) == 1);
    assertThat("This PhoneCall:\n"+validPhoneCall+"\nMust be in index 2",sortedList.indexOf(validPhoneCall) == 2);
    assertThat("This PhoneCall:\n"+Phone7+"\nMust be in index 3",sortedList.indexOf(Phone7) == 3);
    assertThat("This PhoneCall:\n"+Phone4+"\nMust be in index 4",sortedList.indexOf(Phone4) == 4);
    assertThat("This PhoneCall:\n"+Phone3+"\nMust be in index 5",sortedList.indexOf(Phone3) == 5);
    assertThat("This PhoneCall:\n"+Phone2+"\nMust be in index 6",sortedList.indexOf(Phone2) == 6);
    assertThat("This PhoneCall:\n"+Phone6+"\nMust be in index 7",sortedList.indexOf(Phone6) == 7);
  }
  @Test
  public void checkingIfPhoneCallsSortedCorrectlyWithIdenticalStartTime() throws ParseException {
    PhoneBill namedPhoneBill = new PhoneBill("Bad News", validPhoneCall);
    PhoneCall Phone2 ,Phone3, Phone4, Phone5, Phone6, Phone7;
    namedPhoneBill.addPhoneCall(Phone2 = new PhoneCall(new String[]{"900-999-9999","888-888-8888", "2/22/2222", "11:12","pm","3/21/3211", "12:22","pm"}));
    namedPhoneBill.addPhoneCall(Phone3 = new PhoneCall(new String[]{"909-999-9999","888-888-8888", "2/22/2222", "11:12","pm","3/21/3211", "12:22","pm"}));
    namedPhoneBill.addPhoneCall(Phone4 = new PhoneCall(new String[]{"907-999-9999","888-888-8888", "2/22/2222", "11:12","pm","3/21/3211", "12:22","pm"}));
    namedPhoneBill.addPhoneCall(Phone5 = new PhoneCall(new String[]{"803-999-9999","888-888-8888", "2/22/2222", "11:12","pm","3/21/3211", "12:22","pm"}));
    namedPhoneBill.addPhoneCall(Phone6 = new PhoneCall(new String[]{"930-999-9999","888-888-8888", "2/22/2222", "11:12","pm","3/21/3211", "12:22","pm"}));
    namedPhoneBill.addPhoneCall(Phone7 = new PhoneCall(new String[]{"900-999-9999","888-888-8888", "2/22/2222", "11:12","pm","3/21/3211", "12:22","pm"}));
    var sortedList = new ArrayList(namedPhoneBill.getPhoneCalls());
    assertThat("This PhoneCall:\n"+Phone5+"\nMust be in index 0",sortedList.indexOf(Phone5) == 0);
    assertThat("This PhoneCall:\n"+validPhoneCall+"\nMust be in index 1",sortedList.indexOf(validPhoneCall) == 1);
    assertThat("This PhoneCall:\n"+Phone2+"\nMust be in index 2",sortedList.indexOf(Phone2) == 2);
    assertThat("This PhoneCall:\n"+Phone7+"\nMust be in index 3",sortedList.indexOf(Phone7) == 3);
    assertThat("This PhoneCall:\n"+Phone4+"\nMust be in index 4",sortedList.indexOf(Phone4) == 4);
    assertThat("This PhoneCall:\n"+Phone3+"\nMust be in index 5",sortedList.indexOf(Phone3) == 5);
    assertThat("This PhoneCall:\n"+Phone6+"\nMust be in index 6",sortedList.indexOf(Phone6) == 6);
  }
  @Test
  public void checkingIfPhoneCallsSortedCorrectly() throws ParseException {
    PhoneBill namedPhoneBill = new PhoneBill("Bad News", validPhoneCall);
    PhoneCall Phone2 ,Phone3, Phone4, Phone5, Phone6, Phone7, Phone7a, Phone7b,Phone7c;
    namedPhoneBill.addPhoneCall(Phone2 = new PhoneCall(new String[]{"374-973-3874","888-888-8888", "11/20/2018", "8:21","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone3 = new PhoneCall(new String[]{"373-484-3982","888-888-8888", "5/22/2018", "7:19","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone4 = new PhoneCall(new String[]{"475-383-2836","888-888-8888", "3/12/2018", "3:30","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone5 = new PhoneCall(new String[]{"384-384-2938","888-888-8888", "2/16/2018", "9:50","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone6 = new PhoneCall(new String[]{"283-487-3948","888-888-8888", "9/27/2018", "1:22","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone7 = new PhoneCall(new String[]{"374-973-3874","888-888-8888", "6/17/2018", "1:12","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone7a = new PhoneCall(new String[]{"373-973-3874","888-888-8888", "6/17/2018", "1:12","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone7b = new PhoneCall(new String[]{"375-973-3874","888-888-8888", "6/17/2018", "1:12","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone7c = new PhoneCall(new String[]{"374-973-3874","888-888-8888", "6/17/2018", "1:12","am","3/21/9999", "12:22","am"}));
    var sortedList = new ArrayList(namedPhoneBill.getPhoneCalls());
    assertThat("This PhoneCall:\n"+Phone5+"\nMust be in index 0",sortedList.indexOf(Phone5) == 0);
    assertThat("This PhoneCall:\n"+Phone4+"\nMust be in index 1",sortedList.indexOf(Phone4) == 1);
    assertThat("This PhoneCall:\n"+Phone3+"\nMust be in index 2",sortedList.indexOf(Phone3) == 2);
    assertThat("This PhoneCall:\n"+Phone7a+"\nMust be in index 3",sortedList.indexOf(Phone7a) == 3);
    assertThat("This PhoneCall:\n"+Phone7+"\nMust be in index 4",sortedList.indexOf(Phone7) == 4);
    assertThat("This PhoneCall:\n"+Phone7c+"\nMust be in index 5",sortedList.indexOf(Phone7c) == 5);
    assertThat("This PhoneCall:\n"+Phone7b+"\nMust be in index 6",sortedList.indexOf(Phone7b) == 6);
    assertThat("This PhoneCall:\n"+Phone6+"\nMust be in index 7",sortedList.indexOf(Phone6) == 7);
    assertThat("This PhoneCall:\n"+Phone2+"\nMust be in index 8",sortedList.indexOf(Phone2) == 8);
    assertThat("This PhoneCall:\n"+validPhoneCall+"\nMust be in index 9",sortedList.indexOf(validPhoneCall) == 9);
  }
  @Test(expected = InvalidParameterException.class)
  public void testPhoneCallssearch() throws ParseException {
    PhoneBill namedPhoneBill = new PhoneBill("Bad News", validPhoneCall);
    PhoneCall Phone2 ,Phone3, Phone4;
    var formator = new SimpleDateFormat("MM/dd/yyyy h:mm a");
    namedPhoneBill.addPhoneCall(Phone2 = new PhoneCall(new String[]{"374-973-3874","888-888-8888", "11/20/2018", "8:21","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone3 = new PhoneCall(new String[]{"373-484-3982","888-888-8888", "5/22/2018", "7:19","am","3/21/9999", "12:22","am"}));
    namedPhoneBill.addPhoneCall(Phone4 = new PhoneCall(new String[]{"475-383-2836","888-888-8888", "3/12/2018", "3:30","am","3/21/9999", "12:22","am"}));
    System.out.println(namedPhoneBill.searchPhoneCalls(new Date("5/22/2019 7:19 am"), new Date("11/22/2018")));
  }
  private PhoneCall validPhoneCall = new PhoneCall(new String[]{"899-999-9999","888-888-8888", "2/22/2222",
      "11:12","pm","3/21/3211", "12:22","pm"});
  private PhoneBill emptyAndUnnamedPhoneBill = new PhoneBill();
}
