package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Homepage extends AppCompatActivity implements View.OnClickListener {
    ImageButton imgButton_profile, imgButton_event, imgButton_notification, imgButton_communication, img_Logout;
    GlobalVariable gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        gv = (GlobalVariable)getApplicationContext();
        imgButton_profile = (ImageButton)findViewById(R.id.ib_profile);
        imgButton_event = (ImageButton)findViewById(R.id.ib_event);
        imgButton_notification = (ImageButton)findViewById(R.id.ib_notification);
        imgButton_communication = (ImageButton)findViewById(R.id.ib_mail);
        img_Logout = (ImageButton)findViewById(R.id.logout);
        imgButton_profile.setOnClickListener(this);
        imgButton_event.setOnClickListener(this);
        imgButton_notification.setOnClickListener(this);
        imgButton_communication.setOnClickListener(this);
        img_Logout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == imgButton_profile){
            //Direct to profile page
            if(gv.getIdentity().equals("Admin")){
                Intent intent = new Intent(this, AdminAddStudActivity.class);
                startActivity(intent);
            }else if(gv.getIdentity().equals("Student")) {
                Intent intent = new Intent(this, StudentProfile.class);
                startActivity(intent);
            }else if(gv.getIdentity().equals("Parent")){
                //Parent to view student's profile
                /*TODO*/
            }
        }else if(v == imgButton_event){
            //Direct to event page
            Intent intent = new Intent(this, EventMainActivity.class);
            startActivity(intent);
        }else if(v == imgButton_notification){
            //Direct to notification list page
            Intent intent = new Intent(this, NotificationList.class);
            startActivity(intent);
        }else if(v == imgButton_communication){
            //Direct to communication page
            if(gv.getIdentity().equals("Admin")) {
                Intent intent = new Intent(this, MessageSend.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, MessageReceive.class);
                startActivity(intent);
            }
        }else if(v == img_Logout){
            //Reset User & Identity
            gv.setUsername("");
            gv.setIdentity("");
            //Direct to login page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
