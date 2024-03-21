package com.example.lab10_progresstest2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "note_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "note";
    private static final String ID_COL = "id";
    private static final String TITLE_COL = "title";

    private static final String CONTENT_COL = "content";
    private static final String DATE_COL = "date";

    private static final String TIME_COL = "time";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE_COL + " TEXT,"
                + CONTENT_COL + " TEXT,"
                + DATE_COL + " TEXT, "
                + TIME_COL + " TEXT) ";

        sqLiteDatabase.execSQL(query);
    }

    public void addNote(String title, String description, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TITLE_COL, title);
        values.put(CONTENT_COL, description);
        values.put(DATE_COL, date);
        values.put(TIME_COL, time);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public ArrayList<NoteModal> readNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<NoteModal> noteModalArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                noteModalArrayList.add(new NoteModal(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return noteModalArrayList;
    }

    public void updateNote(String id, String title, String content, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COL, title);
        values.put(CONTENT_COL, content);
        values.put(DATE_COL, date);
        values.put(TIME_COL, time);

        db.update(TABLE_NAME, values, "id=?", new String[]{id});
        db.close();
    }

    public void deleteNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{id});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
