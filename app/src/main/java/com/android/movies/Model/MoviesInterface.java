package com.android.movies.Model;

import com.android.movies.Rest.EndPoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesInterface {
    @GET(EndPoints.GET_MOVIES)
    public Call<MoviesModel> getMoviesModel();
}
