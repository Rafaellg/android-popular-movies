package com.rafaelguimas.popularmovies.network;

import android.content.Context;
import android.util.Log;

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

    private static final String TMDB_API_KEY = "?api_key=YOUR_API_KEY";
    private static final String URL_BASE = "https://api.themoviedb.org/3";
    private static final String URL_MOVIE_POPULAR = "/movie/popular";
    private static final String URL_MOVIE_TOP_RATED = "/movie/top_rated";
    private static final String URL_MOVIE = "/movie";
    private static final String URL_VIDEOS = "/videos";
    private static final String URL_REVIEWS = "/reviews";
    public static final String URL_POSTER_BASE = "http://image.tmdb.org/t/p/w500";

    private Context context;

    public TmdbService(Context context) {
        this.context = context;
    }

    private void getMoviesFromUrl(String url, final OnMovieListRequestListener requestListener) {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Add the api key
        url = URL_BASE + url + TMDB_API_KEY;

        // request a string response asynchronously from the provided URL
        GsonRequest<MovieListRequest> request = new GsonRequest<>(url, MovieListRequest.class, null,
                new Response.Listener<MovieListRequest>() {
                    @Override
                    public void onResponse(MovieListRequest response) {
                        Log.d(TAG, "Movies found: " + response.getResults().size());
                        requestListener.onMovieListRequestSuccess((ArrayList<Movie>) response.getResults());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        requestListener.onMovieListRequestError();
                    }
                }
        );

        // add the request to the RequestQueue
        queue.add(request);
    }

    public void getPopularMovies(OnMovieListRequestListener requestListener) {
        getMoviesFromUrl(URL_MOVIE_POPULAR, requestListener);
    }

    public void getTopRatedMovies(OnMovieListRequestListener requestListener) {
        getMoviesFromUrl(URL_MOVIE_TOP_RATED, requestListener);
    }

    public void getMovieReviews(String movieId, final OnMovieReviewsRequestListener requestListener) {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Add the api key
        String url = URL_BASE + URL_MOVIE + movieId + URL_REVIEWS + TMDB_API_KEY;

        // request a string response asynchronously from the provided URL
        GsonRequest<MovieListRequest> request = new GsonRequest<>(url, MovieListRequest.class, null,
                new Response.Listener<MovieListRequest>() {
                    @Override
                    public void onResponse(MovieListRequest response) {
                        Log.d(TAG, "Reviews found: " + response.getResults().size());
                        requestListener.onMovieReviewsRequestSuccess((ArrayList<Movie>) response.getResults());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                requestListener.onMovieReviewsRequestError();
            }
        }
        );

        // add the request to the RequestQueue
        queue.add(request);
    }

    public void getMovieVideos(String movieId, final OnMovieVideosRequestListener requestListener) {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Add the api key
        String url = URL_BASE + URL_MOVIE + movieId + URL_VIDEOS + TMDB_API_KEY;

        // request a string response asynchronously from the provided URL
        GsonRequest<MovieListRequest> request = new GsonRequest<>(url, MovieListRequest.class, null,
                new Response.Listener<MovieListRequest>() {
                    @Override
                    public void onResponse(MovieListRequest response) {
                        Log.d(TAG, "Reviews found: " + response.getResults().size());
                        requestListener.onMovieVideoRequestSuccess((ArrayList<Movie>) response.getResults());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                requestListener.onMovieVideoRequestError();
            }
        }
        );

        // add the request to the RequestQueue
        queue.add(request);
    }

    public interface OnMovieListRequestListener {
        void onMovieListRequestSuccess(ArrayList<Movie> movieList);
        void onMovieListRequestError();
    }

    public interface OnMovieReviewsRequestListener {
        void onMovieReviewsRequestSuccess(ArrayList<Movie> movieList);
        void onMovieReviewsRequestError();
    }

    public interface OnMovieVideosRequestListener {
        void onMovieVideoRequestSuccess(ArrayList<Movie> movieList);
        void onMovieVideoRequestError();
    }
}
