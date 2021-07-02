package com.android.movies.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.movies.Model.MoviesCall;
import com.android.movies.Model.MoviesModel;
import com.android.movies.Model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesViewModel extends ViewModel {
    public MutableLiveData<List<Result>> moviesMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> failure = new MutableLiveData<>();

    public void getMovies() {
        MoviesCall.getINSTANCE().getMovies().enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                moviesMutableLiveData.setValue(response.body().getResults());
            }
            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                failure.setValue("Error");
            }
        });
    }
}
