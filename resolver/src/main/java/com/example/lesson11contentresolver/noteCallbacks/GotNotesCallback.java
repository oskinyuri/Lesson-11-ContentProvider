package com.example.lesson11contentresolver.noteCallbacks;

import com.example.lesson11contentresolver.model.Note;

import java.util.List;

public interface GotNotesCallback extends NoteCallback {
    void onNotesReceived(List<Note> notes);
}
