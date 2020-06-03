package android.example.coursework;

import android.graphics.Bitmap;

public class MyNotification {
    private String title;
    private String date;
    private String content;
    private byte[] image;

    public MyNotification(String tTitle, String dDate, String cContent, byte[] iImage){
        title = tTitle;
        date = dDate;
        content = cContent;
        image = iImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public byte[] getImage() {
        return image;
    }
}
