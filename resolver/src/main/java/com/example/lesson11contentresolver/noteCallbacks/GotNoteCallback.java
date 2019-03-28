package com.example.lesson11contentresolver.noteCallbacks;

import com.example.lesson11contentresolver.model.Note;

public interface GotNoteCallback extends NoteCallback{
    void onNoteReceived(Note note);
}
