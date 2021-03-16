package com.example.schoolofrock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "rockthevote.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_SONG_VOTE = "song_vote";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_SONG = "song";
    public static final String COLUMN_LIST_VOTER = "voter";
    public static final String COLUMN_LIST_VOTE = "vote";

    /**
     * Create a version of the Schoolofrock database
     * @param context reference to the activity that initializes a DBHandler
     * @param factory null
     */
    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates schoolofrock db tables
     * @param sqLiteDatabase reference to Schoolofrock db
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //define create statement fdr vote table and store it
        //in a string
        String query = "CREATE TABLE " + TABLE_SONG_VOTE + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_SONG + " TEXT, " +
                COLUMN_LIST_VOTER + " TEXT, " +
                COLUMN_LIST_VOTE + " INTEGER);";

        //execute the statement
        sqLiteDatabase.execSQL(query);
    }
    /**
     * Creates a new version of the Schoolofrock db
     * @param sqLiteDatabase reference to Schoolofrock db
     * @param oldVersion old version of Schoolofrock db
     * @param newVersion new version of Schoolofrock db
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //define drop statement and store it in a string
        String query = "DROP TABLE IF EXISTS " + TABLE_SONG_VOTE;

        //execute the drop statement
        sqLiteDatabase.execSQL(query);

        //call the method that creates
        onCreate(sqLiteDatabase);
    }
    /**
     * This method is called when the like or dislike button in the action bar of the main activity is
     * clicked. It inserts a new row into the song_vote table.
     * @param voter voter name
     * @param song song text
     *
     */
    public void addVote(String voter, String song, int vote) {
        //get reference to reminder DB
        SQLiteDatabase db = getWritableDatabase();

        //initialize contentvalues obj
        ContentValues values = new ContentValues();

        //put data into contentvalues obj
        values.put(COLUMN_LIST_VOTER, voter);
        values.put(COLUMN_LIST_SONG, song);
        values.put(COLUMN_LIST_VOTE, vote);

        //insert data in contentvalues obj into reminder table
        db.insert(TABLE_SONG_VOTE, null, values);

        //close db reference
        db.close();

    }

    public int getCountLikes (Integer vote) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_SONG_VOTE +
                " WHERE " + COLUMN_LIST_VOTE + " = " + "'" + vote + "'";

        return db.rawQuery(query, null).getCount();
    }


}