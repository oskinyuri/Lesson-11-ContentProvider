package com.example.lesson11contentprovider.noteCallbacks;

import com.example.lesson11contentprovider.model.Note;

public interface GotNoteCallback extends NoteCallback{
    void onNoteReceived(Note note);
}
