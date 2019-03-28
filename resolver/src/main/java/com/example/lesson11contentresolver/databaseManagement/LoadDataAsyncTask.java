package com.example.lesson11contentresolver.databaseManagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.lesson11contentresolver.model.Note;
import com.example.lesson11contentresolver.noteCallbacks.DeletedNoteCallback;
import com.example.lesson11contentresolver.noteCallbacks.GotNoteCallback;
import com.example.lesson11contentresolver.noteCallbacks.GotNotesCallback;
import com.example.lesson11contentresolver.noteCallbacks.LoadedNoteCallback;
import com.example.lesson11contentresolver.noteCallbacks.NoteCallback;

import java.util.ArrayList;
import java.util.List;

public class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {

    private static final String AUTHORITY = "com.example.lesson11contentprovider.Note";
    private static final String NOTE_PATH = "notes";
    private static final Uri NOTE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + NOTE_PATH);

    public static final String COLUMN_ID = "noteID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_CONTENT = "CONTENT";


    public static final int LOAD_NOTES = 1;
    public static final int ADD_NOTE = 2;
    public static final int DELETE_NOTE = 3;
    public static final int UPDATE_NOTE = 4;
    public static final int GET_NOTE = 5;

    private int mAction;
    private Note mNote;
    private List<Note> noteList;
    private Context mContext;

    private NoteCallback mNoteCallback;
    public LoadDataAsyncTask (Context context, NoteCallback noteCallback, int action, Note note){
        mContext = context;
        mNoteCallback = noteCallback;
        mAction = action;
        mNote = note;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        switch (mAction) {
            case LOAD_NOTES:
                getNotes();
                break;
            case ADD_NOTE:
                addNote();
                break;
            case UPDATE_NOTE:
                updateNote();
                break;
            case DELETE_NOTE:
                deleteNote();
                break;
            case GET_NOTE:
                getNote();
                break;
        }
        return null;
    }

    private void getNotes(){

        Cursor cursor = mContext.getContentResolver().query(NOTE_CONTENT_URI, null, null,
                null, null);
        assert cursor != null;
        noteList = parseCursor(cursor);

        //noteList = AppNotes.getNotes();
    }

    private void addNote(){
        ContentValues cv = ConvertNote.getContentValues(mNote);
        mContext.getContentResolver().insert(NOTE_CONTENT_URI, cv);
    }

    private void updateNote(){
        ContentValues cv = ConvertNote.getContentValues(mNote);
        mContext.getContentResolver().update(NOTE_CONTENT_URI, cv, COLUMN_ID + "=\"" + mNote.getId() + "\"", null);
    }

    private void deleteNote(){

        mContext.getContentResolver().delete(NOTE_CONTENT_URI,  COLUMN_ID + "=\"" + mNote.getId() + "\"", null);
    }

    private void getNote() {
        Cursor cursor = mContext.getContentResolver().query(NOTE_CONTENT_URI, null, COLUMN_ID + "=\"" + mNote.getId() + "\"",
                null, null);
        assert cursor != null;
        mNote = parseCursorForNote(cursor);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        switch (mAction){
            case LOAD_NOTES:
                ((GotNotesCallback) mNoteCallback).onNotesReceived(noteList);
                break;
            case ADD_NOTE:
                ((LoadedNoteCallback) mNoteCallback).onNoteLoaded();
                break;
            case UPDATE_NOTE:
                ((LoadedNoteCallback) mNoteCallback).onNoteLoaded();
                break;
            case DELETE_NOTE:
                ((DeletedNoteCallback) mNoteCallback).onNoteDeleted();
                break;
            case GET_NOTE:
                ((GotNoteCallback) mNoteCallback).onNoteReceived(mNote);
                break;
        }
    }

    private Note parseCursorForNote(Cursor cursor) {
        Note note = null;
        if (cursor.moveToFirst()) {
            note = new Note();
            note.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            note.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            note.setText(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
        }
        return note;
    }

    private List<Note> parseCursor(Cursor cursor) {
        List<Note> noteList = new ArrayList<>();
        Note note;
        if (cursor.moveToFirst()){
            do{
                note = new Note();
                note.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                note.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                note.setText(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }
}
