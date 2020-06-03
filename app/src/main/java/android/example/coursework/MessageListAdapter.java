package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageListAdapter extends ArrayAdapter<MyMessage> {
    private final Activity context;
    private ArrayList<MyMessage> msgList;

    //Constructor
    public MessageListAdapter(Activity context, ArrayList<MyMessage> msgList){
//        super(context,R.layout.notification_item , titleArrayParam);
        super(context,R.layout.activity_message_list_adapter , msgList);
        this.context = context;
        this.msgList = msgList;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_message_list_adapter, null,true);

        MyMessage msg = msgList.get(position);
        if (msg != null){
            //this code gets references to objects in the notification_item.xml file
            TextView MsgTextField = (TextView) rowView.findViewById(R.id.MessageContent);
            MsgTextField.setText(msg.getMsg_content());
        }
        return rowView;
    };
}
