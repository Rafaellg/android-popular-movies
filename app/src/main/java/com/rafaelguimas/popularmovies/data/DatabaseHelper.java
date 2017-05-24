package com.rafaelguimas.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rafael on 17/05/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "popular_movies";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEATHER_TABLE =
                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                        MovieContract.MovieEntry._ID                        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID            + " INTEGER NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_TITLE               + " TEXT NOT NULL, "                    +
                        MovieContract.MovieEntry.COLUMN_POSTER_PATH         + " TEXT NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_RELEASE_DATE        + " TEXT NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_ADULT               + " INTEGER NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_OVERVIEW            + " TEXT NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE      + " TEXT NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE   + " TEXT NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_BACKDROP_PATH       + " TEXT NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_POPULARITY          + " FLOAT NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_VOTE_COUNT          + " INTEGER NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE        + " FLOAT NOT NULL, "                 +
                        MovieContract.MovieEntry.COLUMN_VIDEO               + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
