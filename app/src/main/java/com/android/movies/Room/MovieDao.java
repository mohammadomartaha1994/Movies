package com.android.movies.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.android.movies.Model.Result;

import java.util.List;
@Dao
public interface MovieDao {
    @Insert
    void insert(Result result);


    @Query("SELECT * From Result ORDER BY id DESC ")
    LiveData<List<Result>> getAllMovies();

    @Query("SELECT * FROM Result WHERE id=:id ")
    LiveData<Result> getMovie(String id);

}