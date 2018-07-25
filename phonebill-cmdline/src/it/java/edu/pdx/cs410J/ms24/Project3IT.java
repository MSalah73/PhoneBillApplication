  package edu.pdx.cs410J.ms24;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

  /**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3IT extends InvokeMainTestCase {

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
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    System.out.println(result.getTextWrittenToStandardError());
  }

}
