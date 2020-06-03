package android.example.coursework;

import android.app.Application;

public class GlobalVariable extends Application {
    private String identity;
    private String username;

    //set variable
    public void setIdentity(String id){
        this.identity = id;
    }
    public void setUsername(String user){
        this.username = user;
    }

    //取得 變數值
    public String getIdentity() {
        return identity;
    }
    public String getUsername(){
        return username;
    }
}
