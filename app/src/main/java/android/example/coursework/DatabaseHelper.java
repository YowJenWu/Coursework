package android.example.coursework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.VisibleForTesting;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "notifications.db";

    public static final String Table_no = "notifications_data";
    public static final String Table_msg = "message_data";
    public static final String TABLE_ev = "event_table";
    public static final String TABLE_NAME = "registered_student_table";
    public static final String TABLE_NAME_Admin = "student_table";
    //Parent Table
    public static final String TABLE_NAME_Parent = "parent_table";

    //columns of notificaiton table
    public static final String Column0 = "ID";
    public static final String Column1 = "title";
    public static final String Column2 = "date";
    public static final String Column3 = "content";
    public static final String Column4 = "image";

    //columns of message table
    public static final String Column_msg_0 = "ID";
    public static final String Column_msg_1 = "year";
    public static final String Column_msg_2 = "class";
    public static final String Column_msg_3 = "name";
    public static final String Column_msg_4 = "message";

    //columns of event table
    private static final String COL1 = "ID";
    private static final String COL2 = "Title";
    private static final String COL3 = "Date";
    private static final String COL4 = "Time";
    private static final String COL5 = "Location";
    private static final String COL6 = "Content";

    //Table of registered_student_table column name
    private static final String R_STU_COL1 = "ID";
    private static final String R_STU_COL2 = "Username";
    private static final String R_STU_COL3 = "Password";
    private static final String R_STU_COL4 = "First_Name";
    private static final String R_STU_COL5 = "Last_Name";
    private static final String R_STU_COL6 = "Year";
    private static final String R_STU_COL7 = "Class";
    private static final String R_STU_COL8 = "Parent_Phone";

    //Table of student_table column name
    private static final String STU_COL1 = "Username";
    private static final String STU_COL2 = "Registered";

    //Parent Table's column name
    private static final String PAR_COL1 = "Username";
    private static final String PAR_COL2 = "Password";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = " CREATE TABLE " + Table_no + " (ID INTEGER PRIMARY KEY, " + "TITLE TEXT, DATE TEXT, CONTENT TEXT, IMAGE BLOB) ";
        db.execSQL(createTable);

        String createTable_msg = " CREATE TABLE " + Table_msg + " (ID INTEGER PRIMARY KEY, " + "YEAR TEXT, CLASS TEXT, NAME TEXT, CONTENT TEXT) ";
        db.execSQL(createTable_msg);

        String createTable_ev = "CREATE TABLE " + TABLE_ev
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, "
                + COL3 + " TEXT, "
                + COL4 + " TEXT, "
                + COL5 + " TEXT, "
                + COL6 + " TEXT )";
        db.execSQL(createTable_ev);

        String createRegisteredTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + R_STU_COL2 + " TEXT, "
                + R_STU_COL3 + " TEXT, "
                + R_STU_COL4 + " TEXT, "
                + R_STU_COL5 + " TEXT, "
                + R_STU_COL6 + " TEXT, "
                + R_STU_COL7 + " TEXT, "
                + R_STU_COL8 + " TEXT )";
        db.execSQL(createRegisteredTable);

        String createStudentTable = "CREATE TABLE " + TABLE_NAME_Admin
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STU_COL1 + " TEXT, "
                + STU_COL2 + " INTEGER DEFAULT 0 )";
        db.execSQL(createStudentTable);

        String createParentTable = "CREATE TABLE " + TABLE_NAME_Parent
                + " (" + PAR_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PAR_COL2 + " TEXT )";
        db.execSQL(createParentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP IF TABLE EXISTS "+ Table_no);
        db.execSQL(" DROP IF TABLE EXISTS "+ Table_msg);
        db.execSQL(" DROP IF TABLE EXISTS "+ TABLE_ev);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME_Admin);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_Parent);
        onCreate(db);
    }

    //Add data to the table_notification
    public boolean addData(String tTitle, String dDate, String cContent, byte[] iImage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column1, tTitle);
        contentValues.put(Column2, dDate);
        contentValues.put(Column3, cContent);
        contentValues.put(Column4, iImage);

        long result = db.insert(Table_no, null, contentValues);

        //If insert incorrectly then return -1
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //Add data to the table_message
    public boolean addData_msg(String yYear, String cClass, String nName, String mMessage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_msg_1, yYear);
        contentValues.put(Column_msg_2, cClass);
        contentValues.put(Column_msg_3, nName);
        contentValues.put(Column_msg_4, mMessage);

        long result = db.insert(Table_msg, null, contentValues);

        //If insert incorrectly then return -1
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //Add data to event list
    public boolean addData_ev(String title, String date, String time, String location, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, title);
        contentValues.put(COL3, date);
        contentValues.put(COL4, time);
        contentValues.put(COL5, location);
        contentValues.put(COL6, content);

        //Log.d(TAG, "addData: Adding Title:" + title + ", Date:" + date + ", Time:" + time + ", Location:" + location + ", Content:" + content + "to" + TABLE_NAME);
        long result = db.insert(TABLE_ev, null, contentValues);
        //To check if the data insert correctly, if correct, it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Add data to Register Student table
    public boolean addRegisStuData(String username, String password, String firstname, String lastname, String year, String class_num, String parent_phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(R_STU_COL2, username);
        contentValues.put(R_STU_COL3, password);
        contentValues.put(R_STU_COL4, firstname);
        contentValues.put(R_STU_COL5, lastname);
        contentValues.put(R_STU_COL6, year);
        contentValues.put(R_STU_COL7, class_num);
        contentValues.put(R_STU_COL8, parent_phone);
        //Log.d(TAG, "addData: Adding Title:" + title + ", Date:" + date + ", Time:" + time + ", Location:" + location + ", Content:" + content + "to" + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        //To check if the data insert correctly, if correct, it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Add data to Table_Admin
    public boolean addStuData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL1, username);
        //Log.d(TAG, "addData: Adding Title:" + title + ", Date:" + date + ", Time:" + time + ", Location:" + location + ", Content:" + content + "to" + TABLE_NAME);
        long result = db.insert(TABLE_NAME_Admin, null, contentValues);
        //To check if the data insert correctly, if correct, it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Add data to the parent_table
    public boolean addParentData(int username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PAR_COL1, username);
        contentValues.put(PAR_COL2, password);
        //Log.d(TAG, "addData: Adding Title:" + title + ", Date:" + date + ", Time:" + time + ", Location:" + location + ", Content:" + content + "to" + TABLE_NAME);
        long result = db.insert(TABLE_NAME_Parent, null, contentValues);
        //To check if the data insert correctly, if correct, it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Query for Notification data
    public Cursor getListContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + Table_no, null);
        return data;
    }

    //Qurey for Event data
    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_ev, null);
        return data;
    }

    //Get event item id
    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_ev + " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Query for Message data
    public Cursor getListContent_msg(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + Table_msg, null);
        return data;
    }

    //Query for registered student list
    public Cursor getRegisStuContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    //Query for Admin student list
    public Cursor getStuListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME_Admin, null);
        return data;
    }
    //Get parent data
    public Cursor getParentContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME_Parent, null);
        return data;
    }

    //Update notification data
    public boolean updateDB(String tTitle, String dDate, String cContent, byte[] iImage, String key){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column1, tTitle);
        contentValues.put(Column2, dDate);
        contentValues.put(Column3, cContent);
        contentValues.put(Column4, iImage);

        db.update(Table_no, contentValues, Column1 + "=?", new String[]{key});
        return true;
    }

    //Update event data
    public void updateEventList(int id, String newTitle, String newDate, String newTime, String newLocation, String newContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_ev + " SET " + COL2 + " = '" + newTitle + "', " +
                COL3 + " = '" + newDate + "', " +
                COL4 + " = '" + newTime + "', " +
                COL5 + " = '" + newLocation + "', " +
                COL6 + " = '" + newContent + "' WHERE " +
                COL1 + " = '" + id + "'";
        Log.d("DatabaseHelper", "update: query: " + query);
        db.execSQL(query);
    }

    //Update registered student list
    public void updateRegisStuList(int id, String newPassword, String newFirstName, String newLastName, String newYear, String newClass, String newParentNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + R_STU_COL3 + " = '" + newPassword + "', " +
                R_STU_COL4 + " = '" + newFirstName + "', " +
                R_STU_COL5 + " = '" + newLastName + "', " +
                R_STU_COL6 + " = '" + newYear + "', " +
                R_STU_COL7 + " = '" + newClass + "', " +
                R_STU_COL8 + " = '" + newParentNum + "' WHERE " +
                R_STU_COL1 + " = '" + id + "'";
        Log.d(TAG, "update: query: " + query);
        db.execSQL(query);
    }

    //Update admin student list
    public void updateStuList(int flag, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_Admin + " SET " +
                STU_COL2 + " = " + flag + " WHERE " +
                STU_COL1 + " = '" + username + "'";
        Log.d(TAG, "update: query: " + query);
        db.execSQL(query);
    }

    public void deleteDB(String key){
        SQLiteDatabase db = this.getWritableDatabase();

        //To delete all data in db
//        db.execSQL("delete from "+ Table_Name);
        db.delete(Table_no, Column1 + "=?", new String[]{key});
    }

    //delete the whole database
//    public void resetDB(Context context){
//        context.deleteDatabase(DATABASE_NAME);
//    }
}
