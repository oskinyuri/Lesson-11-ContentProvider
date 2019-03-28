package com.example.lesson11contentprovider.noteCallbacks;

import com.example.lesson11contentprovider.model.Note;

import java.util.List;

public interface GotNotesCallback extends NoteCallback {
    void onNotesReceived(List<Note> notes);
}
