package android.example.coursework;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationListAdapter extends ArrayAdapter<MyNotification> {

    //to reference the Activity
    private final Activity context;
//
//    //to store the images
//    private final Bitmap[] imageIDarray;
//
//    //to store the list of titles
//    private final String[] titleArray;
//
//    //to store the list of contents
//    private final String[] contentArray;

    private ArrayList<MyNotification> notiList;

    //Constructor
//    public NotificationListAdapter(Activity context, String[] titleArrayParam, String[] contentArrayParam, Bitmap[] imageIDArrayParam){
    public NotificationListAdapter(Activity context, ArrayList<MyNotification> notiList){
//        super(context,R.layout.notification_item , titleArrayParam);
        super(context,R.layout.notification_item , notiList);
        this.context = context;

//        this.context=context;
        this.notiList = notiList;
//        this.imageIDarray = imageIDArrayParam;
//        this.titleArray = titleArrayParam;
//        this.contentArray = contentArrayParam;

    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.notification_item, null,true);

        MyNotification noti = notiList.get(position);
        if (noti != null){
            //this code gets references to objects in the notification_item.xml file
            TextView titleTextField = (TextView) rowView.findViewById(R.id.NotificationListTitle);
            TextView contentTextField = (TextView) rowView.findViewById(R.id.NotificationListContent);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.NotificationListIcon);

            if (titleTextField != null){
                titleTextField.setText(noti.getTitle());
            }
            if (contentTextField != null){
                contentTextField.setText(noti.getContent());
            }
            if (imageView != null){
                Bitmap b = getImageFromBytes(noti.getImage());
                imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 100, 100, false));
            }
        }

        //this code sets the values of the objects to values from the arrays
//        titleTextField.setText(titleArray[position]);
//        contentTextField.setText(contentArray[position]);
        //            Uri myuri = Uri.parse(imageIDarray[position]);
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), myuri);
//        imageView.setImageBitmap(Bitmap.createScaledBitmap(imageIDarray[position], 100, 100, false));

        return rowView;

    };
    public static Bitmap getImageFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}




