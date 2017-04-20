package com.rafaelguimas.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.fragment.MovieListFragment;
import com.rafaelguimas.popularmovies.model.Movie;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open the movie list fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, MovieListFragment.newInstance(3, MovieListFragment.EnumOrderOptions.POPULAR.getValue()))
                .commit();
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}
