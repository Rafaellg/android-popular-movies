package com.rafaelguimas.popularmovies.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.rafaelguimas.popularmovies.model.Movie;
import com.rafaelguimas.popularmovies.model.MovieListRequest;

import java.util.ArrayList;

/**
 * Created by Rafael on 09/04/2017.
 */

public class TmdbService {

    private static final String TAG = "TmdbService";

    public static final String TMDB_API_KEY = "&api_key=91caf1cd00959c2c48d415ecb5f1d397";
    public static final String URL_BASE = "https://api.themoviedb.org/3/";
    public static final String URL_MOVIE_POPULAR = "movie/popular?";
    public static final String URL_MOVIE_TOP_RATED = "movie/top_rated?";
    public static final String URL_POSTER_BASE = "http://image.tmdb.org/t/p/w500";

    private Context context;

    public TmdbService(Context context) {
        this.context = context;
    }

    private void getMoviesFromUrl(String url, final OnMovieListRequestCompleteListener completeListener) {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Add the api key
        url += TMDB_API_KEY;

        // request a string response asynchronously from the provided URL
        GsonRequest<MovieListRequest> request = new GsonRequest<>(url, MovieListRequest.class, null,
                new Response.Listener<MovieListRequest>() {
                    @Override
                    public void onResponse(MovieListRequest response) {
                        Log.d(TAG, "Movies found: " + response.getResults().size());
                        completeListener.onMovieListRequestSuccess((ArrayList<Movie>) response.getResults());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        completeListener.onMovieListRequestError();
                    }
                }
        );

        // add the request to the RequestQueue
        queue.add(request);
    }

    public void getPopularMovies(OnMovieListRequestCompleteListener completeListener) {
        getMoviesFromUrl(URL_BASE + URL_MOVIE_POPULAR, completeListener);
    }

    public void getTopRatedMovies(OnMovieListRequestCompleteListener completeListener) {
        getMoviesFromUrl(URL_BASE + URL_MOVIE_TOP_RATED, completeListener);
    }

    public interface OnMovieListRequestCompleteListener {
        void onMovieListRequestSuccess(ArrayList<Movie> movieList);

        void onMovieListRequestError();
    }
}
