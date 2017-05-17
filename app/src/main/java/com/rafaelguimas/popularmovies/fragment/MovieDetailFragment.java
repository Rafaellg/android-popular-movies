package com.rafaelguimas.popularmovies.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.activity.MainActivity;
import com.rafaelguimas.popularmovies.activity.MovieDetailActivity;
import com.rafaelguimas.popularmovies.adapter.ReviewListAdapter;
import com.rafaelguimas.popularmovies.adapter.TrailerListAdapter;
import com.rafaelguimas.popularmovies.model.Movie;
import com.rafaelguimas.popularmovies.model.Review;
import com.rafaelguimas.popularmovies.model.Trailer;
import com.rafaelguimas.popularmovies.network.TmdbService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class MovieDetailFragment extends Fragment implements TmdbService.OnMovieVideosRequestListener, TmdbService.OnMovieReviewsRequestListener{

    private static final String ARG_MOVIE = "movie";

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
    @BindView(R.id.rv_trailer_list)
    RecyclerView rvTrailerList;
    @BindView(R.id.rv_review_list)
    RecyclerView rvReviewList;

    private Movie mMovie;
    private TmdbService mTmdbService;
    private OnTrailerItemClickListener mListener;

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

        // Create service and call methods
        mTmdbService = new TmdbService(getContext());
        mTmdbService.getMovieVideos(mMovie.getId().toString(), this);
        mTmdbService.getMovieReviews(mMovie.getId().toString(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Activate the butterknife
        ButterKnife.bind(this, view);

        // Set the view elements
        setupView();

        return view;
    }

    private void setupView() {
        // Hide the toolbar
        ((MovieDetailActivity) getActivity()).getSupportActionBar().hide();

        // Set the poster and background
        String posterUrl = TmdbService.URL_POSTER_BASE + mMovie.getPosterPath();
        Picasso.with(getContext()).load(posterUrl).placeholder(R.drawable.img_movie_placeholder).transform(new BlurTransformation(getContext(), 15)).into(ivMovieBackground);
        Picasso.with(getContext()).load(posterUrl).placeholder(R.drawable.img_movie_placeholder).into(ivMoviePoster);

        // Set the movie info
        tvMovieTitle.setText(mMovie.getOriginalTitle());
        tvMovieVoteAverage.setText(mMovie.getVoteAverage().toString());
        tvMovieOverview.setText(mMovie.getOverview());
        tvMovieReleaseDate.setText(getString(R.string.label_release_date_param, mMovie.getReleaseDate()));
        tvMovieLanguage.setText(getString(R.string.label_language_param, mMovie.getOriginalLanguage()));
        tvMoviePopularity.setText(getString(R.string.label_popularity_param, mMovie.getPopularity().toString()));

        // Set the trailer list
        rvTrailerList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTrailerList.setNestedScrollingEnabled(false);
        rvReviewList.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDestroyView() {
        ((MovieDetailActivity) getActivity()).getSupportActionBar().show();
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MovieDetailFragment.OnTrailerItemClickListener) {
            mListener = (MovieDetailFragment.OnTrailerItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTrailerItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMovieReviewsRequestSuccess(ArrayList<Review> reviewList) {
        ReviewListAdapter mReviewListAdapter = new ReviewListAdapter(reviewList);
        rvReviewList.setAdapter(mReviewListAdapter);
    }

    @Override
    public void onMovieReviewsRequestError() {
        Toast.makeText(getContext(), "Erro ao buscar as reviews", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMovieVideoRequestSuccess(ArrayList<Trailer> trailerList) {
        TrailerListAdapter mTrailerAdapter = new TrailerListAdapter(trailerList, mListener);
        rvTrailerList.setAdapter(mTrailerAdapter);
    }

    @Override
    public void onMovieVideoRequestError() {
        Toast.makeText(getContext(), "Erro ao buscar os trailers", Toast.LENGTH_SHORT).show();
    }

    public interface OnTrailerItemClickListener {
        void onTrailerItemClick(Trailer trailer);
    }
}
