package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.ParserException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextDumberTest {
  @Test(expected = InvalidParameterException.class)
  public void createObjectOfTextDumberWithNullAndEmptyString() throws IOException {
    TextDumper testNull = new TextDumper(null);
    TextDumper testEmpty = new TextDumper("");
  }
  @Test(expected = InvalidParameterException.class)
  public void createObjectOfTextDumberWithFileWithOtherExtension() throws IOException {
    TextDumper test1 = new TextDumper("Test.txttt");
    TextDumper test2 = new TextDumper("Test.java");
  }
  @Test
  public void createObjectOfTextDumberWithFileWithTxtExtension() throws IOException {
    TextDumper test = new TextDumper("Test.txt");
  }
  @Test
  public void createObjectOfTextDumberWithFileWithOutExtension() throws IOException {
    TextDumper test = new TextDumper("Test");
  }
  @Test(expected = InvalidParameterException.class)
  public void writeToFileWithNullPassedInAsParameter() throws IOException {
    TextDumper textDumper = new TextDumper("Test");
    textDumper.dump(null);
  }
  @Test(expected = InvalidParameterException.class)
  public void writeToFileWithAnEmptyPhoneBillObjectPassedInAsParameter() throws IOException {
    TextDumper textDumper = new TextDumper("Test");
    textDumper.dump(new PhoneBill());
  }
  @Test
  public void writeToFileWithPhoneBillObjectThatOnlyContainsCustomerNamePassedInsParameter() throws IOException {
    TextDumper textDumper = new TextDumper("Test");
    textDumper.dump(new PhoneBill("Zack"));

  }
  @Test(expected = InvalidParameterException.class)
  public void writeToFileWithPhoneBillObjectThatOnlyContainsPhoneCallsNamePassedInsParameter() throws IOException {
    TextDumper textDumper = new TextDumper("Test");
    PhoneBill phoneBill = new PhoneBill ();
    phoneBill.addPhoneCall(new PhoneCall(new String[]{"111-111-1111","112-222-2222"
        , "11/11/1111", "11:11","11/22/1111", "22:22"}));
    textDumper.dump(phoneBill);
  }
  @Test(expected = InvalidParameterException.class)
  public void checkIfTextDumberWriteToFileWithDiffrentName() throws IOException {
    TextDumper textDumper = new TextDumper("Test");
    PhoneBill phoneBill = new PhoneBill ("Marshell");
    phoneBill.addPhoneCall(new PhoneCall(new String[]{"111-111-1111","112-222-2222"
        , "11/11/1111", "11:11","11/22/1111", "22:22"}));
    textDumper.dump(phoneBill);
  }
  @Test
  public void writeToFileWithACompletePhoneBillObjectPassedInsParameter() throws IOException {
    TextDumper textDumper = new TextDumper("Test");
    PhoneBill phoneBill = new PhoneBill ("Zack");
    phoneBill.addPhoneCall(new PhoneCall(new String[]{"111-111-1111","112-222-2222"
        , "11/11/1111", "11:11","11/22/1111", "22:22"}));
    textDumper.dump(phoneBill);
  }
  @Test
  public void checkIfDumpFunctionSuccessfullyWriteToFile() throws IOException {
    File file = new File("CustomerPhoneBillFiles/Test.txt");
    var fileLength = file.length();
    TextDumper textDumper = new TextDumper("Test");
    PhoneBill phoneBill = new PhoneBill ("Zack");
    phoneBill.addPhoneCall(new PhoneCall(new String[]{"111-111-1111","112-222-2222"
        , "11/11/1111", "11:11","11/22/1111", "22:22"}));
    textDumper.dump(phoneBill);
    assertThat("File.length must be strictly larger than file.length after dump function call",
        fileLength < file.length());
  }
  @Test(expected = InvalidParameterException.class)
  public void EmptyExistingFile() throws IOException {
    TextDumper textDumper = new TextDumper("empty");
    PhoneBill phoneBill = new PhoneBill ("Zack");
    phoneBill.addPhoneCall(new PhoneCall(new String[]{"111-111-1111","112-222-2222"
        , "11/11/1111", "11:11","11/22/1111", "22:22"}));
    textDumper.dump(phoneBill);
  }
  @Test
  public void NonExistingFile() throws IOException {
    TextDumper textDumper = new TextDumper("sjsj");
    PhoneBill phoneBill = new PhoneBill ("Zack");
    phoneBill.addPhoneCall(new PhoneCall(new String[]{"111-111-1111","112-222-2222"
        , "11/11/1111", "11:11","11/22/1111", "22:22"}));
    textDumper.dump(phoneBill);
  }
}
