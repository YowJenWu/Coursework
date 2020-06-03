package android.example.coursework;

public class Event {
    private String nTitle;
    private String nDate;
    private String nTime;
    private String nLocation;
    private String nContent;

    public Event(String title, String date, String time, String location, String content) {
        nTitle = title;
        nDate = date;
        nTime = time;
        nLocation = location;
        nContent = content;
    }

    public String getTitle() {
        return nTitle;
    }

    public String getDate() {
        return nDate;
    }

    public String getTime() {
        return nTime;
    }

    public String getLocation() {
        return nLocation;
    }

    public String getContent() {
        return nContent;
    }
}
