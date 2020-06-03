package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MessageReceive extends AppCompatActivity {

    ListView listView;
    DatabaseHelper myDB;
    ArrayList<MyMessage> msgList;
    MyMessage msg;
    GlobalVariable gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_receive);
        listView = (ListView) findViewById(R.id.ListView_msg);
        gv = (GlobalVariable)getApplicationContext();

        myDB = new DatabaseHelper(this);
        msgList = new ArrayList<>();
        Cursor msg_data = myDB.getListContent_msg();

        int numRows = msg_data.getCount();
        if(numRows != 0){
            while(msg_data.moveToNext()){
                msg = new MyMessage(msg_data.getString(1), msg_data.getString(2), msg_data.getString(3), msg_data.getString(4));
                //Filter the message which sent to me
                //if identify is Student{
                if (msg_data.getString(3).equals(gv.getUsername()) ) {
                    msgList.add(msg);
                }
            }
        }

        MessageListAdapter whatever = new MessageListAdapter(this, msgList);
        listView.setAdapter(whatever);
    }
}
