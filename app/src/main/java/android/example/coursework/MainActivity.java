package android.example.coursework;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText mUsername;
    EditText mPassword;
    Button mLoginBtn;
    TextView mTextViewRegister;
    public String identity;
    DatabaseHelper myDB;
    GlobalVariable gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = (EditText)findViewById(R.id.username_txt);
        mPassword = (EditText)findViewById(R.id.pswd_txt);
        mLoginBtn = (Button)findViewById(R.id.login_btn);
        mTextViewRegister = (TextView)findViewById(R.id.register_btn);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.identityGroup);
        myDB = new DatabaseHelper(this);
        gv = (GlobalVariable)getApplicationContext();

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Get the checked Radio Button ID from Radio Group
                int selectedRadioButtonID = rg.getCheckedRadioButtonId();
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {
                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    identity = selectedRadioButton.getText().toString();
                }
                if (username.equals("admin") && password.equals("admin_pswd") && identity.equals("Admin")) {
                    //After login success, go to dashboard
                    Intent intent = new Intent(MainActivity.this, Homepage.class);
                    gv.setIdentity("Admin");
                    gv.setUsername(username);
//                    Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
                    intent.putExtra("IDENTITY", identity);
                    startActivity(intent);
                } else if (identity.equals("Student")) {
                    Cursor regis_stu_data = myDB.getRegisStuContents();
                    if(regis_stu_data.getCount() == 0) {
                        Toast.makeText(MainActivity.this, "Please registered first!", Toast.LENGTH_LONG).show();
                    } else {
                        while(regis_stu_data.moveToNext()) {
                            String stu_username = regis_stu_data.getString(1);
                            String stu_password = regis_stu_data.getString(2);
                            if(username.equals(stu_username) && password.equals(stu_password)){
                                //After login success, go to dashboard
                                Intent studentRegister = new Intent(MainActivity.this, Homepage.class);
//                                Intent studentRegister = new Intent(MainActivity.this, AddStudentActivity.class);
                                gv.setIdentity(identity);
                                gv.setUsername(username);
                                studentRegister.putExtra("IDENTITY", identity);
                                studentRegister.putExtra("USERNAME", username);
                                startActivity(studentRegister);
                                break;
                            } else {
                                Toast.makeText(MainActivity.this, "Your username or password is incorrect!", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                } else if (identity.equals("Parent")) {
                    Cursor parent_data = myDB.getParentContents();
                    if(parent_data.getCount() == 0) {
                        Toast.makeText(MainActivity.this, "Please registered first!", Toast.LENGTH_LONG).show();
                    } else {
                        while(parent_data.moveToNext()) {
                            String par_username = Integer.toString(parent_data.getInt(0));
                            if (username.substring(0,1).equals("0")) {
                                par_username = "0" + par_username;
                            }
                            String par_password = parent_data.getString(1);
                            if(username.equals(par_username) && password.equals(par_password)) {
                                //After login success, go to dashboard
                                Intent parentLogin = new Intent(MainActivity.this, Homepage.class);
                                gv.setIdentity(identity);
                                gv.setUsername(username);
//                                Intent parentLogin = new Intent(MainActivity.this, AddStudentActivity.class);
                                parentLogin.putExtra("IDENTITY", identity);
                                parentLogin.putExtra("USERNAME", username);
                                startActivity(parentLogin);
                                break;
                            } else {
                                Toast.makeText(MainActivity.this, "Your username or password is incorrect!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }

            }
        });

    }

    public void register_action(View v) {
        mUsername = (EditText)findViewById(R.id.username_txt);
        mPassword = (EditText)findViewById(R.id.pswd_txt);
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        final RadioGroup rg = (RadioGroup) findViewById(R.id.identityGroup);
        int selectedRadioButtonID = rg.getCheckedRadioButtonId();
        // If nothing is selected from Radio Group, then it return -1
        if (selectedRadioButtonID != -1) {
            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
            identity = selectedRadioButton.getText().toString();
        }
        if (identity.equals("Student")) { //Student Register
            Cursor data = myDB.getStuListContents();
            if(data.getCount() == 0) {
                Toast.makeText(MainActivity.this, "Please contact Admin!", Toast.LENGTH_LONG).show();
            } else {
                while(data.moveToNext()) {
                    String stu_username = data.getString(1);
                    if(username.equals(stu_username)){
                        Intent studentRegister = new Intent(MainActivity.this, AddStudentActivity.class);
                        studentRegister.putExtra("IDENTITY", identity);
                        studentRegister.putExtra("USERNAME", username);
                        studentRegister.putExtra("PASSWORD", password);
                        startActivity(studentRegister);
                        break;
                    }
                }
            }

        } else if (identity.equals("Parent")) { //Parent Register
            Cursor data = myDB.getRegisStuContents();
            if(data.getCount() == 0) {
                Toast.makeText(MainActivity.this, "Please contact Admin!", Toast.LENGTH_LONG).show();
            } else {
                while(data.moveToNext()) {
                    String parent_number = data.getString(7);
                    if(username.equals(parent_number)){
                        if(username.length() != 0 && password.length() != 0) {
                            int phone = 0;
                            try {
                                phone = Integer.parseInt(username);
                            } catch (NumberFormatException e) {
                                Toast.makeText(MainActivity.this, "Please contact Admin!", Toast.LENGTH_LONG).show();
                            }
                            boolean insertData = myDB.addParentData(phone, password);
                            if(insertData == true) {
                                Toast.makeText(MainActivity.this, "Successfully Register! You can login now.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                            }
                            break;
                        }
                    }
                }
            }

        }


    }
}
