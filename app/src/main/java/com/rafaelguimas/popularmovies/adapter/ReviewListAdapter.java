package com.rafaelguimas.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rafaelguimas.popularmovies.R;
import com.rafaelguimas.popularmovies.fragment.MovieDetailFragment.OnTrailerItemClickListener;
import com.rafaelguimas.popularmovies.model.Review;
import com.rafaelguimas.popularmovies.model.Trailer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private final List<Review> mValues;

    public ReviewListAdapter(List<Review> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvAuthor.setText(mValues.get(position).getAuthor());
        holder.tvContent.setText(mValues.get(position).getContent());
        holder.itemView.setTag(mValues.get(position).getId());
    }

    @Override
    public int getItemCount() {
        if (mValues == null) {
            return 0;
        }
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
