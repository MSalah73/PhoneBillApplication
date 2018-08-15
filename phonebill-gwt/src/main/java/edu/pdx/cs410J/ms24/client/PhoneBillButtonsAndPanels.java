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
  final String README = "PhoneBill Application. \n"
            + "\n"
            + "Name: Zack Salah \n"
            + "\n"
            + "This application stores a customer's phone bill information. "
            + "PhoneBill lists the name of the customer and number of phone "
            + "calls made. PhoneBill also lists a record of individual\n phone "
            + "calls that a customer has received or initiated. The phone call "
            + "records contain the caller phone number, callee phone number,"
            + "start date and time of the call,the end date\n and time and the"
            + "duration of the each call.";
  final String Home = "Welcome to the PhoneBill Application\n"
      + "This application allow you to store multiple PhoneBills\n"
      + "For multiple users. It also allows you to search PhoneCalls\n"
      + "for a particular user within a specific time. Not to mention\n "
      + "the ability beautifully print a PhoneBill information for a\n"
      + "particular user.\n\nYou can start by using the tabs above to\n"
      + "use the app's unique features right away.";
/*            + "\n"
            + "Usage:\n"
            + "\n"
            + "format:\n"
            + "Name\n"
            + "Caller Phone Number --> XXX-XXX-XXXX \n"
            + "Callee Phone Number --> XXX-XXX-XXXX\n"
            + "Date --> XX/XX/XXXX"
            + "Time --> XX:XX"
            + "Marker am/pm\n";
   /**
   * Box Option Enum
   */
  enum BoxSetting{
    HourAndMinute, PhoneNumber;
  }

  private TextBoxAndDateBoxLogic textBoxAndDateBoxLogic = new TextBoxAndDateBoxLogic();

    /**
     * This methods add the DateBox with TextArea to a Vertical Panel
     * @param placeHolder
     * The Text shown Text Box is empty
     * @return
     * The vertical panel with added datebox and textArea
     */
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

  /**
   *
   * This methods add the textBox with TextArea to a Vertical Panel
   * @param placeHolder
   * The Text shown Text Box is empty
   * @param boxSetting
   * The Phone number or Time option to add the logic to the textbox
   * @return
   * The vertical panel with added datebox and textArea
   */
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

    /**
     * This method calculate the value of a requested percentage from client window
     * @param width
     * Option width or heights
     * @param percentage
     * the presentage to preform calculation on
     * @return
     * the window side calculated from the percentage
     */
  private int windowCalculator(boolean width,Double percentage){
    return (int) (width?Window.getClientWidth()*(percentage): Window.getClientHeight()*(percentage));
  }

    /**
     * This method removes the extendability of the textArea by adding a fixed size to the textarea box
     * @param width
     * The requested widths
     * @param height
     * The rquested Heights
     * @param style
     * The the style of the widgets
     */
  void setFixedSizeWidget(double width, double height, Style style) {
    style.setProperty("maxWidth",String.valueOf(windowCalculator(true,width))+"px");
    style.setProperty("maxHeight",String.valueOf(windowCalculator(false,height)+"px"));
    style.setProperty("minWidth",String.valueOf(windowCalculator(true,width))+"px");
    style.setProperty("minHeight",String.valueOf(windowCalculator(false,height)+"px"));
  }

    /**
     * Create a close button to any widget
     * @param widget
     * The widget for the close button to hide
     * @return
     * the button with the ability to hide a requested widget
     */
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

    /**
     * This methods create a textarea with a button that hides it
     * @param text
     * the message that the text area set to
     * @param width
     * The fixed width of the text area
     * @param height
     * the fixed heights of the text area
     * @param textArea
     * the textarea to add the functionality  to
     * @return
     * A panel that can hide with a click of a button
     */
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

    /**
     * create the menu for readme
     * @return
     * the panel with the menu attached
     */
  VerticalPanel createHelpMenu(){
    MenuBar mainMenu = new MenuBar();
    MenuBar subMenu = new MenuBar(true);
    DecoratorPanel readMe = createHidableTextArea(README, .97, .07, null);
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

    /**
     * create the textarea with no borders
     * @param text
     * Text set in the text area
     * @param width
     * the fixed width
     * @param height
     * the fixed heights
     * @return
     * return the modified textarea
     */
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

    /**
     * crate the menu with hidable panel
     * @return
     * return the merged panel
     */
  VerticalPanel createHomePage(){
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(createHelpMenu());
    verticalPanel.add(createTextAreaWithNoBorders(Home,.97, .15));
    return verticalPanel;
  }

    /**
     * set uo the label with alignments
     * @param name
     * the text to add to the label
     * @return
     * the label created
     */
  Label labelSetup(String name){
    Label label = new Label(name);
    label.setHorizontalAlignment(Label.ALIGN_CENTER);
    return label;
  }
}
