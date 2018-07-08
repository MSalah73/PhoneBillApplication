package edu.pdx.cs410J.ms24;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link ArgumetsValidator} class.
 */
public class ArgumentsValidatorTest {
    /*
     * validatePhoneNumber
     * Test Start
     */
    @Test
    public void phoneNumberWithHyphens(){
        validator.validatePhoneNumber("123-456-7890");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberMissingADigit() {
        validator.validatePhoneNumber("123-456-789");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberAddedDigit() {
        validator.validatePhoneNumber("123-456-78910");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberMissingHyphen() {
        validator.validatePhoneNumber("1234567891");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberReplaceSpaceWithHyphen() {
        validator.validatePhoneNumber("123 456 7891");
    }
    @Test(expected = IllegalArgumentException.class)
    public void phoneNumberDomesticFormat() {
        validator.validatePhoneNumber("(123) 456-7891");
    }
     /*
     * validatePhoneNumber
     * Test End
     *
     * validateTime
     * Test Start
     */
    @Test
    public void timeInTwentyFourHourFormat(){
        validator.validateTime("23:59");
    }
    @Test
    public void timeInTwentyFourHourFormatShortened(){
        validator.validateTime("2:59");
    }
    @Test(expected = IllegalArgumentException.class)
    public void timeIn_AM_PM_Format() {
        validator.validateTime("11:59pm");
    }
    @Test(expected = IllegalArgumentException.class)
    public void timeOutOfRange() {
        validator.validateTime("60:60");
    }
    @Test(expected = IllegalArgumentException.class)
    public void timeWithOneZeroInTheHourSection() {
        validator.validateTime("0:00");
    }
    /*
     * validateTime
     * Test End
     *
     * validateDate
     * Test Start
     */
    @Test
    public void dateWithSlashes(){
        validator.validateDate("06/06/2000");
    }
    @Test
    public void dateWithSlashesShortened(){
        validator.validateDate("6/6/2000");
    }
    @Test(expected = IllegalArgumentException.class)
    public void dataWithHyphens(){
        validator.validateDate("6-6-2000");
    }
    @Test(expected = IllegalArgumentException.class)
    public void februayOutOfRange(){
        validator.validateDate("2/29/2000");
    }
    @Test(expected = IllegalArgumentException.class)
    public void monthsWithThirtyDaysOutOfRange(){
        validator.validateDate("4/31/2000");
    }
    @Test(expected = IllegalArgumentException.class)
    public void dateOutOfRange(){
        validator.validateDate("99/99/2000");
    }
    /*
     * validateDate
     * Test End
     *
     * validateOption
     * Test Start
     */
    @Test
    public void parserReadmeOption(){
        validator.validateOption("-README");
    }
    @Test
    public void parserPrintOption(){
        validator.validateOption("-print");
    }
    @Test(expected = IllegalArgumentException.class)
    public void optionWithoutHyphen(){
        validator.validateOption("print");
    }
    @Test(expected = IllegalArgumentException.class)
    public void nonExistentOption(){
        validator.validateOption("-nonExistentOption");
    }
    /*
     * validateOption
     * Test End
     *
     * checkOrder
     * Test Start
     */
    @Test
    public void checkOrderOfArgs(){
        assertThat("Args from command line not ordered correctly",
                validator.validateOrder(new String[]{"99-","99","////", "1:","////", "1:"}));
    }
    @Test
    public void testListoOfInvalidOrderOfArgs(){
        assertThat("Test 1 failed",is(not(validator.validateOrder(new String[]{"///","99","99", "1:","////", "1:"}))));
        assertThat("Test 2 failed",is(not(validator.validateOrder(new String[]{"99-","1:","////", "99","////", "1:"}))));
        assertThat("Test 3 failed",is(not(validator.validateOrder(new String[]{"99-","99","1:1", "///","////", "1:"}))));
        assertThat("Test 4 failed",is(not(validator.validateOrder(new String[]{"99-","1:","////", "99","////", "1:"}))));
        assertThat("Test 5 failed",is(not(validator.validateOrder(new String[]{"99-","99","////", "1:","1:", "////"}))));
    }
     /*
     * checkOrder
     * Test End
     *
     * argsValidity
     * Test Start
     */
    @Test
    public void testIfArgsValidityValidateCorrectly(){
        validator.chackArgumentsValidity(new String[]{"-print","-README","NONE","000-000-0000","999-999-9999",
                "1/1/2222", "1:11","1/1/2222", "1:11"});
    }
    @Test(expected = IllegalArgumentException.class)
    public void argsWithPhoneNumbersFormattedWrongly(){
        validator.chackArgumentsValidity(new String[]{"-print","-README","NONE","00-000-0000","999-999-9999",
                "1/1/2222", "1:11","1/1/2222", "1:11"});
        validator.chackArgumentsValidity(new String[]{"-print","-README","NONE","000-000-0000","999-99-9999",
                "1/1/2222", "1:11","1/1/2222", "1:11"});
    }
    @Test(expected = IllegalArgumentException.class)
    public void argsWithDateAndTimeFormattedWrongly(){
        validator.chackArgumentsValidity(new String[]{"-print","-README","NONE","00-000-0000","999-999-9999",
                "1/1/222", "1:11","1/1/2222", "1:11"});
        validator.chackArgumentsValidity(new String[]{"-print","-README","NONE","000-000-0000","999-99-9999",
                "1/1/2222", "24:11","1/1/2222", "1:11"});
    }
    /*
     * argsValidity
     * Test End
     *
     */
    private ArgumetsValidator validator = new ArgumetsValidator();
}

