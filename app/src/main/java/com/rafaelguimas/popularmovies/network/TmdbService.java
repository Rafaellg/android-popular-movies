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

/**
 * Created by Rafael on 09/04/2017.
 */

public class TmdbService {

    private static final String TAG = "TmdbService";

    public static final String TMDB_API_KEY = "&api_key=91caf1cd00959c2c48d415ecb5f1d397";
    public static final String URL_BASE = "https://api.themoviedb.org/3/";
    public static final String URL_DISCOVER = "discover/movie?";
    public static final String PARAMETER_SORD_POPULARITY_DES = "sort_by=popularity.des";
    public static final String PARAMETER_SORD_VOTE_AVERAGED_DES = "sort_by=vote_average.desc";

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
                        completeListener.OnMovieListRequestComplete(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(context, "Error on getting movie list", Toast.LENGTH_SHORT).show();

            }
        });

        // add the request to the RequestQueue
        queue.add(request);
    }

    public void getMoviesByPopularity(OnMovieListRequestCompleteListener completeListener) {
        getMoviesFromUrl(URL_BASE + URL_DISCOVER + PARAMETER_SORD_POPULARITY_DES, completeListener);
    }

    public interface OnMovieListRequestCompleteListener {
        void OnMovieListRequestComplete(MovieListRequest request);
    }
}
