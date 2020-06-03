package android.example.coursework;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.coursework.DatabaseHelper;
import android.example.coursework.MainActivity;
import android.example.coursework.R;
import android.icu.text.Collator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddEventActivity extends AppCompatActivity {
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        myDB = new DatabaseHelper(this);
        //Save Event List Click Listener
        Button save_btn = (Button) findViewById(R.id.save_button);
        save_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Add Event detail into EventMainActivity
                addToEventList();
            }
        });
    }
    //Pass event detail into Event Main Page(database)
    public void addToEventList() {
        //Get the EditText Content in Adding Page
        EditText editTitle = (EditText) findViewById(R.id.editText);
        String title = editTitle.getText().toString();

        EditText editDate = (EditText) findViewById(R.id.editText3);
        String date = editDate.getText().toString();

        EditText editTime = (EditText) findViewById(R.id.editText2);
        String time = editTime.getText().toString();

        EditText editLocation = (EditText) findViewById(R.id.editText4);
        String location = editLocation.getText().toString();

        EditText editContent = (EditText) findViewById(R.id.editText6);
        String content = editContent.getText().toString();
        if(title.length() != 0 && date.length() != 0 && time.length() != 0 && location.length() != 0 && content.length() != 0) {
            boolean insertData = myDB.addData_ev(title, date, time, location, content);
            if(insertData == true) {
                Toast.makeText(AddEventActivity.this, "Successfully Save the Event!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, EventMainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AddEventActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AddEventActivity.this, "You must put something in each text.", Toast.LENGTH_LONG).show();
        }


    }

}
