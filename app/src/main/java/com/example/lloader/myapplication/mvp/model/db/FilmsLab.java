package com.example.lloader.myapplication.mvp.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lloader.myapplication.mvp.model.Film;

/**
 * Created by Alexander Garkavenko
 */

public class FilmsLab {
    private static final String TAG = "SQL";
    private SQLiteDatabase mSQLiteDatabase;

    public FilmsLab(Context context) {
        mSQLiteDatabase = new DataBaseHelper(context).getWritableDatabase();
    }

    public void addFavorite(Film film) {
        ContentValues contentValues = getContentValues(film);
        mSQLiteDatabase.insert(DataBaseSchema.Table.NAME, null, contentValues);
    }

    public void deleteFavorite(Film film) {
        mSQLiteDatabase.delete(DataBaseSchema.Table.NAME,
                DataBaseSchema.Table.Cols.ID + " = ? AND " +
                DataBaseSchema.Table.Cols.TITLE + " = ?", new String[] {film.getId() + "", film.getTitle()});
    }

    public void checkForFavorite(Film film) {
        Cursor cursor = query(DataBaseSchema.Table.Cols.ID + " = ? AND " + DataBaseSchema.Table.Cols.TITLE + " = ?",
                new String[] {film.getId() + "", film.getTitle()});
        try {
            if(cursor.getCount() == 0) {
                Log.d(TAG, film.getTitle() + " is not favorite");
                film.setFavorite(false);
            } else {
                Log.d(TAG, film.getTitle() + " is favorite");
                film.setFavorite(true);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            cursor.close();
        }
    }
    //DataBaseSchema.Table.Cols.ID + " = ? AND " + DataBaseSchema.Table.Cols.TITLE + " = ?"
    //new String[] {filmsResults.getId() + "", filmsResults.getTitle()}
    private Cursor query(String whereClause, String[] whereArgs) {
        return mSQLiteDatabase.query(
                DataBaseSchema.Table.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
    }


    private static ContentValues getContentValues(Film film) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseSchema.Table.Cols.ID, film.getId());
        contentValues.put(DataBaseSchema.Table.Cols.TITLE, film.getTitle());
        return contentValues;
    }
}
