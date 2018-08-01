package edu.pdx.cs410J.ms24;

import static junit.framework.TestCase.assertTrue;

import java.util.List;
import org.junit.Test;
public class ArgumentsValidatorTest {
  //@Test(expected = IllegalArgumentException.class)
  @Test
  public void optionTest(){
    try {
      argumentsValidator.argumentsValidator(new String[]{"-port","11","-host","f","-search","k",
          "11/11/1111","11:11", "pm", "11/11/1111","11:11","am"});
      argumentsValidator.argumentsValidator(new String[]{"-search","-port","11","-search","-host","f","-search","-search","k",
          "11/11/1111","11:11", "pm", "11/11/1111","11:11","am"});
      argumentsValidator.argumentsValidator(new String[]{"-port","11","-host","f","K",
          "111-111-1111","111-111-1111","11/11/1111","11:11", "pm", "11/11/1111","11:11","am"});
      argumentsValidator.argumentsValidator(new String[]{"-port","11","-host","f","-print","k",
      "111-111-1111","111-111-1111","11/11/1111","11:11", "pm", "11/11/1111","11:11","am"});
      argumentsValidator.argumentsValidator(new String[]{"-port","11","-host","f"});
    }catch(IllegalArgumentException ex){
      System.out.println(ex.getMessage());
    }
  }
  private ArgumentsValidator argumentsValidator = new ArgumentsValidator();
}
