package android.example.coursework;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminAddStudActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_student);

        myDB = new DatabaseHelper(this);
        Button admin_add_btn = (Button) findViewById(R.id.admin_add_btn);
        admin_add_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Add new student user name into StudentList
                addToStudentList();
            }
        });
    }

    public void addToStudentList() {
        EditText editUsername = (EditText) findViewById(R.id.username_txt);
        String username = editUsername.getText().toString();
        if(username.length() != 0) {
            boolean insertData = myDB.addStuData(username);
            if(insertData == true) {
                Toast.makeText(AdminAddStudActivity.this, "Successfully Save the Event!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, StudentListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AdminAddStudActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AdminAddStudActivity.this, "You must put something in each text.", Toast.LENGTH_LONG).show();
        }

    }
}