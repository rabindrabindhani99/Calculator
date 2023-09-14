package com.grabs4buisness.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CalculationHistory.db";
    private static final int DATABASE_VERSION = 1;

    // Define the table and columns
    private static final String TABLE_NAME = "history";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_HISTORY_ITEM = "historyItem";

    // Create the table SQL statement
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_HISTORY_ITEM + " TEXT);";

    public HistoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades if needed
    }

    // Method to insert a new history item
    public long insertHistoryItem(String historyItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HISTORY_ITEM, historyItem);
        return db.insert(TABLE_NAME, null, values);
    }

    // Method to retrieve all history items
    public Cursor getAllHistoryItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_HISTORY_ITEM};
        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }

    // Method to clear the history
    public int clearHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }

}

