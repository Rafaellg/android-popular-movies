package com.rafaelguimas.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Rafael on 24/05/2017.
 */

public class PopularMovieApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
