package com.rafaelguimas.popularmovies.fragment;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.Toast;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.model.Movie;
import com.rafaelguimas.popularmovies.model.MovieListRequest;
import com.rafaelguimas.popularmovies.network.TmdbService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListFragment extends Fragment implements TmdbService.OnMovieListRequestCompleteListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_ORDER_BY = "order-by";
    private static final String KEY_MOVIE_LIST = "movie-list";

    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;

    private int mColumnCount;
    private int mOrderBy;
    private TmdbService mTmdbService;
    private OnMovieItemClickListener mListener;
    private ArrayList<Movie> mMovieList;

    public enum OrderBy {
        POPULAR(1), TOP_RATED(2);

        private int value;

        OrderBy(int value) {
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

        if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_MOVIE_LIST)){
            // Load movies from given order
            if (mOrderBy == OrderBy.POPULAR.getValue()) {
                mTmdbService.getPopularMovies(this);
            } else {
                mTmdbService.getTopRatedMovies(this);
            }
        } else {
            onMovieListRequestSuccess(savedInstanceState.<Movie>getParcelableArrayList(KEY_MOVIE_LIST));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // Set the butterknife
        ButterKnife.bind(this, view);

        // Set the adapter
        if (mColumnCount <= 1) {
            rvMovieList.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            rvMovieList.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIE_LIST, mMovieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_popular:
                mTmdbService.getPopularMovies(this);
                break;
            case R.id.action_order_top_rated:
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

    @Override
    public void onMovieListRequestSuccess(ArrayList<Movie> movieList) {
        // Save movie list
        mMovieList = movieList;

        // Set the adapter
        rvMovieList.setAdapter(new MovieListAdapter(movieList, mListener));
    }

    @Override
    public void onMovieListRequestError() {
        Toast.makeText(getContext(), "Error on getting movie list", Toast.LENGTH_SHORT).show();
    }

    public interface OnMovieItemClickListener {
        void onMovieItemClick(Movie item);
    }
}
