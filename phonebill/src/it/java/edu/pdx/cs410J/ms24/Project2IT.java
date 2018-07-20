package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }
  @Test
  public void noName() {
    MainMethodResult result = invokeMain("999-000-0000","222-222-2222","11/11/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Missing command line arguments"));
  }
  @Test
  public void noPhoneNumbers() {
    MainMethodResult result = invokeMain("Zack","11/11/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Missing command line arguments"));
  }
  @Test
  public void onePhoneNumber() {
    MainMethodResult result = invokeMain("Zack","000-000-0000","11/11/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Missing command line arguments"));
  }
  @Test
  public void missingStartDate() {
    MainMethodResult result = invokeMain("Zack","111-111-1111","000-000-0000","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Missing command line arguments"));
  }
  @Test
  public void missingStartTime() {
    MainMethodResult result = invokeMain("Zack","111-111-1111","000-000-0000","11/11/1111","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Missing command line arguments"));
  }
  @Test
  public void missingEndDate() {
    MainMethodResult result = invokeMain("Zack","111-111-1111","000-000-0000","11/11/1111","11:11","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Missing command line arguments"));
  }
  @Test
  public void missingEndTime() {
    MainMethodResult result = invokeMain("Zack","111-111-1111","000-000-0000","11/11/1111","11:11","11/11/1111");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Missing command line arguments"));
  }
  @Test
  public void outOfOrderArrangement1() {
    MainMethodResult result = invokeMain("999-999-999","Zack","000-000-0000","11/11/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Arguments are not ordered correctly."
                + " Please enter in this order -->[Options] Name Caller Callee StartDate StartTime EndData EndTime"));
  }
  @Test
  public void outOfOrderArrangement2() {
    MainMethodResult result = invokeMain("Zack","999-999-9999","000-000-0000","11:11","11/11/1111","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Arguments are not ordered correctly."
                + " Please enter in this order -->[Options] Name Caller Callee StartDate StartTime EndData EndTime"));
  }
  @Test
  public void outOfOrderArrangement3() {
    MainMethodResult result = invokeMain("Zack","999-999-9099","000-000-0000","11/11/2222","11:11","11/11/1111","11/11/");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Arguments are not ordered correctly."
                + " Please enter in this order -->[Options] Name Caller Callee StartDate StartTime EndData EndTime"));
  }
  @Test
  public void outOfOrderArrangement4() {
    MainMethodResult result = invokeMain("Zack","999-999999","000-000-0000","11/11/","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("Arguments are not ordered correctly."
                + " Please enter in this order -->[Options] Name Caller Callee StartDate StartTime EndData EndTime"));
  }
  @Test
  public void PhoneNumberWrongFormatLessThenActual(){
    MainMethodResult result = invokeMain("Zack","999-999-99","000-000-0000","11/11/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("XXX-XXX-XXXX"));
  }
  @Test
  public void PhoneNumberWrongFormatLessThenActual2(){
    MainMethodResult result = invokeMain("Zack","999-9-9229","000-000-0000","11/11/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("XXX-XXX-XXXX"));
  }
  @Test
  public void PhoneNumberWrongFormatMoreThenActual(){
    MainMethodResult result = invokeMain("Zack","999-911-9229","000-000-0000000","11/11/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("XXX-XXX-XXXX"));
  }
  @Test
  public void dateWrongFormatLessThenActual(){
    MainMethodResult result = invokeMain("Zack","999-999-9229","000-000-0000","11/11/111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("MM/DD/YYYY"));
  }
  @Test
  public void dateWrongFormatLessThenActual2(){
    MainMethodResult result = invokeMain("Zack","999-229-9229","000-000-0000","11//1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("MM/DD/YYYY"));
  }
  @Test
  public void dateWrongFormatMoreThenActual(){
    MainMethodResult result = invokeMain("Zack","999-911-9229","000-000-0030","11/1331/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("MM/DD/YYYY"));
  }
  @Test
  public void dateWrongFormatMoreThenActual2(){
    MainMethodResult result = invokeMain("Zack","999-911-9229","000-000-0000","11/11/1133311","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("MM/DD/YYYY"));
  }
  @Test
  public void timeWrongFormatLessThenActual(){
    MainMethodResult result = invokeMain("Zack","999-999-9229","000-000-0000","11/11/1121","0:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("HH:MM"));
  }
  @Test
  public void timeWrongFormatLessThenActual2(){
    MainMethodResult result = invokeMain("Zack","999-229-9229","000-000-0000","11/22/1111","11:1","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("HH:MM"));
  }
  @Test
  public void timeWrongFormatMoreThenActual(){
    MainMethodResult result = invokeMain("Zack","999-911-9229","000-000-0030","11/11/1111","121:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("HH:MM"));
  }
  @Test
  public void timeWrongFormatMoreThenActual2(){
    MainMethodResult result = invokeMain("Zack","999-911-9229","000-000-0000","11/11/1111","11:121","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("HH:MM"));
  }
  @Test
  public void readmeOption() {
    MainMethodResult result = invokeMain("-README","-print","Zack","999-999-9990","000-000-0000","11:11","11/11/1111","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),
        containsString("Zack Salah"));
  }
  @Test
  public void printOption() {
    MainMethodResult result = invokeMain("-print","Zack","999-999-9999","000-000-0000","11/11/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),
        containsString("Zack's phone bill with "));
  }
  @Test
  public void textFileOption() {
    MainMethodResult result = invokeMain("-textFile","Test","-print","Zack","999-999-9999","000-000-0000","11/11/1111","11:11","11/11/1111","11:11");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),
        containsString("Zack's phone bill with "));
  }
}
