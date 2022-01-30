package com.example.storeme;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
