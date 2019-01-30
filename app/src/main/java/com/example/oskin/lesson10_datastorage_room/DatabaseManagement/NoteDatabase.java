package com.example.oskin.lesson10_datastorage_room.DatabaseManagement;

import com.example.oskin.lesson10_datastorage_room.Model.Note;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDAO getNoteDAO();

}
