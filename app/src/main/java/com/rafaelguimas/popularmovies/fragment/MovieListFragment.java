package com.rafaelguimas.popularmovies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.activity.MainActivity;
import com.rafaelguimas.popularmovies.adapter.MovieListAdapter;
import com.rafaelguimas.popularmovies.model.Movie;
import com.rafaelguimas.popularmovies.network.TmdbService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListFragment extends Fragment implements TmdbService.OnMovieListRequestCompleteListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_ORDER_BY = "order-by";
    private static final String KEY_MOVIE_LIST = "movie-list";

    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;
    @BindView(R.id.cl_empty)
    ConstraintLayout clEmpty;

    private int mColumnCount;
    private int mOrderBy;
    private TmdbService mTmdbService;
    private OnMovieItemClickListener mListener;
    private ArrayList<Movie> mMovieList;

    public enum EnumOrderOptions {
        POPULAR(1), TOP_RATED(2);

        private int value;

        EnumOrderOptions(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public MovieListFragment() {
    }

    @SuppressWarnings("unused")
    public static MovieListFragment newInstance(int columnCount, int orderBy) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_ORDER_BY, orderBy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activate menu
        setHasOptionsMenu(true);

        // Get arguments from newInstance
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mOrderBy = getArguments().getInt(ARG_ORDER_BY);
        }

        // Create service
        mTmdbService = new TmdbService(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // Activate the butterknife
        ButterKnife.bind(this, view);

        setupView();

        // Set the adapter
        if (mColumnCount <= 1) {
            rvMovieList.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            rvMovieList.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }

        // Set the movie list
        if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_MOVIE_LIST)) {
            loadMovies();
        } else {
            onMovieListRequestSuccess(savedInstanceState.<Movie>getParcelableArrayList(KEY_MOVIE_LIST));
        }

        return view;
    }

    private void setupView() {
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(mOrderBy == EnumOrderOptions.POPULAR.getValue()? R.string.title_popular_movies : R.string.title_top_rated_movies);
    }

    private void loadMovies() {
        // Load movies from given order
        if (mOrderBy == EnumOrderOptions.POPULAR.getValue()) {
            mTmdbService.getPopularMovies(this);
        } else {
            mTmdbService.getTopRatedMovies(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIE_LIST, mMovieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_popular:
                mOrderBy = EnumOrderOptions.POPULAR.getValue();
                mTmdbService.getPopularMovies(this);
                break;
            case R.id.action_order_top_rated:
                mOrderBy = EnumOrderOptions.TOP_RATED.getValue();
                mTmdbService.getTopRatedMovies(this);
                break;
        }

        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieItemClickListener) {
            mListener = (OnMovieItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.bt_try_again)
    public void onTryAgainClick() {
        loadMovies();
    }

    @Override
    public void onMovieListRequestSuccess(ArrayList<Movie> movieList) {
        // Update the view
        setupView();

        // Save movie list
        mMovieList = movieList;

        // Set the adapter
        rvMovieList.setAdapter(new MovieListAdapter(movieList, mListener));
    }

    @Override
    public void onMovieListRequestError() {
        clEmpty.setVisibility(View.VISIBLE);
        rvMovieList.setVisibility(View.GONE);
    }

    public interface OnMovieItemClickListener {
        void onMovieItemClick(Movie item);
    }
}
