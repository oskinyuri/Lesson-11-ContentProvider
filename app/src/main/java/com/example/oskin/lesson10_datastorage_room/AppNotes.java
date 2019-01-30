package com.example.oskin.lesson10_datastorage_room;

import android.app.Application;

import com.example.oskin.lesson10_datastorage_room.DatabaseManagement.NoteDatabase;
import com.example.oskin.lesson10_datastorage_room.Model.Note;

import java.util.List;

import androidx.room.Room;

public class AppNotes extends Application {

    private static String NOTE_DATABASE_NAME = "NOTE_DATABASE";
    private static NoteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class, NOTE_DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public static List<Note> getNotes(){
        return db.getNoteDAO().getNotes();
    }

    public static Note getNote(String noteID){
        return db.getNoteDAO().getNote(noteID);
    }

    public static void addNote(Note note){
        db.getNoteDAO().addNote(note);
    }

    public static void updateNote(Note note){
        db.getNoteDAO().updateNote(note);
    }

    public static void deleteNote(Note note){
        db.getNoteDAO().deleteNote(note);
    }

}
