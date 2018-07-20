package edu.pdx.cs410J.ms24;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class SubmissionTest extends InvokeMainTestCase {
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
        "03/03/2018", "12:00", "03/03/2018", "16:00");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void startTimeMalformatted() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24-x.txt","Test4","123-456-7890", "234-567-8901",
        "03/03/2018", "12:XX", "03/03/2018", "16:00");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void endTimeMalformatted() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24-x.txt","Test5","123-456-7890", "234-567-8901",
        "03/03/2018", "12:00", "03/03/20/8", "16:00");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void unknownCommandLineArgument() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24-x.txt","Test6","123-456-7890", "234-567-8901",
        "03/03/2018", "12:00", "03/03/2018", "16:00","John");
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }
  @Test
  public void startANewPhoneBillFile() {
    MainMethodResult result = invokeMain("-textFile","ms24/ms24.txt","-print", "Test7","123-456-7890", "234-567-8901",
        "01/07/2018", "07:00", "01/17/2018", "17:00");
    assertThat(result.getExitCode(), equalTo(0));
    System.out.println(result.getTextWrittenToStandardOut());
  }
}
