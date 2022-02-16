package com.example.storeme.object;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;
import java.util.ArrayList;

public class ObjectDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "MainDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "myStorageRoom";
    private static final String ID_COL = "id";
    private static final String TYPE_COL = "type";
    private static final String ATTRIBUTE1_COL = "attribute1";
    private static final String ATTRIBUTE2_COL = "attribute2";

    // creating a constructor for our database handler.
    public ObjectDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TYPE_COL + " TEXT,"
                + ATTRIBUTE1_COL + " TEXT,"
                + ATTRIBUTE2_COL + " TEXT)";

        db.execSQL(query);
    }

    // this method is use to add new object to our sqlite database.
    public void addNewObject(String objectType, String objectAttribute1, String objectAttribute2) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TYPE_COL, objectType);
        values.put(ATTRIBUTE1_COL, objectAttribute1);
        values.put(ATTRIBUTE2_COL, objectAttribute2);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    // this method is used to get all objects in sqlite database
    public ArrayList<StoreMeObject> getAllObjects(){
        ArrayList<StoreMeObject> StoreMeObjectArrayList = new ArrayList<>();

        try {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorObjects = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if(cursorObjects.moveToFirst()){
            do{
                StoreMeObjectArrayList.add(new StoreMeObject(cursorObjects.getInt(0),cursorObjects.getString(1),
                        cursorObjects.getString(2),cursorObjects.getString(3)));
            }while (cursorObjects.moveToNext());
        }

        cursorObjects.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return StoreMeObjectArrayList;
    }

    // this method is used to check if object is already in sqlite database
    public boolean checkIfObjectExists(String type, String attribute1, String attribute2){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT *  FROM " + TABLE_NAME + " WHERE " + TYPE_COL + " = ? AND " + ATTRIBUTE1_COL + " = ? AND " + ATTRIBUTE2_COL + " = ?";
            Cursor cursorObjects = db.rawQuery(query, new String[] {type,attribute1,attribute2});

            if (cursorObjects.getCount() > 0) {
                return true;
            }

            cursorObjects.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteObject(int id){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            db.delete(TABLE_NAME, ID_COL + "=?",new String[]{String.valueOf(id)});
            db.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateObject(int id, String type, String att1, String att2){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues values  = new ContentValues();

            values.put(TYPE_COL, type);
            values.put(ATTRIBUTE1_COL, att1);
            values.put(ATTRIBUTE2_COL, att2);

            db.update(TABLE_NAME, values, ID_COL + "=?", new String[]{String.valueOf(id)});
            db.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
