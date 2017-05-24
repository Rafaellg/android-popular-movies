package com.rafaelguimas.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.data.MovieContract;
import com.rafaelguimas.popularmovies.fragment.MovieListFragment;
import com.rafaelguimas.popularmovies.model.Movie;
import com.rafaelguimas.popularmovies.network.TmdbService;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rafael on 24/05/2017.
 */

public class MovieListCursorAdapter extends RecyclerView.Adapter<MovieListCursorAdapter.ViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    private MovieListFragment.OnMovieItemClickListener mListener;

    public MovieListCursorAdapter(Context context, MovieListFragment.OnMovieItemClickListener requestListener) {
        mContext = context;
        mListener = requestListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_movie_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        int originalTitleIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        int posterPathIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        int releaseDateIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);

        mCursor.moveToPosition(position);

        holder.tvMovieTitle.setText(mCursor.getString(originalTitleIndex));

        String releaseDate = mCursor.getString(releaseDateIndex);
        holder.tvMovieReleaseDate.setText(releaseDate.isEmpty() ? "" : releaseDate.substring(0, 4));

        String posterUrl = TmdbService.URL_POSTER_BASE + mCursor.getString(posterPathIndex);
        Picasso.with(holder.itemView.getContext()).load(posterUrl).placeholder(R.drawable.img_movie_placeholder).into(holder.ivMoviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onMovieItemClick(getMovieFromCursor(holder.getAdapterPosition()));
                }
            }
        });

    }

    private Movie getMovieFromCursor(int position) {
        mCursor.moveToPosition(position);

        int idIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        int titleIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
        int posterPath = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        int releaseDateIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        int adultIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ADULT);
        int overviewIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        int originalTitleIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        int originalLanguageIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE);
        int backdropPathIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH);
        int popularityIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY);
        int voteCountIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT);
        int voteAverageIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        int videoIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VIDEO);

        Movie movie = new Movie();
        movie.setId(mCursor.getInt(idIndex));
        movie.setTitle(mCursor.getString(titleIndex));
        movie.setPosterPath(mCursor.getString(posterPath));
        movie.setReleaseDate(mCursor.getString(releaseDateIndex));
        movie.setAdult(mCursor.getInt(adultIndex) == 1);
        movie.setOverview(mCursor.getString(overviewIndex));
        movie.setOriginalTitle(mCursor.getString(originalTitleIndex));
        movie.setOriginalLanguage(mCursor.getString(originalLanguageIndex));
        movie.setBackdropPath(mCursor.getString(backdropPathIndex));
        movie.setPopularity(mCursor.getFloat(popularityIndex));
        movie.setVoteCount(mCursor.getInt(voteCountIndex));
        movie.setVoteAverage(mCursor.getFloat(voteAverageIndex));
        movie.setVideo(mCursor.getInt(videoIndex) == 1);

        return movie;
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_movie_title)
        TextView tvMovieTitle;
        @BindView(R.id.tv_movie_release_date)
        TextView tvMovieReleaseDate;
        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
