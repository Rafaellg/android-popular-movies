package com.rafaelguimas.popularmovies.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.activity.MainActivity;
import com.rafaelguimas.popularmovies.model.Movie;
import com.rafaelguimas.popularmovies.network.TmdbService;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class MovieDetailFragment extends Fragment {

    private static final String ARG_MOVIE = "param1";

    @BindView(R.id.iv_movie_background)
    ImageView ivMovieBackground;
    @BindView(R.id.iv_movie_poster)
    ImageView ivMoviePoster;
    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;
    @BindView(R.id.tv_movie_vote_average)
    TextView tvMovieVoteAverage;
    @BindView(R.id.tv_movie_overview)
    TextView tvMovieOverview;
    @BindView(R.id.tv_movie_release_date)
    TextView tvMovieReleaseDate;
    @BindView(R.id.tv_movie_language)
    TextView tvMovieLanguage;
    @BindView(R.id.tv_movie_popularity)
    TextView tvMoviePopularity;

    private Movie mMovie;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Activate the butterknife
        ButterKnife.bind(this, view);

        setupView();

        return view;
    }

    private void setupView() {
        // Hide the toolbar
        ((MainActivity) getActivity()).getSupportActionBar().hide();

        // Set the poster and background
        String posterUrl = TmdbService.URL_POSTER_BASE + mMovie.getPosterPath();
        Picasso.with(getContext()).load(posterUrl).placeholder(R.drawable.img_movie_placeholder).transform(new BlurTransformation(getContext(), 20)).into(ivMovieBackground);
        Picasso.with(getContext()).load(posterUrl).placeholder(R.drawable.img_movie_placeholder).into(ivMoviePoster);

        // Set the movie info
        tvMovieTitle.setText(mMovie.getOriginalTitle());
        tvMovieVoteAverage.setText(mMovie.getVoteAverage().toString());
        tvMovieOverview.setText(mMovie.getOverview());
        tvMovieReleaseDate.setText(getString(R.string.label_release_date_param, mMovie.getReleaseDate()));
        tvMovieLanguage.setText(getString(R.string.label_language_param, mMovie.getOriginalLanguage()));
        tvMoviePopularity.setText(getString(R.string.label_popularity_param, mMovie.getPopularity().toString()));
    }

    @Override
    public void onDestroyView() {
        ((MainActivity) getActivity()).getSupportActionBar().show();
        super.onDestroyView();
    }
}
