package com.rafaelguimas.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.fragment.MovieDetailFragment;
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
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        // Open the detail fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, MovieDetailFragment.newInstance(movie))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
