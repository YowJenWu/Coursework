package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class StudentProfile extends AppCompatActivity {
    GlobalVariable gv;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        gv = (GlobalVariable)getApplicationContext();
        myDB = new DatabaseHelper(this);

        TextView year_class = (TextView) findViewById(R.id.YearClass);
        TextView student_name = (TextView) findViewById(R.id.StudentName);
        TextView student_account = (TextView) findViewById(R.id.Account);
        TextView parent_number = (TextView) findViewById(R.id.ParentNumber);

        Cursor regis_stu_data = myDB.getRegisStuContents();
        while(regis_stu_data.moveToNext()) {
            String stu_username = regis_stu_data.getString(1);
            String stu_FirstName = regis_stu_data.getString(3);
            String stu_LastName = regis_stu_data.getString(4);
            String stu_Year = regis_stu_data.getString(5);
            String stu_Class = regis_stu_data.getString(6);
            String stu_ParentNum = regis_stu_data.getString(7);
            if(gv.getIdentity().equals("Student")) {
                if (gv.getUsername().equals(stu_username)) {
                    //Set the profile data to the login user
                    year_class.setText("Year: " + stu_Year + " / Class: " + stu_Class);
                    student_name.setText(stu_FirstName + " " + stu_LastName);
                    student_account.setText("User ID: " + stu_username);
                    parent_number.setText("Parent's Number: " + stu_ParentNum);
                } else {
                    Toast.makeText(this, "Error: Missing Student Data", Toast.LENGTH_LONG).show();
                }
            }else if(gv.getIdentity().equals("Parent")){
                if (gv.getUsername().equals(stu_ParentNum)) {
                    //Set the profile data to the login user
                    year_class.setText("Year: " + stu_Year + " / Class: " + stu_Class);
                    student_name.setText(stu_FirstName + " " + stu_LastName);
                    student_account.setText("User ID: " + stu_username);
                    parent_number.setText("Parent's Number: " + stu_ParentNum);
                } else {
                    Toast.makeText(this, "Error: Missing Student Data", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
