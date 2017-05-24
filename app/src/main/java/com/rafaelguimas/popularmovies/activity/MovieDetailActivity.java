package com.rafaelguimas.popularmovies.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.fragment.MovieDetailFragment;
import com.rafaelguimas.popularmovies.model.Movie;
import com.rafaelguimas.popularmovies.model.Trailer;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailFragment.OnTrailerItemClickListener {

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

    @Override
    public void onTrailerItemClick(Trailer trailer) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, R.string.alert_youtube_not_found, Toast.LENGTH_SHORT).show();
        }
    }
}
