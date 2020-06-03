package android.example.coursework;

public class MyMessage {
    private String msg_year;
    private String msg_class;
    private String msg_name;
    private String msg_content;

    public MyMessage(String yYear, String cClass, String nName, String cContent){
        msg_year = yYear;
        msg_class = cClass;
        msg_name = nName;
        msg_content = cContent;
    }

    public String getMsg_year() {
        return msg_year;
    }

    public String getMsg_class() {
        return msg_class;
    }

    public String getMsg_name() {
        return msg_name;
    }

    public String getMsg_content() {
        return msg_content;
    }
}
