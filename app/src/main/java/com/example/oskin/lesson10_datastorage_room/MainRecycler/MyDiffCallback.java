package com.example.oskin.lesson10_datastorage_room.MainRecycler;

import com.example.oskin.lesson10_datastorage_room.Model.Note;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class MyDiffCallback extends DiffUtil.Callback {

    private List<Note> mOldNotes;
    private List<Note> mNewNotes;

    public MyDiffCallback(List<Note> oldNotes, List<Note> newNotes) {
        mOldNotes = oldNotes;
        mNewNotes = newNotes;
    }

    @Override
    public int getOldListSize() {
        return mOldNotes.size();
    }

    @Override
    public int getNewListSize() {
        return mNewNotes.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldNotes.get(oldItemPosition).getId().equals(mNewNotes.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldNotes.get(oldItemPosition).hashCode() == mNewNotes.get(newItemPosition).hashCode();
    }
}