package com.example.lloader.myapplication.mvp.model.db;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by Alexander Garkavenko
 */

public class FavoriteCursorWrapper extends CursorWrapper{

    public FavoriteCursorWrapper(Cursor cursor) {
        super(cursor);
    }
/*
    public Film getFavorite() {
        int id = getInt(getColumnIndex(DataBaseSchema.Table.Cols.ID));
        String title = getString(getColumnIndex(DataBaseSchema.Table.Cols.TITLE));
        Film filmsResults = new Film();

    }*/
}
