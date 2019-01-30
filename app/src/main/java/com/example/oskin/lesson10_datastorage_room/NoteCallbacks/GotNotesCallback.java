package com.example.oskin.lesson10_datastorage_room.NoteCallbacks;

import com.example.oskin.lesson10_datastorage_room.Model.Note;

import java.util.List;

public interface GotNotesCallback extends NoteCallback {
    void onNotesReceived(List<Note> notes);
}
