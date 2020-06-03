package android.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.Arrays;

public class MessageSend extends AppCompatActivity implements OnClickListener {
    EditText msg_cre_content_inp;
    String selected_year, selected_class, selected_name, selected_user, selected_parent;
    RadioGroup rg_identity, rg_group;
    DatabaseHelper myDB;
    String[] year_items = {};
    String[] class_items = {};
    String[] name_list = {};
    String[] user_list = {};
    String[] parent_list = {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);

        rg_identity = (RadioGroup)findViewById(R.id.radioGroup_Identity);
        rg_group = (RadioGroup)findViewById(R.id.radioGroup_Group);
        Spinner dropdown_year = (Spinner)findViewById(R.id.spinner_Year);
        Spinner dropdown_class = (Spinner)findViewById(R.id.spinner_Class);
        Spinner dropdown_name = (Spinner)findViewById(R.id.spinner_Name);
        msg_cre_content_inp = (EditText)findViewById(R.id.msg_cre_content_inp);
        Button button_send = (Button)findViewById(R.id.button_send);
        button_send.setOnClickListener(this);

        myDB = new DatabaseHelper(this);

        Cursor regis_stu_data = myDB.getRegisStuContents();
        if(regis_stu_data.getCount() == 0) {
            Toast.makeText(this, "No registered students!", Toast.LENGTH_LONG).show();
        } else {
            while(regis_stu_data.moveToNext()) {
                String stu_username = regis_stu_data.getString(1);
                String stu_FirstName = regis_stu_data.getString(3);
                String stu_LastName = regis_stu_data.getString(4);
                String stu_Year = regis_stu_data.getString(5);
                String stu_Class = regis_stu_data.getString(6);
                String stu_ParentNum = regis_stu_data.getString(7);
                if(!(Arrays.asList(year_items).contains(stu_Year))) {
                    year_items = push(year_items, stu_Year);
                }
                if(!(Arrays.asList(class_items).contains(stu_Class))) {
                    class_items = push(class_items, stu_Class);
                }
                name_list = push(name_list, (stu_FirstName+ " " + stu_LastName));
                user_list = push(user_list, stu_username);
                parent_list = push(parent_list, stu_ParentNum);
            }
        }

        //Get the target receiver identity
//        if (rg_identity.getCheckedRadioButtonId() == R.id.Student){
//
//        }else if(rg_identity.getCheckedRadioButtonId() == R.id.Parent){
//
//        }else if(rg_identity.getCheckedRadioButtonId() == R.id.StudentAndParent){
//
//        }

        //create an adapter to describe how the items are displayed.
        ArrayAdapter<String> adapter_year = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, year_items);
        //set the spinners adapter to the previously created one.
        dropdown_year.setAdapter(adapter_year);
        dropdown_year.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                selected_year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Set default value to the selected strings
                selected_year = year_items[0];
            }
        });
        //Choose class
        ArrayAdapter<String> adapter_class = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, class_items);
        dropdown_class.setAdapter(adapter_class);
        dropdown_class.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                selected_class = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_class = class_items[0];
            }
        });
        ArrayAdapter<String> adapter_name = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, name_list);
        dropdown_name.setAdapter(adapter_name);
        dropdown_name.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                selected_name = parent.getItemAtPosition(position).toString();
                for(int i=0; i< name_list.length; i++){
                    if(selected_name.equals(name_list[i])){
                        selected_user = user_list[i];
                        selected_parent = parent_list[i];
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_name = user_list[0];
            }
        });

        /*
        //If the Year radio button is selected
        if(rg_group.getCheckedRadioButtonId() == R.id.Year) {
            //Disable the other 2 spinners
            dropdown_class.setEnabled(false);
            dropdown_name.setEnabled(false);
            //Set Strings to null
            selected_class = "";
            selected_name = "";

            //create an adapter to describe how the items are displayed.
            ArrayAdapter<String> adapter_year = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, year_items);

            //set the spinners adapter to the previously created one.
            dropdown_year.setAdapter(adapter_year);
            dropdown_year.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                    selected_year = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Set default value to the selected strings
                    selected_year = year_items[0];
                }
            });
        }


        //If the user choose class in the RadioGroup
        else if(rg_group.getCheckedRadioButtonId() == R.id.Class){
            //Disable spinner_name and set String to null
            dropdown_class.setEnabled(true);
            dropdown_name.setEnabled(false);
            selected_name = "";

            ArrayAdapter<String> adapter_year = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, year_items);
            //Choose year.
            dropdown_year.setAdapter(adapter_year);
            dropdown_year.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                    selected_year = parent.getItemAtPosition(position).toString();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Set default value to the selected strings
                    selected_year = year_items[0];
                }
            });

            //Choose class
            ArrayAdapter<String> adapter_class = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, class_items);
            dropdown_class.setAdapter(adapter_class);
            dropdown_class.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                    selected_class = parent.getItemAtPosition(position).toString();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    selected_class = class_items[0];
                }
            });
        }else if(rg_group.getCheckedRadioButtonId() == R.id.Individual){
            //Disable the other 2 spinners
            dropdown_class.setEnabled(false);
            dropdown_year.setEnabled(false);
            //Set Strings to null
            selected_class = "";
            selected_year = "";

            ArrayAdapter<String> adapter_name = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, name_list);
            dropdown_name.setAdapter(adapter_name);
            dropdown_name.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                    selected_name = parent.getItemAtPosition(position).toString();
                    for(int i=0; i< name_list.length; i++){
                        if(selected_name.equals(name_list[i])){
                            selected_user = user_list[i];
                            selected_parent = parent_list[i];
                        }
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    selected_name = user_list[0];
                }
            });
        }

         */
    }

    @Override
    public void onClick(View v) {
        String Message = msg_cre_content_inp.getText().toString();

        //Add data to database if data is not null
        if(Message.length() !=0) {
            //First determine the target group
            if (rg_group.getCheckedRadioButtonId() == R.id.Individual) {
                if (rg_identity.getCheckedRadioButtonId() == R.id.Student) {
                    addDataToDB_msg(selected_year, selected_class, selected_user, Message);
                } else if (rg_identity.getCheckedRadioButtonId() == R.id.Parent) {
                    addDataToDB_msg(selected_year, selected_class, selected_parent, Message);
                } else if (rg_identity.getCheckedRadioButtonId() == R.id.StudentAndParent) {
                    addDataToDB_msg(selected_year, selected_class, selected_user, Message);
                    addDataToDB_msg(selected_year, selected_class, selected_parent, Message);
                }
            } else if(rg_group.getCheckedRadioButtonId() == R.id.Class){
                Cursor regis_stu_data = myDB.getRegisStuContents();
                while(regis_stu_data.moveToNext()) {
                    String stu_username = regis_stu_data.getString(1);
                    String stu_Year = regis_stu_data.getString(5);
                    String stu_Class = regis_stu_data.getString(6);
                    String stu_ParentNum = regis_stu_data.getString(7);
                    if(selected_class.equals(stu_Class) && selected_year.equals(stu_Year)){
                        if (rg_identity.getCheckedRadioButtonId() == R.id.Student) {
                            addDataToDB_msg(selected_year, selected_class, stu_username, Message);
                        } else if (rg_identity.getCheckedRadioButtonId() == R.id.Parent) {
                            addDataToDB_msg(selected_year, selected_class, stu_ParentNum, Message);
                        } else if (rg_identity.getCheckedRadioButtonId() == R.id.StudentAndParent) {
                            addDataToDB_msg(selected_year, selected_class, stu_username, Message);
                            addDataToDB_msg(selected_year, selected_class, stu_ParentNum, Message);
                        }
                    }
                }

            }else if (rg_group.getCheckedRadioButtonId() == R.id.Year){
                Cursor regis_stu_data = myDB.getRegisStuContents();
                while(regis_stu_data.moveToNext()) {
                    String stu_username = regis_stu_data.getString(1);
                    String stu_Year = regis_stu_data.getString(5);
                    String stu_Class = regis_stu_data.getString(6);
                    String stu_ParentNum = regis_stu_data.getString(7);
                    if(selected_year.equals(stu_Year)){
                        if (rg_identity.getCheckedRadioButtonId() == R.id.Student) {
                            addDataToDB_msg(selected_year, stu_Class, stu_username, Message);
                        } else if (rg_identity.getCheckedRadioButtonId() == R.id.Parent) {
                            addDataToDB_msg(selected_year, stu_Class, stu_ParentNum, Message);
                        } else if (rg_identity.getCheckedRadioButtonId() == R.id.StudentAndParent) {
                            addDataToDB_msg(selected_year, stu_Class, stu_username, Message);
                            addDataToDB_msg(selected_year, stu_Class, stu_ParentNum, Message);
                        }
                    }
                }
            }
        }else{
            Toast.makeText(this, "ERROR: empty message field", Toast.LENGTH_LONG).show();
        }
        //Next determine the target identity
    }

    public void addDataToDB_msg(String yYear, String cClass, String nName, String mMessage){
        boolean insertData = myDB.addData_msg(yYear, cClass, nName, mMessage);
        if(insertData == true){
            Toast.makeText(this, "Successfully insert data", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Failed to insert data to db", Toast.LENGTH_LONG).show();
        }
    }

    private static String[] push(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }
}
