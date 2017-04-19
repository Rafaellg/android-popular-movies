package com.rafaelguimas.popularmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.Toast;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.fragment.MovieListFragment;
import com.rafaelguimas.popularmovies.fragment.dummy.DummyContent;
import com.rafaelguimas.popularmovies.model.Movie;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open the movie list fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, MovieListFragment.newInstance(3, MovieListFragment.OrderBy.POPULAR.getValue()))
                .commit();
    }

    @Override
    public void onMovieItemClick(Movie item) {
        Toast.makeText(this, item.getOriginalTitle(), Toast.LENGTH_SHORT).show();
    }
}
