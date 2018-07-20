package edu.pdx.cs410J.ms24;

import static org.hamcrest.MatcherAssert.assertThat;

import edu.pdx.cs410J.ParserException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import org.junit.Test;
@Deprecated
public class TextParserTest {
  @Test
  public void phoneBillParserStoreNameInPhoneBillObject() throws IOException, ParserException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader("Test.txt"));
    String Name = bufferedReader.readLine();
    TextParser textParser = new TextParser("Test");
    PhoneBill phoneBill = textParser.parse();
    assertThat("PhoneBill ia not stored correctly" , phoneBill.getCustomer().equals(Name));
  }
  @Test
  public void readWithFileFormattedCorrectly() throws IOException, ParserException {
    TextParser textParser = new TextParser("Test");
    PhoneBill phoneBill = textParser.parse();
    Collection<PhoneCall> list = phoneBill.getPhoneCalls();
    assertThat("Wrong format of PhoneCalls" , list.toArray()[0].toString().
        contains("Phone call from"));
  }
  @Deprecated
  public void readWithFileStoreAllPhoneCallsFromFile() throws IOException, ParserException {
    TextParser textParser = new TextParser("Test");
    PhoneBill phoneBill = textParser.parse();
    Collection<PhoneCall> list = phoneBill.getPhoneCalls();
    assertThat("Collection of PhoneCall object should read all the phonecalls in the file", list.size() == 4);
  }
  @Test
  public void NonexistentFile() throws IOException, ParserException {
    TextParser textParser = new TextParser("Em");
    PhoneBill phoneBill = phoneBill = textParser.parse();
    assertThat("Non existing file should fail", phoneBill == null);
  }
  @Test
  public void readWithEmptyFile() throws IOException, ParserException {
    TextParser textParser = new TextParser("Empty");
    PhoneBill phoneBill = phoneBill = textParser.parse();
    assertThat("Empty fail should have Phonebill object as null", phoneBill == null);
  }
  @Test(expected = ParserException.class)
  public void readWithFileFormattedWrongly() throws IOException, ParserException {
    TextParser textParser = new TextParser("NotFormattedCorrectly");
    PhoneBill phoneBill = phoneBill = textParser.parse();
  }
}
