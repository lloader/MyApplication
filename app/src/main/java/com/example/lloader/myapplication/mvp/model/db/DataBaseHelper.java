package com.example.lloader.myapplication.mvp.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.lloader.myapplication.mvp.model.db.DataBaseSchema.*;

/**
 * Created by Alexander Garkavenko
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "database.db";

    public DataBaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Table.NAME + "(" +
                Table.Cols.ID + " integer primary key, " +
                Table.Cols.TITLE +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
