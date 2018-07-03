package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  public PhoneCall(){super();}
  @Override
  public String getCaller() {
    return callerNumber;
  }

  @Override
  public String getCallee() {
    return calleeNumber;
  }

  @Override
  public String getStartTimeString() {
      return startTime;
  }

  @Override
  public String getEndTimeString() {
      return endTime;
  }
  private String callerNumber;
  private String calleeNumber;
  private String startTime;
  private String endTime;
}