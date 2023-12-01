package com.zybooks.deegutierrez_photofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "PhotoLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "photos_library";
    private static final String COLUMN_ID = "photo_id";
    private static final String COLUMN_PHOTO = "photo_data";
    private static final String COLUMN_PHOTO_TAG = "photo_tag";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PHOTO + " BLOB, " +
                COLUMN_PHOTO_TAG + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // This will add the photo and tags to the database
    void addPhoto(byte[] photoData, String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PHOTO, photoData);
        contentValues.put(COLUMN_PHOTO_TAG, tag);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Toast.makeText(context, "Oh no! Please try again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully added photo!", Toast.LENGTH_SHORT).show();
        }
    }
    // Retrieve all photos from the database
    public List<PhotoModel> getAllPhotos() {
        List<PhotoModel> photoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor != null) {
            try {
                int columnIndexPhoto = cursor.getColumnIndex(COLUMN_PHOTO);
                int columnIndexTag = cursor.getColumnIndex(COLUMN_PHOTO_TAG);

                while (cursor.moveToNext()) {
                    byte[] photoData = columnIndexPhoto != -1 ? cursor.getBlob(columnIndexPhoto) : null;
                    String photoTag = columnIndexTag != -1 ? cursor.getString(columnIndexTag) : null;

                    if (photoData != null && photoTag != null) {
                        photoList.add(new PhotoModel(photoData, photoTag));
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return photoList;
    }

    // Method to get photos based on tags from user
    public List<PhotoModel> getPhotosByTag(String tag) {
        List<PhotoModel> photoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_PHOTO_TAG + " LIKE ?";
        String[] selectionArgs = {"%" + tag + "%"};

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            try {
                int columnIndexPhoto = cursor.getColumnIndex(COLUMN_PHOTO);
                int columnIndexTag = cursor.getColumnIndex(COLUMN_PHOTO_TAG);

                while (cursor.moveToNext()) {
                    byte[] photoData = columnIndexPhoto != -1 ? cursor.getBlob(columnIndexPhoto) : null;
                    String photoTag = columnIndexTag != -1 ? cursor.getString(columnIndexTag) : null;

                    if (photoData != null && photoTag != null) {
                        photoList.add(new PhotoModel(photoData, photoTag));
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return photoList;
    }
}
