package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity implements OnClickListener {

    private TextView notif_cre_tit, notif_cre_date, notif_cre_img, notif_cre_content;
    private EditText notif_cre_tit_inp, notif_cre_date_inp, notif_cre_content_inp;
    private Button button_upload_image, button_submit;
    private ImageView preview_upload_image;

    private String imagepath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_create);

        //Connect variables to the layout
        notif_cre_tit  = (TextView)findViewById(R.id.notif_cre_tit);
        notif_cre_date = (TextView)findViewById(R.id.notif_cre_date);
        notif_cre_img = (TextView)findViewById(R.id.notif_cre_img);
        notif_cre_content = (TextView)findViewById(R.id.notif_cre_content);

        preview_upload_image = (ImageView)findViewById(R.id.preview_upload_image);

        button_upload_image = (Button)findViewById(R.id.button_upload_image);
        button_submit = (Button)findViewById(R.id.button_submit);

        //Setting onClickListener for buttons
        button_upload_image.setOnClickListener(this);
        button_submit.setOnClickListener(this);

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

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Actions to do after user picked an image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data!= null) {
            Uri selectedImageUri = data.getData();

            imagepath = getPath(selectedImageUri);
            Log.d("PATH", selectedImageUri.getPath());
            Log.d("PATH2", imagepath);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            //依相片的路徑，轉成Bitmap的型態，在ImageView，顯示出選取的相片。
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imagepath));
                //bitmap = Bitmap.createScaledBitmap(bitmap, 100, 80, true);
                preview_upload_image.setImageBitmap(bitmap);
                if (bitmap != null) {
                    preview_upload_image = (ImageView) findViewById(R.id.preview_upload_image);
                }
                else{
                    Log.d("PATH", "Fail_To_Get_Image!!!");
                }
            }catch(IOException ie) {
                Log.d("PATH", ie);
            }
        }
        else{

        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(projection[0]);
        String picturePath = cursor.getString(column_index);
        cursor.close();
        return picturePath;
    }

}
