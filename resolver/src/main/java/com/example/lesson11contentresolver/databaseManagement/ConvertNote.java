package com.example.lesson11contentresolver.databaseManagement;

import android.content.ContentValues;

import com.example.lesson11contentresolver.model.Note;

public class ConvertNote {

    public static final String COLUMN_ID = "noteID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_CONTENT = "CONTENT";

    public static ContentValues getContentValues(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, note.getId());
        contentValues.put(COLUMN_NAME, note.getName());
        contentValues.put(COLUMN_CONTENT, note.getText());
        return contentValues;
    }
}
