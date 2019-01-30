package com.example.oskin.lesson10_datastorage_room.NoteCallbacks;

import com.example.oskin.lesson10_datastorage_room.Model.Note;

public interface GotNoteCallback extends NoteCallback{
    void onNoteReceived(Note note);
}
