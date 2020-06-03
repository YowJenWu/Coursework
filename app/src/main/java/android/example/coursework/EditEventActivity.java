package android.example.coursework;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditEventActivity extends AppCompatActivity {
    private static String TAG = "EditEventActivity";
    private Button btnEditSave;
    private EditText getEdit_title, getEdit_date, getEdit_time, getEdit_location, getEdit_content;
    DatabaseHelper myDB;
    GlobalVariable gv;

    private String selectedName;
    private int selectedID;
    private String selectedTitle, selectedDate, selectedTime, selectedLocation, selectedContent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event_layout);
        btnEditSave = (Button) findViewById(R.id.btnEditSave);
        //tide the edit text id
        getEdit_title = (EditText) findViewById(R.id.editText);
        getEdit_date = (EditText) findViewById(R.id.editText3);
        getEdit_time = (EditText) findViewById(R.id.editText2);
        getEdit_location = (EditText) findViewById(R.id.editText4);
        getEdit_content = (EditText) findViewById(R.id.editText6);

        myDB = new DatabaseHelper(this);
        gv = (GlobalVariable)getApplicationContext();
        //get the intent extra from the EventMainActivity
        Intent receivedIntent = getIntent();
        //get all items we passed as an extra from EventMainActivity
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedTitle = receivedIntent.getStringExtra("title");
        selectedDate = receivedIntent.getStringExtra("date");
        selectedTime = receivedIntent.getStringExtra("time");
        selectedLocation = receivedIntent.getStringExtra("location");
        selectedContent = receivedIntent.getStringExtra("content");
        //set the text to show the current selected data
        getEdit_title.setText(selectedTitle);
        getEdit_date.setText(selectedDate);
        getEdit_time.setText(selectedTime);
        getEdit_location.setText(selectedLocation);
        getEdit_content.setText(selectedContent);
        btnEditSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(gv.getIdentity().equals("Admin")){
                    String title = getEdit_title.getText().toString();
                    String date = getEdit_date.getText().toString();
                    String time = getEdit_time.getText().toString();
                    String location = getEdit_location.getText().toString();
                    String content = getEdit_content.getText().toString();
                    if (title.length() != 0 && date.length() != 0 && time.length() != 0 && location.length() != 0 && content.length() != 0) {
                        myDB.updateEventList(selectedID, title, date, time, location, content);
                        Toast.makeText(EditEventActivity.this, "Successfully Edit the Event!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditEventActivity.this, EventMainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditEventActivity.this, "You must put something in each text.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}

