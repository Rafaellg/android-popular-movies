package com.rafaelguimas.popularmovies.activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.fragment.MovieDetailFragment;
import com.rafaelguimas.popularmovies.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    public static String EXTRA_MOVIE = "movie";

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (getIntent() != null) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        }

        // Open the detail fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_detail, MovieDetailFragment.newInstance(mMovie))
                .commit();
    }

}
