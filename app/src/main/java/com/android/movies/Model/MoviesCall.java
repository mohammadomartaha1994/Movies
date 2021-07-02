package com.android.movies.Model;

import com.android.movies.Rest.EndPoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesCall {
    private MoviesInterface moviesInterface;
    private static MoviesCall INSTANCE;

    public MoviesCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EndPoints.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesInterface = retrofit.create(MoviesInterface.class);
    }

    public static MoviesCall getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new MoviesCall();
        }
        return INSTANCE;
    }

    public Call<MoviesModel> getMovies() {
        return moviesInterface.getMoviesModel();
    }

}
