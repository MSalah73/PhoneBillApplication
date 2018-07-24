package edu.pdx.cs410J.ms24;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class SubmissionTestIT extends InvokeMainTestCase {
    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void print() {
    MainMethodResult result = invokeMain("-print", "Test7","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    assertThat(result.getExitCode(), equalTo(0));
    System.out.println(result.getTextWrittenToStandardOut());
  }
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void testReadMe() {
    MainMethodResult result = invokeMain("-README");
    assertThat(result.getExitCode(), equalTo(0));
    System.out.println(result.getTextWrittenToStandardOut());
  }
  @Test
  public void callerContainNonNumber() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24-x.txt","Test3","ABC-123-4567", "123-456-7890",
        "03/03/2018", "1:00","pm", "03/03/2018", "4:00", "pm");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void startTimeMalformatted() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24-x.txt","Test4","123-456-7890", "234-567-8901",
        "03/03/2018", "12:XX","am", "03/03/2018", "4:00", "pm");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void endTimeMalformatted() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24-x.txt","Test5","123-456-7890", "234-567-8901",
        "03/03/2018", "1:00","pm", "03/03/20/8", "4:00","pm");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void unknownCommandLineArgument() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24-x.txt","Test6","123-456-7890", "234-567-8901",
        "03/03/2018", "1:00","pm", "03/03/2018", "4:00","pm","John");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void startANewPhoneBillFile() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24.txt","-print", "Test7","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    assertThat(result.getExitCode(), equalTo(0));
    System.out.println(result.getTextWrittenToStandardOut());
  }
  @Test
  public void addToExistFile() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24.txt","-print", "Test7","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    assertThat(result.getExitCode(), equalTo(0));
    System.out.println(result.getTextWrittenToStandardOut());
  }
  @Test
  public void addToExistFileWithDiffName() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24.txt","-print", "Shit","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void bogusFile() {
    MainMethodResult result = invokeMain("-textFile","ms24/bogus.txt","-print", "shit","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void weongYeatFile() {
    MainMethodResult result = invokeMain("-textFile","ms24/malformatedYear.txt","-print", "shit","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void prettyprint() {
    MainMethodResult result = invokeMain("-pretty","-","-textFile","ms24/onePhoneBill.txt","-print", "Test7","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    System.out.println(result.getTextWrittenToStandardError());
    assertThat(result.getExitCode(), equalTo(0));
    System.out.println(result.getTextWrittenToStandardOut());
  }
  @Test
  public void prettyprint3() {
    MainMethodResult result = invokeMain("-textFile","ms24/onePhoneBill.txt","-print","-pretty","-", "Test7","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    assertThat(result.getExitCode(), equalTo(0));
    System.out.println(result.getTextWrittenToStandardOut());
  }
  @Test
  public void prettyprintFile() {
    MainMethodResult result = invokeMain("-textFile","ms24/onePhoneBill.txt","-print","-pretty","pretty", "Test7","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    assertThat(result.getExitCode(), equalTo(0));
    System.out.println(result.getTextWrittenToStandardOut());
  }
  @Test
  public void prettyprintFileFromTextFile() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24.txt","-print","-pretty","ms24/ms24.txt", "Test7","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00","am", "01/17/2018", "5:00", "pm");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
}
