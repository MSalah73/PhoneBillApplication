package edu.pdx.cs410J.ms24.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

  /**
   * Box Option Enum
   */


public class PhoneBillButtonsAndPanels {
   /**
   * Box Option Enum
   */
  enum BoxSetting{
    HourAndMinute, PhoneNumber;
  }
  private TextBoxAndDateBoxLogic textBoxAndDateBoxLogic = new TextBoxAndDateBoxLogic();

  public VerticalPanel createDatePanel(final String placeHolder){
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    TextArea textArea = createTextAreaWithNoBorders("",.1, .05);
    textArea.getElement().getStyle().setColor("red");

    DateBox dateBox = textBoxAndDateBoxLogic.setDateBoxSettings(new DateBox(), textArea, "Date");
    dateBox.getTextBox().setAlignment(TextAlignment.CENTER);

    verticalPanel.add(dateBox);
    verticalPanel.add(textArea);

    return verticalPanel;
  }
  public VerticalPanel createTimeOrPhonePanel(final String placeHolder, BoxSetting boxSetting){
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    //verticalPanel.getElement().getStyle().setPadding(5, Unit.PX);

    TextBox textBox = new TextBox();
    textBox.setAlignment(TextAlignment.CENTER);

    TextArea textArea = createTextAreaWithNoBorders("",.1, .05);
    textArea.getElement().getStyle().setColor("red");

    int boxSize = 0;

    switch (boxSetting){
      case HourAndMinute:
        boxSize = 5;
        textBox = textBoxAndDateBoxLogic.setHourMinuteBoxSettings(textBox, textArea);
        break;
      case PhoneNumber:
        boxSize = 12;
        textBox = textBoxAndDateBoxLogic.setPhoneNumberBoxSettings(textBox,textArea);
        break;
    }

    textBox.setMaxLength(boxSize);
    textBox.setVisibleLength(boxSize);
    textBox.getElement().setAttribute("placeholder", placeHolder);

    verticalPanel.add(textBox);
    verticalPanel.add(textArea);

    return verticalPanel;
  }

  private int windowCalculator(boolean width,Double percentage){
    return (int) (width?Window.getClientWidth()*(percentage): Window.getClientHeight()*(percentage));
  }
  void setFixedSizeWidget(double width, double height, Style style) {
    style.setProperty("maxWidth",String.valueOf(windowCalculator(true,width))+"px");
    style.setProperty("maxHeight",String.valueOf(windowCalculator(false,height)+"px"));
    style.setProperty("minWidth",String.valueOf(windowCalculator(true,width))+"px");
    style.setProperty("minHeight",String.valueOf(windowCalculator(false,height)+"px"));
  }
  Button createCloseButton(Widget widget){
    Button button= new Button();
    button.setHTML("Close");
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        //getClass.isAssignableFrom is not included so
        //I'm doing this to imitate the logic if I can use isAssignableFrom
        try {
          ((PopupPanel) widget).hide();
        }catch (Exception ex) {
          widget.setVisible(false);
        }
      }
    });
    return button;
  }
  DecoratorPanel createHidableTextArea(String text, double width, double height, TextArea textArea){
    DecoratorPanel decoratorPanel = new DecoratorPanel();
    VerticalPanel verticalPanel = new VerticalPanel();
    Button button;

    verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    verticalPanel.setSpacing(5);

    textArea = new TextArea();
    textArea.setText(text);
    textArea.getElement().getStyle().setColor("black");
    textArea.getElement().getStyle().setTextAlign(TextAlign.CENTER);
    textArea.setReadOnly(true);

    setFixedSizeWidget(width,height, textArea.getElement().getStyle());

    button = createCloseButton(decoratorPanel);

    verticalPanel.add(textArea);
    verticalPanel.add(button);
    decoratorPanel.add(verticalPanel);
    return decoratorPanel;
  }
  VerticalPanel createHelpMenu(){
    MenuBar mainMenu = new MenuBar();
    MenuBar subMenu = new MenuBar(true);
    DecoratorPanel readMe = createHidableTextArea("README", .97, .07, null);
    VerticalPanel verticalPanel = new VerticalPanel();

    readMe.setVisible(false);

    mainMenu.setAutoOpen(true);
    subMenu.setAutoOpen(true);

    mainMenu.setAnimationEnabled(true);
    subMenu.setAnimationEnabled(true);

    subMenu.addItem(new MenuItem("README", new Command() {
      @Override
      public void execute() {
        readMe.setVisible(true);
      }
    }));

    mainMenu.setWidth("53px");
    mainMenu.addItem(new MenuItem("Help",subMenu));

    verticalPanel.add(mainMenu);
    verticalPanel.add(readMe);

    return verticalPanel;
  }
  TextArea createTextAreaWithNoBorders(String text, double width, double height){
    TextArea textArea = new TextArea();

    textArea.setText(text);
    textArea.getElement().getStyle().setBorderStyle(BorderStyle.HIDDEN);
    textArea.getElement().getStyle().setTextAlign(TextAlign.CENTER);
    textArea.getElement().getStyle().setColor("black");
    textArea.setReadOnly(true);

    setFixedSizeWidget(width,height,textArea.getElement().getStyle());

    return textArea;
  }
  VerticalPanel createHomePage(){
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(createHelpMenu());
    verticalPanel.add(createTextAreaWithNoBorders("herllo",.97, .1));
    return verticalPanel;
  }
    Label labelSetup(String name){
    Label label = new Label(name);
    label.setHorizontalAlignment(Label.ALIGN_CENTER);
    return label;
  }
}
