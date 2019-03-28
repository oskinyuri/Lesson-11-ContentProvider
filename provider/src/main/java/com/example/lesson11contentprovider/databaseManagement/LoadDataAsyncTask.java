package com.example.lesson11contentprovider.databaseManagement;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lesson11contentprovider.AppNotes;
import com.example.lesson11contentprovider.model.Note;
import com.example.lesson11contentprovider.noteCallbacks.DeletedNoteCallback;
import com.example.lesson11contentprovider.noteCallbacks.GotNoteCallback;
import com.example.lesson11contentprovider.noteCallbacks.GotNotesCallback;
import com.example.lesson11contentprovider.noteCallbacks.LoadedNoteCallback;
import com.example.lesson11contentprovider.noteCallbacks.NoteCallback;

import java.util.List;

public class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {

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
        noteList = AppNotes.getNotes();
    }

    private void addNote(){
        AppNotes.addNote(mNote);
    }

    private void updateNote(){
        AppNotes.updateNote(mNote);
    }

    private void deleteNote(){
        AppNotes.deleteNote(mNote);
    }

    private void getNote(){
        mNote = AppNotes.getNote(mNote.getId());
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
}
