package android.example.coursework;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    ArrayList<Student_1> theStudentList;
    ListView student_list_view;
    Student_1 student_1;
    TextView StudentListBack;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);
        //Action Bar Layout
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.studentlist_action_bar);
        StudentListBack = (TextView)findViewById(R.id.student_list_bar);

        //New add button (Go to AdminAddStuActivity)
        findViewById(R.id.add_student_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminAddUser = new Intent(StudentListActivity.this, AdminAddStudActivity.class);
                startActivity(adminAddUser);
            }
        });
        myDB = new DatabaseHelper(this);
        theStudentList = (ArrayList) new ArrayList<>();
        //Cursor all the data from the database
        Cursor data = myDB.getStuListContents();
        if(data.getCount() == 0) {
            Toast.makeText(StudentListActivity.this, "No student be added at the moment!", Toast.LENGTH_LONG).show();
        } else {
            while(data.moveToNext()){
                //columnIndex = 0 is ID
                student_1 = new Student_1(data.getString(1), data.getInt(2));
                theStudentList.add(student_1);
                //Create the list adapter and set the adapter
                Student_ListAdapter listAdapter = new Student_ListAdapter(this, R.layout.student_listivew_item, theStudentList);
                student_list_view = (ListView) findViewById(R.id.student_list);
                student_list_view.setAdapter(listAdapter);
            }
        }
        //below code should add into Dashboard activity to see the authority
//        Bundle info_from_main = getIntent().getExtras();
//        if(info_from_main != null) {
//            String identity = info_from_main.getString("IDENTITY");
//            if (identity = "Admin") {
        // Add/Edit/Delete Student List/Event/Notification/Communication
//            }
//        }
    }

    public void back_action(View v) {
        Intent backToHomepage = new Intent(StudentListActivity.this, Homepage.class);
        startActivity(backToHomepage);
    }
}
