package com.rafaelguimas.popularmovies.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.fragment.MovieListFragment.OnMovieItemClickListener;
import com.rafaelguimas.popularmovies.model.Movie;
import com.rafaelguimas.popularmovies.network.TmdbService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private static final String URL_POSTER_BASE = "http://image.tmdb.org/t/p/w500";

    private final List<Movie> mValues;
    private final OnMovieItemClickListener mListener;

    public MovieListAdapter(List<Movie> items, OnMovieItemClickListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvMovieTitle.setText(mValues.get(position).getOriginalTitle());

        String releaseDate = mValues.get(position).getReleaseDate();
        holder.tvMovieReleaseDate.setText(releaseDate.isEmpty()? "" : releaseDate.substring(0,4));

        String posterUrl = URL_POSTER_BASE + mValues.get(position).getPosterPath().toString();
//        Picasso.with(holder.itemView.getContext()).load(posterUrl).placeholder(R.mipmap.ic_launcher).into(holder.ivMoviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onMovieItemClick(mValues.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
