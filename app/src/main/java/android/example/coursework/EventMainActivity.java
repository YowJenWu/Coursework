package android.example.coursework;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.icu.text.Collator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class EventMainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    GlobalVariable gv;
    ArrayList<Event> theEventList;
    ListView event_list_view;
    Event event;
    private static String TAG = "EventMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //New add button (Go to AddEventActivity)
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gv.getIdentity().equals("Admin")) {
                    Intent addEvent = new Intent(EventMainActivity.this, AddEventActivity.class);
                    startActivity(addEvent);
                }
            }
        });
        //Initialise DatabaseHelper
        myDB = new DatabaseHelper(this);
        gv = (GlobalVariable)getApplicationContext();

        theEventList = (ArrayList) new ArrayList<>();
        //Cursor all the data from the database
        Cursor data = myDB.getListContents();
        if(data.getCount() == 0) {
            Toast.makeText(EventMainActivity.this, "No Event be added at the moment!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                //columnIndex = 0 is ID
                event = new Event(data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5));
                theEventList.add(event);
                //Create the list adapter and set the adapter
                MultiRow_ListAdapter listAdapter = new MultiRow_ListAdapter(this, R.layout.event_listview, theEventList);
                event_list_view = (ListView) findViewById(R.id.event_list);
                event_list_view.setAdapter(listAdapter);
            }

            //set an onItemClickListener to the ListView
            event_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String title = theEventList.get(position).getTitle();
                    String date = theEventList.get(position).getDate();
                    String time = theEventList.get(position).getTime();
                    String location = theEventList.get(position).getLocation();
                    String content = theEventList.get(position).getContent();
                    Log.d(TAG, "onItemClick: You Clicked on " + title);
                    Cursor data = myDB.getItemID(title);
                    int itemID = -1;
                    while(data.moveToNext()){
                        itemID = data.getInt(0);
                    }
                    if(itemID > -1) {
                        Log.d(TAG, "onItemClick: The ID is: " + itemID);
                        Intent editScreenIntent = new Intent(EventMainActivity.this, EditEventActivity.class);
                        editScreenIntent.putExtra("id", itemID);
                        editScreenIntent.putExtra("title", title);
                        editScreenIntent.putExtra("date", date);
                        editScreenIntent.putExtra("time", time);
                        editScreenIntent.putExtra("location", location);
                        editScreenIntent.putExtra("content", content);
                        startActivity(editScreenIntent);
                    } else {
                        Toast.makeText(EventMainActivity.this, "No ID associated with that name.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}
