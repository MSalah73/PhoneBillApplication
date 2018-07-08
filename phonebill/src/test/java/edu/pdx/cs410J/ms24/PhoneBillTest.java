package edu.pdx.cs410J.ms24;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


/**
 * Unit tests for the {@link PhoneBill} class.
 */
public class PhoneBillTest {
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
  public void addingMultiplePhoneCallToEmptyPhoneBillObjectAndCheckIfTheContentIsEqualToObjectInserted(){
    PhoneCall anotherValidPhoneCall = new PhoneCall(new String[]{"777-222-222","888-888-8888",
        "2/22/2222", "11:12","3/21/1211", "22:22"});
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
  private PhoneCall validPhoneCall = new PhoneCall(new String[]{"999-999-9999","888-888-8888",
      "2/22/2222", "11:12","3/21/1211", "22:22"});
  private PhoneBill emptyAndUnnamedPhoneBill = new PhoneBill();
}
