package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private TextView notif_cre_tit, notif_cre_date, notif_cre_img, notif_cre_content;
    private EditText notif_cre_tit_inp, notif_cre_date_inp, notif_cre_content_inp;
    private Button button_upload_image, button_submit;
    private ImageView preview_upload_image;

    private Uri PassingUri = null;
    private Bitmap PassingBitmap;
    public static final String CHANNEL_ID = "channel1";
    private int notificationId = 1;

    DatabaseHelper myDB;

    //This notificationManagerCompat actually shows the notification, but cannot create notificaitonChannel
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_create);

        //Connect variables to the layout
        notif_cre_tit  = (TextView)findViewById(R.id.notif_cre_tit);
        notif_cre_date = (TextView)findViewById(R.id.notif_cre_date);
        notif_cre_img = (TextView)findViewById(R.id.notif_cre_img);
        notif_cre_content = (TextView)findViewById(R.id.notif_cre_content);

        notif_cre_tit_inp = (EditText)findViewById(R.id.notif_cre_tit_inp);
        notif_cre_date_inp = (EditText)findViewById(R.id.notif_cre_date_inp);
        notif_cre_content_inp = (EditText)findViewById(R.id.notif_cre_content_inp);

        preview_upload_image = (ImageView)findViewById(R.id.preview_upload_image);

        button_upload_image = (Button)findViewById(R.id.button_upload_image);
        button_submit = (Button)findViewById(R.id.button_submit);

        //Setting onClickListener for buttons
        button_upload_image.setOnClickListener(this);
        button_submit.setOnClickListener(this);

        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);

//        myDB.resetDB(this);
        myDB = new DatabaseHelper(this);


    }

    // Create the NotificationChannel with unique ID: CHANNEL_ID
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            channel1.setDescription(description);

            // Submit the notification channel object to the notification manager
            NotificationManager nm = (NotificationManager) getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel1);
        }
    }

    @Override
    public void onClick(View v) {
        //If user clicks the choosefile botton
        if (v == button_upload_image){
            //Triggered the photos on the phone to choose the image to upload
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            //Use Override onActivityResult to process the result after image uploading。
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
        }

        //If user clicks the post botton
        else if (v == button_submit){
            String NotificationTitle = notif_cre_tit_inp.getText().toString();
            String NotificationMessage = notif_cre_content_inp.getText().toString();
            String NotificationDate = notif_cre_date_inp.getText().toString();
            byte[] NotificationImage = getBytes(PassingBitmap);

            //Add data to database if data is not null
            if(NotificationTitle.length() != 0 && NotificationMessage.length() != 0 && NotificationDate.length() !=0){
                addDataToDB(NotificationTitle, NotificationDate, NotificationMessage, NotificationImage);
            }
            else{
                Toast.makeText(MainActivity.this, "ERROR: There are empty field", Toast.LENGTH_LONG).show();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                //Set the notification's tap action
                Intent intentclicking = new Intent(this, NotificationDetailDisplay.class);
                intentclicking.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                //Send data to Notification display too
                Bundle passingbag = new Bundle();
                passingbag.putByteArray("getimagebit", NotificationImage);
                passingbag.putString("gettitle", NotificationTitle);
                passingbag.putString("getmessage", NotificationMessage);
                passingbag.putString("getdate", NotificationDate);
                intentclicking.putExtras(passingbag);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentclicking, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        //Set our APP's icon as small icon
                        .setSmallIcon(R.drawable.ic_face)
                        .setContentTitle(NotificationTitle)
                        .setContentText(NotificationMessage)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setContentIntent(pendingIntent)
                        .build();

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(notificationId, builder);
            }

            //Direct to the NotificationList Page
            Intent intent = new Intent();
            intent.setClass(MainActivity.this  , NotificationList.class);

            //put image, title and content together and wrap as a package
            Bundle bag = new Bundle();
            bag.putParcelable("imagebitmap", PassingBitmap);
            bag.putString("title", NotificationTitle);
            bag.putString("message", NotificationMessage);
            bag.putString("date", NotificationDate);

            intent.putExtras(bag);
            startActivity(intent);

        }
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public void addDataToDB(String tTitle, String dDate, String cContent, byte[] iImage){
        boolean insertData = myDB.addData(tTitle, dDate, cContent, iImage);
        if(insertData == true){
            Toast.makeText(MainActivity.this, "Successfully insert data", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Failed to insert data to db", Toast.LENGTH_LONG).show();
        }
    }
    //Define the action after clicking the button "choose Image"
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Actions to do after user picked an image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data!= null) {
            Uri selectedImageUri = data.getData();

            Log.d("PATH", selectedImageUri.getPath());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
//                PassingUri = selectedImageUri;
                PassingBitmap = getResizedBitmap(bitmap, 100);;
                if (bitmap != null) {
//                    preview_upload_image = (ImageView) findViewById(R.id.preview_upload_image);
                    preview_upload_image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));
                }
                else{
                    Log.d("PATH", "Fail_To_Get_Image!!!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}