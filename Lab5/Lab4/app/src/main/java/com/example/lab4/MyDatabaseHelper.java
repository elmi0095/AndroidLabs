package com.example.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    // Database information
    private static final String DATABASE_NAME = "todo_database.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_TODO = "todo_items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_URGENT = "urgent";

    // SQL statement to create the table
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_TODO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_TEXT + " TEXT," +
                    COLUMN_URGENT + " INTEGER)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public void insertTodoItem(String text, boolean isUrgent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, text);
        values.put(COLUMN_URGENT, isUrgent ? 1 : 0);
        db.insert(TABLE_TODO, null, values);
        db.close();
    }

    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TODO, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
                boolean isUrgent = cursor.getInt(cursor.getColumnIndex(COLUMN_URGENT)) == 1;
                todoItems.add(new TodoItem(id, text, isUrgent));
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return todoItems;
    }

    public void deleteTodoItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_ID + "=?", new String[]{String.valueOf(itemId)});
        db.close();
    }
}
