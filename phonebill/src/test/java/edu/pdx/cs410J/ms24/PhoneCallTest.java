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
        "2/28/2222", "10:11", "3/22/2222", "12:12"});
    assertThat(call.getStartTimeString(), is(equalTo("2/28/2222 10:11")));
  }
  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall(new String[]{"999-999-9999", "111-111-1111",
        "2/28/2222", "10:11", "3/22/2222", "12:12"});
    assertThat(call.getCallee(), containsString("111-111-1111"));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall();
    PhoneCall call2 = new PhoneCall(new String[]{"999-999-9999", "111-111-1111",
        "", "", "3/22/2222", "12:12"});
    assertThat(call.getStartTime(), is(nullValue()));
    assertThat(call2.getStartTime(),is(nullValue()));
  }
  @Test
  public void storePassedDataCorrectly(){
    PhoneCall call = new PhoneCall(new String[]{"999-999-9999", "111-111-1111",
        "2/28/2222", "10:11", "3/22/2222", "12:12"});
    assertThat(call.getCaller(), containsString("999-999-9999"));
    assertThat(call.getCallee(), containsString("111-111-1111"));
    assertThat(call.getStartTimeString(), is(equalTo("2/28/2222 10:11")));
    assertThat(call.getEndTimeString(), is(equalTo("3/22/2222 12:12")));
  }
}
