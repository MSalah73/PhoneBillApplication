package edu.pdx.cs410J.ms24;

public class ArgsParser {
    public ArgsParser() {

    }

    public String[] argsParser(String[] args){
        return args;
    }
    public boolean parserPhoneNumber(String phoneNumber){
        if(phoneNumber.matches(phoneFormat))
            return true;
        throw new IllegalArgumentException("Phone number must in this format XXX-XXX-XXXX");
    }
    public boolean parserTime(String time){
        if(time.matches(timeFormat))
            return true;
        throw new IllegalArgumentException("Time must be in this format XX:XX where the first XX is between 00 to 23 and the second XX is between 00 to 59");
    }
    public boolean parserDate(String date){
        if(date.matches(dateFormat))
            return true;
        throw new IllegalArgumentException("Date must be in this format XX/XX/XXXX");
    }
    private String phoneFormat = "^\\d{3}-\\d{3}-\\d{4}$";
    private String timeFormat = "^((00)|(0?[1-9])|(1\\d)|(2[0-3])):[0-5]\\d$";
    private String dateFormat = "^(((0?[13578]|1[02])/(0?[1-9]|[12]\\d|3[01]))|((0?[469]|11)/(0?[1-9]|[12]\\d|30))|(0?2/(0?[1-9]|1\\d|2[0-8])))/\\d{4}$";

}
