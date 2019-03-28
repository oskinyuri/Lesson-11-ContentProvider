package com.example.lesson11contentprovider.databaseManagement;

import android.content.ContentValues;

import com.example.lesson11contentprovider.model.Note;

public class ConvertNote {
    public static ContentValues getContentValues(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteProvider.COLUMN_ID, note.getId());
        contentValues.put(NoteProvider.COLUMN_NAME, note.getName());
        contentValues.put(NoteProvider.COLUMN_CONTENT, note.getText());
        return contentValues;
    }
}
