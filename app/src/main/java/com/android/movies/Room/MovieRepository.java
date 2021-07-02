package com.android.movies.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.android.movies.Model.Result;

import java.util.List;

public class MovieRepository {
    private MovieDao mMovieDao;
    private LiveData<List<Result>> getAllMovies;
    public MovieRepository(Application app)
    {
        MovieRoomDb db = MovieRoomDb.getInstance(app);
        mMovieDao = db.movieeDao();
        getAllMovies = mMovieDao.getAllMovies();
    }

    public void insert(Result result)
    {
        new InsertAsyncTask(mMovieDao).execute(result);
    }
    public LiveData<List<Result>> getAllMovies()
    {
        return getAllMovies;
    }

    private static class InsertAsyncTask extends AsyncTask<Result, Void, Void> {
        private MovieDao mMovieDao;
        public InsertAsyncTask(MovieDao moviesDao)
        {
            mMovieDao = moviesDao;
        }
        @Override
        protected Void doInBackground(Result... results) {
            mMovieDao.insert(results[0]);
            return null;
        }
    }
    public LiveData<Result> getMovieItem(String id) {
        return mMovieDao.getMovie(id);
    }

}

