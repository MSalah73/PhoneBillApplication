package edu.pdx.cs410J.ms24;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {

  @Test
  public void getStartTimeStringCorrectFormat() {
    PhoneCall call = new PhoneCall(new String[]{"999-999-9999", "111-111-1111",
        "2/28/2222", "10:11","AM", "3/22/2222", "12:12","Pm"});
    assertThat(call.getStartTimeString(), is(equalTo("2/28/22, 10:11 AM")));
  }
  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall(new String[]{"999-999-9999", "111-111-1111",
        "2/28/2222", "10:11","PM", "3/22/2222", "12:12","pm"});
    assertThat(call.getCallee(), containsString("111-111-1111"));
  }
  @Test
  public void storePassedDataCorrectly(){
    PhoneCall call = new PhoneCall(new String[]{"999-999-9999", "111-111-1111",
        "2/28/2222", "10:11", "AM", "3/22/2222", "12:12", "PM"});
    assertThat(call.getCaller(), containsString("999-999-9999"));
    assertThat(call.getCallee(), containsString("111-111-1111"));
    assertThat(call.getStartTimeString(), is(equalTo("2/28/22, 10:11 AM")));
    assertThat(call.getEndTimeString(), is(equalTo("3/22/22, 12:12 PM")));
  }
  @Test
  public void toComparePhoneCalls(){
    PhoneCall Time = new PhoneCall(new String[]{"999-999-9999", "111-111-1111",
        "2/28/2222", "10:11","AM", "3/22/2222", "12:12","PM"});
    PhoneCall TimeLarger = new PhoneCall(new String[]{"999-999-9999", "111-111-1111",
        "2/28/2222", "10:20","Am", "3/22/2222", "12:20","Pm"});
    PhoneCall TimeSmaller = new PhoneCall(new String[]{"999-999-9999", "111-111-1111",
        "2/28/2222", "10:00","am", "3/22/2222", "12:00","pm"});
    assertThat("Identical PhoneCalls with equal start time must be 0",Time.compareTo(Time) == 0);
    assertThat("Identical PhoneCalls with larger start time must be larger than 0",TimeLarger.compareTo(Time) > 0 );
    assertThat("Identical PhoneCalls with Smaller start time must be smaller than 0",TimeSmaller.compareTo(Time) < 0);
  }
  @Test
  public void toComparePhoneCallsWithEqualStartTimeButDifferent(){
    PhoneCall Time = new PhoneCall(new String[]{"999-999-5000", "111-111-1111",
        "2/28/2222", "10:11","Am", "3/22/2222", "12:12","pm"});
    PhoneCall TimeLarger = new PhoneCall(new String[]{"999-999-9990", "111-111-1111",
        "2/28/2222", "10:11","am", "3/22/2222", "12:12","pm"});
    PhoneCall TimeSmaller = new PhoneCall(new String[]{"999-999-0000", "111-111-1111",
        "2/28/2222", "10:11","am", "3/22/2222", "12:12","pm"});
    assertThat("Identical PhoneCalls with equal start time must be 0",Time.compareTo(Time) == 0);
    assertThat("Identical PhoneCalls with larger start time must be larger than 0",TimeLarger.compareTo(Time) > 0);
    assertThat("Identical PhoneCalls with Smaller start time must be smaller than 0",TimeSmaller.compareTo(Time) < 0);
  }
}
