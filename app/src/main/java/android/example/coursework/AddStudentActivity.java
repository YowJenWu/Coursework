package android.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class AddStudentActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_add_info);
        //Action Bar Layout
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.student_add_action_bar);

        myDB = new DatabaseHelper(this);

        Button add_student_btn = (Button) findViewById(R.id.add_student_btn);
        add_student_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Add Register detail into StudentList
                addToRegisStuList();
            }
        });
    }

    public void addToRegisStuList() {
        Bundle info_from_main = getIntent().getExtras();
        String identity = info_from_main.getString("IDENTITY");
        String username = info_from_main.getString("USERNAME");
        String password = info_from_main.getString("PASSWORD");

        //Get the EditText Content in Adding Page
        EditText editFN = (EditText) findViewById(R.id.first_name_txt);
        String firstname = editFN.getText().toString();
        EditText editLN = (EditText) findViewById(R.id.last_name_txt);
        String lastname = editLN.getText().toString();
        EditText editYear = (EditText) findViewById(R.id.year_txt);
        String year = editYear.getText().toString();
        EditText editClass = (EditText) findViewById(R.id.class_txt);
        String class_val = editClass.getText().toString();
        EditText editParentNum = (EditText) findViewById(R.id.parent_phone_txt);
        String parent_num = editParentNum.getText().toString();

        if(username.length() != 0 && password.length() != 0 && firstname.length() != 0 && lastname.length() != 0 && year.length() != 0 && class_val.length() != 0 && parent_num.length() != 0) {
            boolean insertData = myDB.addRegisStuData(username, password, firstname, lastname, year, class_val, parent_num);
            if(insertData == true) {
                Toast.makeText(AddStudentActivity.this, "Successfully Register!", Toast.LENGTH_LONG).show();
                myDB.updateStuList(1, username);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AddStudentActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AddStudentActivity.this, "You must put something in each text.", Toast.LENGTH_LONG).show();
        }

    }

    public void back_action(View v) {
        Intent backToHomepage = new Intent(AddStudentActivity.this, MainActivity.class);
        startActivity(backToHomepage);
    }
}
