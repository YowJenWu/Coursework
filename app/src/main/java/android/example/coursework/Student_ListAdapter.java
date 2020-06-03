package android.example.coursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Student_ListAdapter extends ArrayAdapter<Student_1> {
    private LayoutInflater mInflater;
    private ArrayList<Student_1> students;
    private int mViewResourceId;
    public Student_ListAdapter(@NonNull Context context, int textViewResourceId, ArrayList<Student_1> students) {
        super(context, textViewResourceId, students);
        this.students = students;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {
        convertView = mInflater.inflate(mViewResourceId, null);
        Student_1 student_1 = students.get(position);
        if(student_1 != null) {
            TextView username = (TextView) convertView.findViewById(R.id.userTextID);
            TextView flag = (TextView) convertView.findViewById(R.id.flagTextID);
            if(username != null) {
                username.setText(("Username: " + student_1.getTitle()));
            }
            if(flag != null) {
                String register_status;
                if ((student_1.getFlag()) != 1) {
                    register_status = "False";
                } else {
                    register_status = "True";
                }
                flag.setText(("Registered Status: " + register_status));
            }
        }
        return convertView;
    }

}
