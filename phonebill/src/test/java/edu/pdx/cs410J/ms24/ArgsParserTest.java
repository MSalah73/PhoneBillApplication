package edu.pdx.cs410J.ms24;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link ArgsParser} class.
 */
public class ArgsParserTest {
    /*
     * parserPhoneNumber
     * Test Start
     */
    @Test
    public void phoneNumberWithHyphens(){
        argpars.parserPhoneNumber("123-456-7890");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberMissingADigit() {
        argpars.parserPhoneNumber("123-456-789");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberAddedDigit() {
        argpars.parserPhoneNumber("123-456-78910");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberMissingHyphen() {
        argpars.parserPhoneNumber("1234567891");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberReplaceSpaceWithHyphen() {
        argpars.parserPhoneNumber("123 456 7891");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberDomesticFormat() {
        argpars.parserPhoneNumber("(123) 456-7891");
    }
     /*
     * parserPhoneNumber
     * Test End
     *
     * parserTime
     * Test Start
     */
    @Test
    public void timeInTwentyFourHourFormat(){
        argpars.parserTime("23:59");
    }
    @Test
    public void timeInTwentyFourHourFormatShortened(){
        argpars.parserTime("2:59");
    }
    @Test(expected = IllegalArgumentException.class)
    public void timeIn_AM_PM_Format() {
        argpars.parserTime("11:59pm");
    }
    @Test(expected = IllegalArgumentException.class)
    public void timeOutOfRange() {
        argpars.parserTime("60:60");
    }
    @Test(expected = IllegalArgumentException.class)
    public void timeWithOneZeroInTheHourSection() {
        argpars.parserTime("0:00");
    }
    /*
     * parserTime
     * Test End
     *
     * parserDate
     * Test Start
     */
    @Test
    public void dateWithSlashes(){
        argpars.parserDate("06/06/2000");
    }
    @Test
    public void dateWithSlashesShortened(){
        argpars.parserDate("6/6/2000");
    }
    @Test(expected = IllegalArgumentException.class)
    public void dataWithHyphens(){
        argpars.parserDate("6-6-2000");
    }
    @Test(expected = IllegalArgumentException.class)
    public void februayOutOfRange(){
        argpars.parserDate("2/29/2000");
    }
    @Test(expected = IllegalArgumentException.class)
    public void monthsWithThirtyDaysOutOfRange(){
        argpars.parserDate("4/31/2000");
    }
    @Test(expected = IllegalArgumentException.class)
    public void dateOutOfRange(){
        argpars.parserDate("99/99/2000");
    }
     /*
     * parserDate
     * Test End
     */
    private ArgsParser argpars = new ArgsParser();
}

