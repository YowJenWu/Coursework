package android.example.coursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MultiRow_ListAdapter extends ArrayAdapter<Event> {
        private LayoutInflater mInflater;
        private ArrayList<Event> events;
        private int mViewResourceId;

        public MultiRow_ListAdapter(Context context, int textViewResourceId, ArrayList<Event> events) {
            super(context, textViewResourceId, events);
            this.events = events;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mViewResourceId = textViewResourceId;
        }
        public View getView(int position, View convertView, ViewGroup parents) {
            convertView = mInflater.inflate(mViewResourceId, null);
            Event event = events.get(position);
            if(event != null) {
                TextView title= (TextView) convertView.findViewById(R.id.titleTextID);
                TextView date= (TextView) convertView.findViewById(R.id.dateTextID);
                TextView time= (TextView) convertView.findViewById(R.id.timeTextID);
                TextView location= (TextView) convertView.findViewById(R.id.locationTextID);
                TextView content= (TextView) convertView.findViewById(R.id.contentTextID);
                if(title != null) {
                    title.setText(("Event: " + event.getTitle()));
                }
                if(date != null) {
                    date.setText(("Date: " + event.getDate()));
                }
                if(time != null) {
                    time.setText(("Time: " + event.getTime()));
                }
                if(location != null) {
                    location.setText(("Location: " + event.getLocation()));
                }
                if(content != null) {
                    content.setText(("Content: " + event.getContent()));
                }
            }
            return convertView;
        }
}
