package com.rafaelguimas.popularmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.fragment.MovieListFragment;
import com.rafaelguimas.popularmovies.fragment.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open the movie list fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, MovieListFragment.newInstance(1))
                .commit();
    }

    @Override
    public void onMovieItemClick(DummyContent.DummyItem item) {
        Toast.makeText(this, item.content, Toast.LENGTH_SHORT).show();
    }
}
