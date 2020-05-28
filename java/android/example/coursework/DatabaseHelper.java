package android.example.coursework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.VisibleForTesting;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notifications.db";
    public static final String Table_Name = "notifications_data";
    public static final String Column0 = "ID";
    public static final String Column1 = "title";
    public static final String Column2 = "date";
    public static final String Column3 = "content";
    public static final String Column4 = "image";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = " CREATE TABLE " + Table_Name + " (ID INTEGER PRIMARY KEY, " + "TITLE TEXT, DATE TEXT, CONTENT TEXT, IMAGE BLOB) ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP IF TABLE EXISTS "+ Table_Name);
        onCreate(db);
    }

    public boolean addData(String tTitle, String dDate, String cContent, byte[] iImage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column1, tTitle);
        contentValues.put(Column2, dDate);
        contentValues.put(Column3, cContent);
        contentValues.put(Column4, iImage);

        long result = db.insert(Table_Name, null, contentValues);

        //If insert incorrectly then return -1
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //Query for 1 week repeats
    public Cursor getListContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + Table_Name, null);
        return data;
    }

    //Update data
    public boolean updateDB(String tTitle, String dDate, String cContent, byte[] iImage, String key){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column1, tTitle);
        contentValues.put(Column2, dDate);
        contentValues.put(Column3, cContent);
        contentValues.put(Column4, iImage);

        db.update(Table_Name, contentValues, Column1 + "=?", new String[]{key});
        return true;
    }
    public void deleteDB(String key){
        SQLiteDatabase db = this.getWritableDatabase();

        //To delete all data in db
//        db.execSQL("delete from "+ Table_Name);
        db.delete(Table_Name, Column1 + "=?", new String[]{key});
    }

    //delete the whole database
//    public void resetDB(Context context){
//        context.deleteDatabase(DATABASE_NAME);
//    }
}
