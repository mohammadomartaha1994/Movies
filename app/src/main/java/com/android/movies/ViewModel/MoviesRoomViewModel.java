package com.android.movies.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.movies.Model.Result;
import com.android.movies.Room.MovieRepository;

import java.util.List;

public class MoviesRoomViewModel extends AndroidViewModel {
    private MovieRepository mRepository;
    private LiveData<List<Result>> mAllMovies;
    public MoviesRoomViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mAllMovies = mRepository.getAllMovies();

    }
    public void insert(Result result) {
        mRepository.insert(result);
    }
    public LiveData<List<Result>> getAllMovies() {
        return mAllMovies;
    }
    public LiveData<Result> getMovieItem(String id) {
        return mRepository.getMovieItem(id);
    }
}
