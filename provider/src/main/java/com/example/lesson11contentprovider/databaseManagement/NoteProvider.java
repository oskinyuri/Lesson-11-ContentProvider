package com.example.lesson11contentprovider.databaseManagement;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoteProvider extends ContentProvider {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "notes.db";
    public static final String TABLE_NAME = "NOTES";

    public static final String COLUMN_ID = "noteID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_CONTENT = "CONTENT";

    static final String DB_CREATE = "create table " + TABLE_NAME + "("
            + COLUMN_ID + " text primary key, "
            + COLUMN_NAME + " text, " + COLUMN_CONTENT + " text" + ");";


    static final String AUTHORITY = "com.example.lesson11contentprovider.Note";

    // path
    static final String NOTE_PATH = "notes";

    // Общий Uri
    public static final Uri NOTE_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + NOTE_PATH);

    // Типы данных
    // набор строк
    static final String NOTE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + NOTE_PATH;

    // одна строка
    static final String NOTE_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + NOTE_PATH;

    //// UriMatcher
    // общий Uri
    static final int URI_NOTES = 1;

    // Uri с указанным ID
    static final int URI_NOTES_ID = 2;

    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, NOTE_PATH, URI_NOTES);
        uriMatcher.addURI(AUTHORITY, NOTE_PATH + "/#", URI_NOTES_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // проверяем Uri
        switch (uriMatcher.match(uri)) {
            case URI_NOTES: // общий Uri
                break;
            case URI_NOTES_ID: // Uri с ID
                String id = uri.getLastPathSegment();
                // добавляем ID к условию выборки
                if (TextUtils.isEmpty(selection)) {
                    selection = COLUMN_ID + " = " + id;
                } else {
                    selection = selection + " AND " + COLUMN_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CONTACT_CONTENT_URI
        cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),
                NOTE_CONTENT_URI);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_NOTES:
                return NOTE_CONTENT_TYPE;
            case URI_NOTES_ID:
                return NOTE_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) != URI_NOTES)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(TABLE_NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(NOTE_CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_NOTES:
                break;
            case URI_NOTES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = COLUMN_ID + " = " + id;
                } else {
                    selection = selection + " AND " + COLUMN_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(TABLE_NAME, selection, selectionArgs);
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_NOTES:
                break;
            case URI_NOTES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = COLUMN_ID + " = " + id;
                } else {
                    selection = selection + " AND " + COLUMN_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(TABLE_NAME, values, selection, selectionArgs);
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
