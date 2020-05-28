package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NotificationEdit extends AppCompatActivity implements OnClickListener {

    DatabaseHelper myDB;
    private Bitmap PassingBitmap;
    
    private TextView notif_ed_tit, notif_ed_date, notif_ed_img, notif_ed_content;
    private EditText notif_ed_tit_inp, notif_ed_date_inp, notif_ed_content_inp;
    private Button button_upload_image, button_save, button_cancel;
    private ImageView preview_upload_image;
    
    //Catch the data from NotificationDetailDisplay
    private String savedTitle, savedDate, savedContent;
//    private Integer position;
    private byte[] savedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_edit);

        //Connect variables to the layout
        notif_ed_tit  = (TextView)findViewById(R.id.notif_ed_tit);
        notif_ed_date = (TextView)findViewById(R.id.notif_ed_date);
        notif_ed_img = (TextView)findViewById(R.id.notif_ed_img);
        notif_ed_content = (TextView)findViewById(R.id.notif_ed_content);

        notif_ed_tit_inp = (EditText)findViewById(R.id.notif_ed_tit_inp);
        notif_ed_date_inp = (EditText)findViewById(R.id.notif_ed_date_inp);
        notif_ed_content_inp = (EditText)findViewById(R.id.notif_ed_content_inp);

        preview_upload_image = (ImageView)findViewById(R.id.preview_upload_image);

        button_upload_image = (Button)findViewById(R.id.button_upload_image);
        button_save = (Button)findViewById(R.id.button_save);
        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_upload_image.setOnClickListener(this);
        button_save.setOnClickListener(this);
        button_cancel.setOnClickListener(this);

        myDB = new DatabaseHelper(this);

        //Get data from intent
        savedTitle = getIntent().getExtras().getString("gettitle", null);
        savedDate = getIntent().getExtras().getString("getdate", null);
        savedContent = getIntent().getExtras().getString("getmessage", null);
        savedImage = getIntent().getExtras().getByteArray("getimagebit");
//        position = getIntent().getExtras().getInt("position", 0);
        
        //Initial the EditText to the original value
        notif_ed_tit_inp.setText(savedTitle);
        notif_ed_date_inp.setText(savedDate);
        notif_ed_content_inp.setText(savedContent);

        Bitmap b = getImageFromBytes(savedImage);
        PassingBitmap = b;
        preview_upload_image.setImageBitmap(Bitmap.createScaledBitmap(b, 100, 100, false));

    }

    public static Bitmap getImageFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public void onClick(View v) {
        if(v == button_upload_image){
            //Triggered the photos on the phone to choose the image to upload
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            //Use Override onActivityResult to process the result after image uploading。
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
        }
        else if(v == button_save){

            String NotificationTitle = notif_ed_tit_inp.getText().toString();
//            Log.d("UPDATED_POS", position.toString());
            String NotificationMessage = notif_ed_content_inp.getText().toString();
            String NotificationDate = notif_ed_date_inp.getText().toString();
            byte[] NotificationImage = getBytes(PassingBitmap);
            
            boolean updateData = myDB.updateDB(NotificationTitle, NotificationDate, NotificationMessage, NotificationImage, savedTitle);
            if (updateData == true){
                Toast.makeText(NotificationEdit.this, "Successfully update data", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(NotificationEdit.this, "Failed to update data", Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(NotificationEdit.this  , NotificationList.class);
            startActivity(intent);
        }
        else if(v == button_save){
            Intent intent = new Intent(NotificationEdit.this  , NotificationList.class);
            startActivity(intent);
        }

    }

    //Define the action after clicking the button "choose Image"
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Actions to do after user picked an image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data!= null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                PassingBitmap = getResizedBitmap(bitmap, 100);;
                if (bitmap != null) {
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

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
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
