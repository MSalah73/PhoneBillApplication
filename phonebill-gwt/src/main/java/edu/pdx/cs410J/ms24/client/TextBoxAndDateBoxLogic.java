package edu.pdx.cs410J.ms24.client;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;

public class TextBoxAndDateBoxLogic {
    /**
   * The logic to format the date box and user-friendly error message
   * @param dateBox
   * This box which will add the logic to
   * @param textArea
   * contains the user-friendly messages
   * @return
   * return the modified datebox
   */
 DateBox setDateBoxSettings(DateBox dateBox, TextArea textArea, final String placeHolder) {
    String regex = "^((|(0?[1-9]|1?[012])|"
        + "((02/(|0?[1-9]|1|1\\d|2|2[0-8]))|(0[13578]|1|1[02])/(|0?[1-9]|[12]|[12]\\d|3|3[01])|(0[469]|11)/(|0?[1-9]|[12]|[12]\\d|30?))"
        + "|(((02/([01]\\d|2[0-8]))|((0[13578]|1[02])/([012]\\d|3[01]))|((0[469]|11)/([012]\\d|30)))/\\d{0,4})))$";
    dateBox.setFormat(new DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
    dateBox.getDatePicker().setYearAndMonthDropdownVisible(true);

    TextBox textBox = dateBox.getTextBox();
    textBox.setMaxLength(10);
    textBox.setVisibleLength(10);
    textBox.getElement().setAttribute("placeholder",placeHolder);

    textBox.addFocusHandler(new FocusHandler() {
      @Override
      public void onFocus(FocusEvent focusEvent) {
        textBox.getElement().getStyle().clearBackgroundColor();
        textArea.setText("");
      }
    });
    textBox.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(BlurEvent blurEvent) {
        if(textBox.getText().length() != 0)
          try {
            DateTimeFormat.getFormat("MM/dd/yyyy").parseStrict(dateBox.getTextBox().getText()).toString();
          }catch (Exception e){
            textBox.getElement().getStyle().setBackgroundColor("pink");
            textArea.setText("Incomplete or wrong input");
          }
      }
    });

    textBox.addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent keyDownEvent) {
        textArea.setText("");
        String text = textBox.getText();
        int keyCode = keyDownEvent.getNativeKeyCode();

        if (keyCode == KeyCodes.KEY_SHIFT || keyCode == 17)
          return;
        else if(keyDownEvent.isControlKeyDown() &&
            (keyCode == KeyCodes.KEY_V || keyCode == KeyCodes.KEY_C || keyCode == KeyCodes.KEY_X)) {
          textArea.setText("Cut/copy/Paste Not allowed");
          return;
        }

        if (keyCode == KeyCodes.KEY_BACKSPACE) {
          if (text.length() == 4 || text.length() == 7)
            textBox.setText(text.substring(0, text.length() - 1));
        } else if (!(text + (keyCode == 191 ? '/' : (char) keyCode)).matches(regex) || keyDownEvent.isShiftKeyDown()) {
          ((TextBox) keyDownEvent.getSource()).cancelKey();

          if(!Character.isDigit((char) keyCode) && !((text.length() == 1 || text.length()==4)&& keyCode == 191))
            textArea.setText("Only numbers are applicable");
          else if(Character.isDigit((char) (keyCode-3)) && (text.length() == 1 || text.length() == 4)){
            textArea.setText("Use '/' for "+text.charAt(text.length()-1)+" -> 0"+text.charAt(text.length()-1));
          }
        }

        if (keyCode == 191) {
          if (((text.length() == 1 && text.charAt(0) != '0') || (text.length() == 4 && text.charAt(text.length()-1) != '0'))) {
            textBox.setText(text.substring(0, text.length() - 1) + "0" + text.charAt(text.length() - 1) + '/');
            ((TextBox) keyDownEvent.getSource()).cancelKey();
          }else if(text.length() == 2 || text.length() == 5){
            ((TextBox) keyDownEvent.getSource()).cancelKey();
            textArea.setText("'/' will automatically be added");
          }
        } else if((text.length() == 2 || text.length() == 5) && Character.isDigit((char)keyCode))
          textBox.setText(text+ '/'+(char)keyCode);
      }

    });
    return dateBox;
  }
    /**
   * The logic to format the phone number box and user-friendly error message
   * @param textBox
   * This box which will add the logic to
   * @param textArea
   * contains the user-friendly messages
   * @return
   * return the modified textbox
   */
  TextBox setPhoneNumberBoxSettings(TextBox textBox, TextArea textArea) {
    textBox.addFocusHandler(new FocusHandler() {
      @Override
      public void onFocus(FocusEvent focusEvent) {
        textBox.getElement().getStyle().clearBackgroundColor();
        textArea.setText("");
      }
    });
    textBox.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(BlurEvent blurEvent) {
        if (!textBox.getText().matches("^(\\d{3}-){2}\\d{4}$") && !textBox.getText().isEmpty()) {
          textBox.getElement().getStyle().setBackgroundColor("pink");
          textArea.setText("Incomplete or wrong input");
        }
      }
    });

    textBox.addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent keyDownEvent) {
        String text = textBox.getText();
        textArea.setText("");
        int keyCode = keyDownEvent.getNativeKeyCode();

        if (keyCode == KeyCodes.KEY_SHIFT || keyCode == 17)
          return;
        else if(keyDownEvent.isControlKeyDown() &&
            (keyCode == KeyCodes.KEY_V || keyCode == KeyCodes.KEY_C || keyCode == KeyCodes.KEY_X)) {
          textArea.setText("Cut/copy/Paste Not allowed");
          return;
        }else if (keyCode == 173) {
          textArea.setText("'-' will automatically be added");
        }

        if (keyCode == KeyCodes.KEY_BACKSPACE) {
          if (text.length() == 5 || text.length() == 9)
            textBox.setText(text.substring(0, text.length() - 1));
        } else if (!(text + (keyCode == 173 ? '-' : (char) keyCode))
            .matches("^(\\d{0,3}|\\d{3}-|\\d{3}-\\d{0,3}|(\\d{3}-){2}\\d{0,4})$")
            || keyDownEvent.isShiftKeyDown()) {
          ((TextBox) keyDownEvent.getSource()).cancelKey();

          if(!Character.isDigit((char) keyCode))
            textArea.setText("Only numbers are applicable");
        }
        if ((text.length() == 3 || text.length() == 7) && keyCode != KeyCodes.KEY_BACKSPACE) {
          if ((char) keyDownEvent.getNativeKeyCode() == 173)
            ((TextBox) keyDownEvent.getSource()).cancelKey();
          else if (Character.isDigit((char) keyCode))
            textBox.setText(text + "-" + (char) keyCode);
        }
      }
    });
    return textBox;
  }

  /**
   * The logic to format the Time box and user-friendly error message
   * @param textBox
   * This box which will add the logic to
   * @param textArea
   * contains the user-friendly messages
   * @return
   * return the modified textbox
   */
  TextBox setHourMinuteBoxSettings(TextBox textBox, TextArea textArea) {
    String regex = "^(|0[1-9]?|[1-9]?|1[0-2]?)(:[0-5]?|:[0-5]\\d)?$";

    textBox.addFocusHandler(new FocusHandler() {
      @Override
      public void onFocus(FocusEvent focusEvent) {
        textBox.getElement().getStyle().clearBackgroundColor();
        textArea.setText("");
      }
    });
    textBox.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(BlurEvent blurEvent) {
        if (!textBox.getText().matches("^(0\\d|1[0-2]):[0-5]\\d$") && !textBox.getText().isEmpty()){
          textBox.getElement().getStyle().setBackgroundColor("pink");
          textArea.setText("Incomplete Input or wrong input");
      }}
    });

    textBox.addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent keyDownEvent) {
        String text = textBox.getText();
        textArea.setText("");
        int keyCode = keyDownEvent.getNativeKeyCode();

        if (keyCode == KeyCodes.KEY_SHIFT || keyCode == 17)
          return;

        if(keyDownEvent.isControlKeyDown() &&
            (keyCode == KeyCodes.KEY_V || keyCode == KeyCodes.KEY_C || keyCode == KeyCodes.KEY_X)) {
          textArea.setText("Cut/copy/Paste Not allowed");
          return;
        }

        if (keyCode == KeyCodes.KEY_BACKSPACE) {
          if (text.length() == 4)
            textBox.setText(text.substring(0, text.length() - 1));
        } else if (!(text + (keyCode == 59 && keyDownEvent.isShiftKeyDown() ? ':' : (char) keyCode)).matches(regex)
            || keyDownEvent.isShiftKeyDown()) {
          ((TextBox) keyDownEvent.getSource()).cancelKey();

          if (!(text.length() == 1 && keyDownEvent.isShiftKeyDown() && keyCode == 59)
              && !(Character.isDigit((char) keyCode)))
            textArea.setText("Only number are applicable");
          else if(text.length() == 1 && (keyCode == KeyCodes.KEY_ZERO || (Integer.parseInt(text+keyCode) > 12 &&
              !(keyDownEvent.isShiftKeyDown() && keyCode == 59))))
            textArea.setText("HH must be 01 to 12"+ (keyCode != KeyCodes.KEY_ZERO?
                "\nuse ':'\nfor \'"+text+":\' -> 0"+text+":":""));
          else if(text.length() == 1 && !(keyDownEvent.isShiftKeyDown() && keyCode == 59))
            textArea.setText("Use ':'");
          else if (text.length() == 2 && !Character.isDigit((char) (keyCode+4)))
            textArea.setText("MM must be 00 to 59");

        }
        if (text.length() == 2 && keyCode != KeyCodes.KEY_BACKSPACE){
          if (keyDownEvent.isShiftKeyDown() && keyCode == 59)
            ((TextBox) keyDownEvent.getSource()).cancelKey();
          else if(Character.isDigit((char) (keyCode+4)))
            textBox.setText(text + ":" + (char) keyCode);
        }else if(text.length() == 1 && keyCode == 59 && keyDownEvent.isShiftKeyDown() && !text.equals("0")){
          textBox.setText('0'+text);
        }
      }
    });
    return textBox;
  }
}
