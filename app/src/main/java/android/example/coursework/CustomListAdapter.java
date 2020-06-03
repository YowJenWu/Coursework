package android.example.coursework;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter {
    //Add new variables that are properties of this Class:
    //to reference the Activity
    private final Activity context;
    //to store the list of titles
    List<String> titleArray = new ArrayList<String>();
    List<String> dateArray = new ArrayList<String>();
    List<String> timeArray = new ArrayList<String>();
    List<String> locationArray = new ArrayList<String>();
    List<String> contentArray = new ArrayList<String>();
//    private final String[] titleArray;
//    private final String[] dateArray;
//    private final String[] timeArray;
//    private final String[] locationArray;
//    private final String[] contentArray;
    //Assign and store the data into each array
    public CustomListAdapter(MainActivity context, List<String> titleArrayParam, List<String> dateArrayParam, List<String> timeArrayParam, List<String> locationArrayParam, List<String> contentArrayParam) {
        super(context, R.layout.event_listview, titleArrayParam);
        this.context = context;
        this.titleArray = titleArrayParam;
        this.dateArray = dateArrayParam;
        this.timeArray = timeArrayParam;
        this.locationArray = locationArrayParam;
        this.contentArray = contentArrayParam;
    }

    //This method is used automatically by the app to populate the data into each row.
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //maps the data from the properties (titleArray etc) to the write fields in the event_listview.xml design.
        View rowView = inflater.inflate(R.layout.event_listview, null, true);
        //This code gets references to objects in the event_listview.xml file
        TextView titleTextField = (TextView) rowView.findViewById(R.id.titleTextID);
        TextView dateTextField = (TextView) rowView.findViewById(R.id.dateTextID);
        TextView timeTextField = (TextView) rowView.findViewById(R.id.timeTextID);
        TextView locationTextField = (TextView) rowView.findViewById(R.id.locationTextID);
        TextView contentTextField = (TextView) rowView.findViewById(R.id.contentTextID);
        //this code sets the values of the objects to values from the arrays
        titleTextField.setText(titleArray.get(position));
        dateTextField.setText(dateArray.get(position));
        timeTextField.setText(timeArray.get(position));
        locationTextField.setText(locationArray.get(position));
        contentTextField.setText(contentArray.get(position));
//        dateTextField.setText(dateArray[position]);
//        timeTextField.setText(timeArray[position]);
//        locationTextField.setText(locationArray[position]);
//        contentTextField.setText(contentArray[position]);
        return rowView;
    }
}
