package com.example.lesson11contentprovider.databaseManagement;

import com.example.lesson11contentprovider.model.Note;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDAO getNoteDAO();

}
