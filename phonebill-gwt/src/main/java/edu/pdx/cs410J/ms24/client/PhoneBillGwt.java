package edu.pdx.cs410J.ms24.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import edu.pdx.cs410J.ms24.client.PhoneBillButtonsAndPanels.BoxSetting;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {

  private final Alerter alerter;
  private final PhoneBillServiceAsync phoneBillService;
  private final Logger logger;
  private final PhoneBillButtonsAndPanels phoneBillButtonsAndPanels = new PhoneBillButtonsAndPanels();

  /**
   * Box Option Enum
   */
  enum PageType{
    AddPhoneCall, SearchPhoneCall;
  }
  /**
   * Panels
   */
  TabPanel        tabPanel;         VerticalPanel   addPhoneBillPanel;
  VerticalPanel   prettyPrintPanel; VerticalPanel   searchPhoneCallsPanel;
  VerticalPanel   homePanel;
  /**
   * Text information for Panels
   */
  DialogBox       errorLog;
  /**
   * Add phone call widgets
   */
  TextBox         addCustomer;
  TextBox         addCaller;
  TextBox         addCallee;

  TextBox         addStartTime;     TextBox         addEndTime;
  DateBox         addStartDate;     DateBox         addEndDate;
  ToggleButton    addStartMarker;   ToggleButton    addEndMarker;
  CheckBox        printAddedPhoneCall;
  /**
   * Search Phone calls widgets
   */
  TextBox         searchCustomer;

  TextBox         searchStartTime;   TextBox         searchEndTime;
  DateBox         searchStartDate;   DateBox         searchEndDate;
  ToggleButton    searchStartMarker; ToggleButton    searchEndMarker;
  /**
   * Pretty print widgets
   */
  TextBox         printCustomer;
  /**
   * Page's display
   */
  DecoratorPanel  addPage;           DecoratorPanel  searchPage;
  DecoratorPanel  prettyPage;

  /**
   * menu
   */
  VerticalPanel   addMenu;           VerticalPanel   searchMenu;
  VerticalPanel   prettyMenu;

  /**
   * Page messages
   */
  String addMessage = "Welcome to the \"Add Phone Call\" facility.\n"
      + "This allows you to add as many phone calls as you want for any user."
      + "For new users, a new account will be generated for them adn adds the phone call to their account.";
  String searchMessage = "Welcome to the \"Search Phone Calls\" facility.\n"
      + "This allows to search a particular phone bill within the requested start and end date and time";
  String prettyMessage = "Welcome to the \"Pretty Print Phone Bill\" facility.\n"
      + "This allows to display a beautiful and user-friendly phone bill for a particular customer.";
  public PhoneBillGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  PhoneBillGwt(Alerter alerter) {
    this.alerter = alerter;
    this.phoneBillService = GWT.create(PhoneBillService.class);
    this.logger = Logger.getLogger("phoneBill");
    Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
  }

  private void alertOnException(Throwable throwable) {
    Throwable unwrapped = unwrapUmbrellaException(throwable);
    StringBuilder sb = new StringBuilder();
    sb.append(unwrapped.toString());
    sb.append('\n');

    for (StackTraceElement element : unwrapped.getStackTrace()) {
      sb.append("  at ");
      sb.append(element.toString());
      sb.append('\n');
    }

    this.alerter.alert(sb.toString());
  }

  private Throwable unwrapUmbrellaException(Throwable throwable) {
    if (throwable instanceof UmbrellaException) {
      UmbrellaException umbrella = (UmbrellaException) throwable;
      if (umbrella.getCauses().size() == 1) {
        return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
      }

    }

    return throwable;
  }

  /**
   * This method communicate with the server to add a phone call
   */
  private void  addPhoneCall(){
    final String[] phoneCallInfo = new String[]{addCustomer.getText(), addCaller.getText(), addCallee.getText(),
        addStartDate.getTextBox().getText() ,addStartTime.getText(),addStartMarker.getText(),
        addEndDate.getTextBox().getText(), addEndTime.getText(), addEndMarker.getText()};

    phoneBillService.addPhoneCall(phoneCallInfo, printAddedPhoneCall.getValue(),
        new AsyncCallback<String>() {
          @Override
          public void onFailure(Throwable throwable) {
            ((TextArea)((VerticalPanel) addPage.getWidget()).getWidget(0)).setText(throwable.getMessage());
          }
          @Override
          public void onSuccess(String s) {
            ((TextArea)((VerticalPanel) addPage.getWidget()).getWidget(0)).setText(s);
          }
        });
    addPage.setVisible(true);
  }

  /**
   * This method communicate with the server to search phone calls
   */
  private void searchPhoneCalls(){
    phoneBillService.searchPhoneCalls(searchCustomer.getText(), searchStartDate.getValue(), searchEndDate.getValue(),
        new AsyncCallback<String>() {
          @Override
          public void onFailure(Throwable throwable) {
            ((TextArea)((VerticalPanel) searchPage.getWidget()).getWidget(0)).setText(throwable.getMessage());
          }
          @Override
          public void onSuccess(String s) {
            ((TextArea)((VerticalPanel) searchPage.getWidget()).getWidget(0)).setText(s);
          }
        });
    searchPage.setVisible(true);

  }

  /**
   * This method communicate with the server to pretty print a phone bill
   */
  private void prettyPrintPhoneBill(){
    phoneBillService.prettyPrint(printCustomer.getText(),new AsyncCallback<String>() {
      @Override
      public void onFailure(Throwable throwable) {
       ((TextArea)((VerticalPanel) prettyPage.getWidget()).getWidget(0)).setText(throwable.getMessage());
      }

      @Override
      public void onSuccess(String s) {
        ((TextArea)((VerticalPanel) prettyPage.getWidget()).getWidget(0)).setText(s);
      }
    });
    prettyPage.setVisible(true);
  }

  @Override
  public void onModuleLoad() {
    setUpUncaughtExceptionHandler();

    // The UncaughtExceptionHandler won't catch exceptions during module load
    // So, you have to set up the UI after module load...
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        setupUI();
      }
    });
  }

  /**
   * This method create the tabs "Pages" and populated them with widgets so the user can interact with.
   */
  private void createTabPages(){
    errorLog = new DialogBox();
    errorLog.setAnimationEnabled(true);
    errorLog.setGlassEnabled(true);
    errorLog.add(phoneBillButtonsAndPanels.createCloseButton(errorLog));

    tabPanel = new TabPanel();

    phoneBillButtonsAndPanels.setFixedSizeWidget(.987,.905, tabPanel.getDeckPanel().getElement().getStyle());

    tabPanel.getElement().getStyle().setPadding(5, Unit.PX);
    tabPanel.getDeckPanel().getElement().getStyle().setPadding(5, Unit.PX);
    tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
      @Override
      public void onBeforeSelection(BeforeSelectionEvent<Integer> beforeSelectionEvent) {
        dateReset();
      }
    });
    homePanel = phoneBillButtonsAndPanels.createHomePage();
    addPhoneBillPanel = createAddPhoneCallPage();
    searchPhoneCallsPanel = createSearchPage();
    prettyPrintPanel = createPrettyPrintPage();

    tabPanel.add(homePanel, "Home");
    tabPanel.add(addPhoneBillPanel,"Add Phone Call");
    tabPanel.add(searchPhoneCallsPanel, "Search Phone Calls");
    tabPanel.add(prettyPrintPanel, "Pretty Print PhoneBill");
  }
  private void setupUI() {
    RootPanel rootPanel = RootPanel.get();
    createTabPages();
    tabPanel.selectTab(0);
    rootPanel.get().add(tabPanel);
  }

  /**
   * This create and populate the addPhoneBill Page with widgets
   * @return
   * Vertical panel -- "Page"
   */
  private VerticalPanel createAddPhoneCallPage(){
    VerticalPanel page = new VerticalPanel();
    addMenu = phoneBillButtonsAndPanels.createHelpMenu();
    page.add(addMenu);
    page.setSpacing(8);
    page.add(phoneBillButtonsAndPanels.createTextAreaWithNoBorders(addMessage, .97, .03));

    HorizontalPanel horizontalPanel;
    VerticalPanel verticalPanelToAdd;


    Label label = phoneBillButtonsAndPanels.labelSetup("Customer's Name: ");
    label.getElement().getStyle().setPaddingRight(50,Unit.PX);
    addCustomer = new TextBox();
    addCustomer.getElement().setAttribute("placeholder","Name");
    addCustomer.setAlignment(TextAlignment.CENTER);
    addCustomer.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        addCustomer.getElement().getStyle().clearBackgroundColor();
      }
    });
    printAddedPhoneCall = new CheckBox("Print added call");

    horizontalPanel = new HorizontalPanel();
    horizontalPanel.setSpacing(4);
    horizontalPanel.add(label);
    horizontalPanel.add(addCustomer);

    page.add(horizontalPanel);
    page.add(callerCalleePanel());
    page.add(dateAndTimePanel(PageType.AddPhoneCall));
    page.add(printAddedPhoneCall);
    page.add(addCallButton());

    addPage = phoneBillButtonsAndPanels.createHidableTextArea("",.97,.03, null);
    addPage.setVisible(false);

    page.add(addPage);

    return page;
  }
  /**
   * This create and populate the searchPhoneCalls Page with widgets
   * @return
   * Vertical panel -- "Page"
   */
  private VerticalPanel createSearchPage(){
    VerticalPanel page = new VerticalPanel();
    searchMenu = phoneBillButtonsAndPanels.createHelpMenu();
    page.add(searchMenu);
    page.setSpacing(8);
    page.add(phoneBillButtonsAndPanels.createTextAreaWithNoBorders(searchMessage, .97, .03));

    HorizontalPanel horizontalPanel;
    VerticalPanel verticalPanelToAdd;

    Label label = phoneBillButtonsAndPanels.labelSetup("Customer's Name: ");
    label.getElement().getStyle().setPaddingRight(50,Unit.PX);
    searchCustomer = new TextBox();
    searchCustomer.getElement().setAttribute("placeholder","Name");
    searchCustomer.setAlignment(TextAlignment.CENTER);
    searchCustomer.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        searchCustomer.getElement().getStyle().clearBackgroundColor();
      }
    });

    horizontalPanel = new HorizontalPanel();
    horizontalPanel.setSpacing(4);
    horizontalPanel.add(label);
    horizontalPanel.add(searchCustomer);

    page.add(horizontalPanel);
    page.add(dateAndTimePanel(PageType.SearchPhoneCall));
    page.add(addSearchButton());//add new

    searchPage = phoneBillButtonsAndPanels.createHidableTextArea("",.97,.2, null);//searchPage
    searchPage.setVisible(false);

    page.add(searchPage);

    return page;
  }
    /**
   * This create and populate the prettyPrintPhoneBill Page with widgets
   * @return
   * Vertical panel -- "Page"
   */
  private VerticalPanel createPrettyPrintPage(){
    VerticalPanel page = new VerticalPanel();
    prettyMenu = phoneBillButtonsAndPanels.createHelpMenu();
    page.add(prettyMenu);
    page.setSpacing(8);
    page.add(phoneBillButtonsAndPanels.createTextAreaWithNoBorders(prettyMessage, .97, .03));

    Label label = phoneBillButtonsAndPanels.labelSetup("Customer's Name: ");
    label.getElement().getStyle().setPaddingRight(50,Unit.PX);
    printCustomer = new TextBox();
    printCustomer.getElement().setAttribute("placeholder","Name");
    printCustomer.setAlignment(TextAlignment.CENTER);
    printCustomer.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        printCustomer.getElement().getStyle().clearBackgroundColor();
      }
    });
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.setSpacing(4);
    horizontalPanel.add(label);
    horizontalPanel.add(printCustomer);

    page.add(horizontalPanel);
    page.add(addPrettyButton());//add new

    prettyPage = phoneBillButtonsAndPanels.createHidableTextArea("",.97,.4, null);//searchPage
    prettyPage.setVisible(false);

    page.add(prettyPage);

    return page;
  }

  /**
   * This methods add logic to the button for the addPhoneCall Page
   * @return Button
   */
  private Button addCallButton() {
    Button button = new Button();
    button.setHTML("Submit");
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        String log = new String();
        int startValid = 0;
        int endValid = 0;
        boolean isRed = false;

        if(addCustomer.getText().length() == 0){
          addCustomer.getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        }

        if (addCaller.getText().length() == 0) {
          addCaller.getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        }else if (addCaller.getElement().getStyle().getBackgroundColor() == "pink")
          isRed = true;

        if (addCallee.getText().length() == 0){
          addCallee.getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        } else if (addCallee.getElement().getStyle().getBackgroundColor() == "pink")
          isRed = true;

        if (addStartDate.getTextBox().getText().length() == 0){
          addStartDate.getTextBox().getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        }
        else if (addStartDate.getTextBox().getElement().getStyle().getBackgroundColor() == "pink")
          isRed = true;
        else
          ++startValid;

        if (addStartTime.getText().length() == 0){
          addStartTime.getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        } else if (addStartTime.getElement().getStyle().getBackgroundColor() == "pink")
          isRed = true;
        else
          ++startValid;

        if (addEndDate.getTextBox().getText().length() == 0){
          addEndDate.getTextBox().getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        } else if (addEndDate.getTextBox().getElement().getStyle().getBackgroundColor() == "pink")
          isRed = true;
        else
          ++endValid;

        if (addEndTime.getText().length() == 0){
          addEndTime.getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        } else if (addEndTime.getElement().getStyle().getBackgroundColor() == "pink")
          isRed = true;
        else
          ++endValid;

        if (startValid == 2 && endValid == 2 && !isRed) {
          String Start = addStartDate.getTextBox().getText()+" "+addStartTime.getText()+" "+addStartMarker.getText();
          String End = addEndDate.getTextBox().getText()+" "+addEndTime.getText()+" "+addEndMarker.getText();
          if (!DateTimeFormat.getFormat("MM/dd/yyyy h:mm a").parse(Start)
              .before(DateTimeFormat.getFormat("MM/dd/yyyy h:mm a").parse(End)))
            log += "The end date and time of the phone call not not be before it started or equal to it";
        }
        //getClass.isAssignableFrom is not included so
        //I'm doing this to imitate the logic if I can use isAssignableFrom
        if (!log.isEmpty() || isRed) {
          errorLog.setText(isRed?"Fields in red are either missing or wrong":log);
          errorLog.center();
          errorLog.show();
        }else {
          addMenu.getWidget(1).setVisible(false);
          addPhoneCall();
        }
      }
    });
    return button;
  }
  /**
   * This methods add logic to the button for the searchPhoneCalls Page
   * @return Button
   */
  private Button addSearchButton(){
    Button button = new Button();
    button.setHTML("Submit");
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        String log = new String();
        int startValid = 0;
        int endValid = 0;
        boolean isRed = false;

        if(searchCustomer.getText().length() == 0){
          searchCustomer.getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        }

        if (searchStartDate.getTextBox().getText().length() == 0){
          searchStartDate.getTextBox().getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        }
        else if (searchStartDate.getTextBox().getElement().getStyle().getBackgroundColor() == "pink")
          isRed = true;
        else
          ++startValid;

        if (searchStartTime.getText().length() == 0){
          searchStartTime.getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        } else if (searchStartTime.getElement().getStyle().getBackgroundColor() == "pink")
          isRed = true;
        else
          ++startValid;

        if (searchEndDate.getTextBox().getText().length() == 0){
          searchEndDate.getTextBox().getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        } else if (searchEndDate.getTextBox().getElement().getStyle().getBackgroundColor() == "pink")
          isRed = true;
        else
          ++endValid;

        if (searchEndTime.getText().length() == 0){
          searchEndTime.getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        } else if (searchEndTime.getElement().getStyle().getBackgroundColor() == "red")
          isRed = true;
        else
          ++endValid;

        if (startValid == 2 && endValid == 2 && !isRed) {
          String Start = searchStartDate.getTextBox().getText()+" "+searchStartTime.getText()+" "+searchStartMarker.getText();
          String End = searchEndDate.getTextBox().getText()+" "+searchEndTime.getText()+" "+searchEndMarker.getText();
          if (!DateTimeFormat.getFormat("MM/dd/yyyy h:mm a").parse(Start)
              .before(DateTimeFormat.getFormat("MM/dd/yyyy h:mm a").parse(End)))
            log += "The end date and time of the phone call not not be before it started or equal to it";
        }
        //getClass.isAssignableFrom is not included so
        //I'm doing this to imitate the logic if I can use isAssignableFrom
        if (!log.isEmpty() || isRed) {
          errorLog.setText(isRed?"Fields in red are either missing or wrong":log);
          errorLog.center();
          errorLog.show();
        }else {
          searchMenu.getWidget(1).setVisible(false);
          searchPhoneCalls();
        }
      }
    });
    return button;
  }

  /**
   * This methods adds logic to the prettyPrintPhoneBill Page
   * @return button
   */
  private Button addPrettyButton(){
    Button button = new Button();
    button.setHTML("Submit");
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        String log = new String();
        boolean isRed = false;

        if(printCustomer.getText().length() == 0){
          printCustomer.getElement().getStyle().setBackgroundColor("pink");
          isRed = true;
        }
        //getClass.isAssignableFrom is not included so
        //I'm doing this to imitate the logic if I can use isAssignableFrom
        if (!log.isEmpty() || isRed) {
          errorLog.setText(isRed?"Fields in red are either missing or wrong":log);
          errorLog.center();
          errorLog.show();
        }else {
          prettyMenu.getWidget(1).setVisible(false);
          prettyPrintPhoneBill();
        }
      }
    });
    return button;
  }

  /**
   * This methods create a horizontal panel that contains the caller and callee widgets to add to Pages
   * @return Horizontal Panel
   */
  private HorizontalPanel callerCalleePanel(){
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    VerticalPanel verticalPanel;
    horizontalPanel.setSpacing(4);
    horizontalPanel.getElement().getStyle().setPaddingTop(30,Unit.PX);

    verticalPanel = phoneBillButtonsAndPanels.createTimeOrPhonePanel("Caller", BoxSetting.PhoneNumber);
    addCaller = ((TextBox) verticalPanel.getWidget(0));

    horizontalPanel.add(phoneBillButtonsAndPanels.labelSetup("Caller's Phone Number: "));
    horizontalPanel.add(verticalPanel);

    verticalPanel = phoneBillButtonsAndPanels.createTimeOrPhonePanel("Callee", BoxSetting.PhoneNumber);
    addCallee = ((TextBox) verticalPanel.getWidget(0));

    horizontalPanel.add(phoneBillButtonsAndPanels.labelSetup("Callee's Phone Number: "));
    horizontalPanel.add(verticalPanel);
    return horizontalPanel;
  }
    /**
   * This methods create a vertical panel that contains the Date and Time widgets to add to Pages
   * @return Vertical Panel
   */
  private VerticalPanel dateAndTimePanel(PageType pageType){
    HorizontalPanel start = new HorizontalPanel();
    HorizontalPanel end = new HorizontalPanel();
    VerticalPanel toReturn = new VerticalPanel();

    VerticalPanel verticalPanel;

    start.setSpacing(4);
    end.setSpacing(4);

    boolean type = pageType.equals(PageType.AddPhoneCall);

    if(!type) {
      verticalPanel = phoneBillButtonsAndPanels.createDatePanel("Date");
      searchStartDate = ((DateBox) verticalPanel.getWidget(0));

      start.add(phoneBillButtonsAndPanels.labelSetup("From: "));
      start.add(verticalPanel);

      verticalPanel = phoneBillButtonsAndPanels.createTimeOrPhonePanel("Time", BoxSetting.HourAndMinute);
      searchStartTime = (TextBox) verticalPanel.getWidget(0);

      searchStartMarker = new ToggleButton("AM", "PM");

      start.add(verticalPanel);
      start.add(searchStartMarker);

      verticalPanel = phoneBillButtonsAndPanels.createDatePanel("Date");
      searchEndDate = ((DateBox) verticalPanel.getWidget(0));

      end.add(phoneBillButtonsAndPanels.labelSetup("To: "));
      end.add(verticalPanel);

      verticalPanel = phoneBillButtonsAndPanels.createTimeOrPhonePanel("Time", BoxSetting.HourAndMinute);
      searchEndTime = (TextBox) verticalPanel.getWidget(0);

      searchEndMarker = new ToggleButton("AM", "PM");

      end.add(verticalPanel);
      end.add(searchEndMarker);
    }else{
      verticalPanel = phoneBillButtonsAndPanels.createDatePanel("Date");
      addStartDate = ((DateBox) verticalPanel.getWidget(0));

      start.add(phoneBillButtonsAndPanels.labelSetup("Call started on: "));
      start.add(verticalPanel);

      verticalPanel = phoneBillButtonsAndPanels.createTimeOrPhonePanel("Time", BoxSetting.HourAndMinute);
      addStartTime = (TextBox) verticalPanel.getWidget(0);

      addStartMarker = new ToggleButton("AM", "PM");

      start.add(verticalPanel);
      start.add(addStartMarker);

      verticalPanel = phoneBillButtonsAndPanels.createDatePanel("Date");
      addEndDate = ((DateBox) verticalPanel.getWidget(0));

      end.add(phoneBillButtonsAndPanels.labelSetup("Call ended on: "));
      end.add(verticalPanel);

      verticalPanel = phoneBillButtonsAndPanels.createTimeOrPhonePanel("Time", BoxSetting.HourAndMinute);
      addEndTime = (TextBox) verticalPanel.getWidget(0);

      addEndMarker = new ToggleButton("AM", "PM");

      end.add(verticalPanel);
      end.add(addEndMarker);
    }
    toReturn.add(start);
    toReturn.add(end);
    return toReturn;
  }

  /**
   * This method reset the TextBoxes and TextAreas -- its used when changes tabs "Pages"
   */
  private void dateReset(){
    addCustomer.setText("");
    addCustomer.getElement().getStyle().clearBackgroundColor();

    printCustomer.setText("");
    printCustomer.getElement().getStyle().clearBackgroundColor();

    searchCustomer.setText("");
    searchCustomer.getElement().getStyle().clearBackgroundColor();

    addCaller.setText("");
    addCaller.getElement().getStyle().clearBackgroundColor();

    addCallee.setText("");
    addCallee.getElement().getStyle().clearBackgroundColor();

    addStartDate.getTextBox().setText("");
    addStartDate.getTextBox().getElement().getStyle().clearBackgroundColor();

    addEndDate.getTextBox().setText("");
    addEndDate.getTextBox().getElement().getStyle().clearBackgroundColor();

    addStartTime.setText("");
    addStartTime.getElement().getStyle().clearBackgroundColor();

    addEndTime.setText("");
    addEndTime.getElement().getStyle().clearBackgroundColor();

    addStartMarker.setDown(false);
    addEndMarker.setDown(false);

    printAddedPhoneCall.setValue(false);

    searchStartDate.getTextBox().setText("");
    searchStartDate.getTextBox().getElement().getStyle().clearBackgroundColor();

    searchEndDate.getTextBox().setText("");
    searchEndDate.getTextBox().getElement().getStyle().clearBackgroundColor();

    searchStartTime.setText("");
    searchStartTime.getElement().getStyle().clearBackgroundColor();

    searchEndTime.setText("");
    searchEndTime.getElement().getStyle().clearBackgroundColor();

    searchStartMarker.setDown(false);
    searchEndMarker.setDown(false);

    addPage.setVisible(false);
    searchPage.setVisible(false);
    prettyPage.setVisible(false);

    addMenu.getWidget(1).setVisible(false);
    searchMenu.getWidget(1).setVisible(false);
    prettyMenu.getWidget(1).setVisible(false);

  }
  private void setUpUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable throwable) {
        alertOnException(throwable);
      }
    });
  }
  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }
}