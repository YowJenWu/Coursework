package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class NotificationList extends AppCompatActivity{

    ListView listView;
    DatabaseHelper myDB;
    ArrayList<MyNotification> notiList;
    MyNotification noti;

    String title1 = null;
    String content1 = null;
    String date1 = null;
    Bitmap bit1 = null;

    String[] titleArray = {};

    String[] contentArray = {};

    String[] dateArray = {};

    Bitmap[] imageArray = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        myDB = new DatabaseHelper(this);
        notiList = new ArrayList<>();
        Cursor data = myDB.getListContent();
        int numRows = data.getCount();
        if(numRows != 0){
            while(data.moveToNext()){
                noti = new MyNotification(data.getString(1), data.getString(2), data.getString(3), data.getBlob(4));
//                Log.d("SHOW_PoS", String.valueOf(data.getInt(0)));
                notiList.add(noti);
            }
        }

        FloatingActionButton fab = findViewById(R.id.add_fab);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title1 = bundle.getString("title", null);
            content1 = bundle.getString("message", null);
            bit1 = bundle.getParcelable("imagebitmap");
            date1 = bundle.getString("date", null);
//            Log.d("SHOW", uri1.getPath());
        }

        titleArray = push(titleArray, title1);
        contentArray = push(contentArray, content1);
        imageArray = push(imageArray, bit1);
        dateArray = push(dateArray, date1);

//        NotificationListAdapter whatever = new NotificationListAdapter(this, titleArray, contentArray, imageArray);
        NotificationListAdapter whatever = new NotificationListAdapter(this, notiList);
        listView = (ListView) findViewById(R.id.ListView1);
        listView.setAdapter(whatever);

        //listens for clicks on rows in the ListView, and provides the position of which row was clicked
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NotificationList.this, NotificationDetailDisplay.class);

                MyNotification n = notiList.get(position);

                Bundle bag = new Bundle();
                bag.putByteArray("getimagebit", n.getImage());
                bag.putString("gettitle", n.getTitle());
                bag.putString("getmessage", n.getContent());
                bag.putString("getdate", n.getDate());
                bag.putInt("position", position);

                intent.putExtras(bag);
                startActivity(intent);
            }
        });

        //Setting the FloatingActionButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationList.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private static String[] push(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

    private static Bitmap[] push(Bitmap[] array, Bitmap push) {
        Bitmap[] longer = new Bitmap[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

}
