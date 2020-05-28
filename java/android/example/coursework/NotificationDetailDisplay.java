package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class NotificationDetailDisplay extends AppCompatActivity implements OnClickListener {

    DatabaseHelper myDB;

    private Button button_edit, button_delete;
    private Integer position = 0;
    private String savedTitle, savedDate, savedContent;
    private byte[] savedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail_display);

        button_edit = (Button)findViewById(R.id.button_edit);
        button_delete = (Button)findViewById(R.id.button_delete);
        button_edit.setOnClickListener(this);
        button_delete.setOnClickListener(this);

        myDB = new DatabaseHelper(this);
//        notiList = new ArrayList<>();
//        Cursor data = myDB.getListContent();
//        int numRows = data.getCount();

        //Get data from intent
        savedTitle = getIntent().getExtras().getString("gettitle", null);
        savedDate = getIntent().getExtras().getString("getdate", null);
        savedContent = getIntent().getExtras().getString("getmessage", null);
        savedImage = getIntent().getExtras().getByteArray("getimagebit");
        position = getIntent().getExtras().getInt("position", 0);
        Log.d("SHOW_PoS", position.toString());

        TextView myTitle = (TextView) findViewById(R.id.display_title);
        TextView myDate = (TextView) findViewById(R.id.display_date);
        TextView myContent = (TextView) findViewById(R.id.display_content);
        ImageView myImage = (ImageView) findViewById(R.id.display_image);

        //Set TextView as data
        myTitle.setText(savedTitle);
        myDate.setText(savedDate);
        myContent.setText(savedContent);
        Bitmap b = getImageFromBytes(savedImage);
        myImage.setImageBitmap(Bitmap.createScaledBitmap(b, 100, 100, false));

    }
    public static Bitmap getImageFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public void onClick(View v) {
        if (v == button_edit){
            //Redirect to the notification edit page
            Intent intent = new Intent(NotificationDetailDisplay.this  , NotificationEdit.class);

            Bundle bag = new Bundle();
            bag.putByteArray("getimagebit", savedImage);
            bag.putString("gettitle", savedTitle);
            bag.putString("getmessage", savedContent);
            bag.putString("getdate", savedDate);
            bag.putInt("position", position);

            intent.putExtras(bag);
            startActivity(intent);
        }
        else if(v == button_delete){
            //Delete the item
            myDB.deleteDB(savedTitle);

            //Redirect to the notification list page
            Intent intent = new Intent(NotificationDetailDisplay.this  , NotificationList.class);
            startActivity(intent);
        }
    }
}
