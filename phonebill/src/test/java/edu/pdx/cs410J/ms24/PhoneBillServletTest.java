package edu.pdx.cs410J.ms24;

import com.google.common.collect.Lists;
import edu.pdx.cs410J.web.HttpRequestHelper.Response;
import java.util.Date;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.internal.MethodSorter;
import org.junit.runners.MethodSorters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {
    private String name = "Zack";
    private String caller = "111-111-1111";
    private String caller2 = "999-999-9999";
    private String callee = "211-111-1111";
    private String callee2 = "277-181-1231";
    private String sdate = "11/11/1111";
    private String sdate2 = "12/17/1111";
    private String stime = "11:11";
    private String stime2 = "9:11";
    private String smarker = "am";
    private String smarker2 = "am";
    private String edate = "11/11/1111";
    private String edate2 = "12/17/1111";
    private String etime = "11:11";
    private String etime2 = "9:30";
    private String emarker = "pm";
    private String emarker2 = "pm";
    private String noprint = null;
    private String print = "print";
    private String search = "search";
    private String printall = "printall";
    private PhoneCall call1 = new PhoneCall(List.of(caller,callee,sdate,stime,smarker,edate,etime,emarker).toArray(new String[0]));
    private PhoneCall call2 = new PhoneCall(List.of(caller2,callee2,sdate2,stime2,smarker2,edate2,etime2,emarker2).toArray(new String[0]));
  @Test
  public void addPhoneCall() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();
    addPhoneCall(servlet,1);
  }
    @Test
  public void addPhoneCallWithPrint() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(name);
    when(request.getParameter("caller")).thenReturn(caller2);
    when(request.getParameter("callee")).thenReturn(callee2);
    when(request.getParameter("startdate")).thenReturn(sdate2);
    when(request.getParameter("starttime")).thenReturn(stime2);
    when(request.getParameter("startmarker")).thenReturn(smarker2);
    when(request.getParameter("enddate")).thenReturn(edate2);
    when(request.getParameter("endtime")).thenReturn(etime2);
    when(request.getParameter("endmarker")).thenReturn(emarker2);
    when(request.getParameter("print")).thenReturn(print);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);
    verify(pw).println(Messages.phoneCallAdded(call2, print));
    verify(response).setStatus(HttpServletResponse.SC_OK);

  }
  @Test
  public void SearchPhonecalls() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();
    addPhoneCall(servlet,1);
    addPhoneCall(servlet,2);
    HttpServletRequest searchRequest = mock(HttpServletRequest.class);
    when(searchRequest.getParameter("customer")).thenReturn(name);
    when(searchRequest.getParameter("startdate")).thenReturn(sdate);
    when(searchRequest.getParameter("starttime")).thenReturn(stime);
    when(searchRequest.getParameter("startmarker")).thenReturn(smarker);
    when(searchRequest.getParameter("enddate")).thenReturn(edate);
    when(searchRequest.getParameter("endtime")).thenReturn(etime);
    when(searchRequest.getParameter("endmarker")).thenReturn(emarker);
    when(searchRequest.getParameter("search")).thenReturn(search);
    HttpServletResponse searchResponse = mock(HttpServletResponse.class);
    PrintWriter searchPW = mock(PrintWriter.class);
    when(searchResponse.getWriter()).thenReturn(searchPW);

    PhoneBill phoneBill = new PhoneBill(name,call1);
    servlet.doGet(searchRequest,searchResponse);
    verify(searchPW).println(phoneBill.searchPhoneCalls(new Date(sdate+" "+stime+" "+smarker),new Date(edate+" "+etime+" "+emarker)));
    verify(searchResponse).setStatus(HttpServletResponse.SC_OK);


    phoneBill.addPhoneCall(call2);
    HttpServletRequest searchRequest2 = mock(HttpServletRequest.class);
    when(searchRequest2.getParameter("customer")).thenReturn(name);
    when(searchRequest2.getParameter("startdate")).thenReturn(sdate);
    when(searchRequest2.getParameter("starttime")).thenReturn(stime);
    when(searchRequest2.getParameter("startmarker")).thenReturn(smarker);
    when(searchRequest2.getParameter("enddate")).thenReturn(edate2);
    when(searchRequest2.getParameter("endtime")).thenReturn(etime2);
    when(searchRequest2.getParameter("endmarker")).thenReturn(emarker2);
    when(searchRequest2.getParameter("search")).thenReturn(search);
    HttpServletResponse searchResponse2 = mock(HttpServletResponse.class);
    PrintWriter searchPW2 = mock(PrintWriter.class);
    when(searchResponse2.getWriter()).thenReturn(searchPW2);

    servlet.doGet(searchRequest2,searchResponse2);
    verify(searchPW2).println(phoneBill.searchPhoneCalls(new Date(sdate+" "+stime+" "+smarker),new Date(edate2+" "+etime2+" "+emarker2)));
    verify(searchResponse2).setStatus(HttpServletResponse.SC_OK);
  }
  @Test
  public void DisplayPretty() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();
    addPhoneCall(servlet,1);
    addPhoneCall(servlet,2);
    var name = "Zack";
    String printall = "printall";
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(name);
    when(request.getParameter("printall")).thenReturn(printall);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(pw);

    PrettyPrinter prettyPrinter = new PrettyPrinter();
    PhoneBill phoneBill = new PhoneBill(name,call1);
    phoneBill.addPhoneCall(call2);

    servlet.doGet(request, response);
    verify(pw).println(prettyPrinter.prettifyPhoneBill(phoneBill));
    verify(response).setStatus(HttpServletResponse.SC_OK);

  }
  private void addPhoneCall(PhoneBillServlet servlet, int call) throws IOException, ServletException {

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(name);
    when(request.getParameter("caller")).thenReturn(call == 1?caller:caller2);
    when(request.getParameter("callee")).thenReturn(call == 1?callee:callee2);
    when(request.getParameter("startdate")).thenReturn(call == 1?sdate:sdate2);
    when(request.getParameter("starttime")).thenReturn(call == 1?stime:stime2);
    when(request.getParameter("startmarker")).thenReturn(call == 1?smarker:smarker2);
    when(request.getParameter("enddate")).thenReturn(call == 1?edate:edate2);
    when(request.getParameter("endtime")).thenReturn(call == 1?etime:etime2);
    when(request.getParameter("endmarker")).thenReturn(call == 1?emarker:emarker2);
    when(request.getParameter("print")).thenReturn(noprint);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);
    verify(pw).println(Messages.phoneCallAdded(call2,noprint));
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }
}
