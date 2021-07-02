package com.android.movies.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.android.movies.Model.Result;
import com.android.movies.R;
import com.android.movies.ViewModel.MoviesRoomViewModel;
import com.android.movies.ViewModel.MoviesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MoviesViewModel moviesViewModel;
    MoviesRoomViewModel moviesRoomViewModel;
    RecyclerView recyclerView;
    MoviesAdapter adapter;
    EditText editTextSearch;
    ProgressBar loading;
    ImageButton list_btn,bookmark_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =findViewById(R.id.recyclerViewSearchResults);
        loading = (ProgressBar)findViewById(R.id.progressBar);
        list_btn = findViewById(R.id.list_btn);
        bookmark_btn= findViewById(R.id.bookmark_btn);
        adapter = new MoviesAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        editTextSearch = (EditText)findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        adapter.OnItemClickListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Result result) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                intent.putExtra("movie_id",result.getId());
                intent.putExtra("backdrop_path",result.getBackdropPath());
                intent.putExtra("popularity",result.getPopularity());
                intent.putExtra("original_language",result.getOriginalLanguage());
                intent.putExtra("release_date",result.getReleaseDate());
                intent.putExtra("overview",result.getOverview());
                intent.putExtra("vote_average",result.getVoteAverage());
                intent.putExtra("vote_count",result.getVoteCount());
                intent.putExtra("original_title",result.getOriginalTitle());
                intent.putExtra("poster_path",result.getPosterPath());
                startActivity(intent);
            }
        });
        getFromAPI();
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFromAPI();
            }
        });
        bookmark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFromRoom();
            }
        });

    }

    private void getFromRoom() {
        moviesRoomViewModel = new ViewModelProvider(MainActivity.this).get(MoviesRoomViewModel.class);
        getMoviesRoom();
    }
    private void getFromAPI(){
        moviesViewModel = new ViewModelProvider(MainActivity.this).get(MoviesViewModel.class);
        moviesViewModel.getMovies();
        getMovies();
    }

    private void getMovies(){
        moviesViewModel.moviesMutableLiveData.observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                adapter.setList(results);
                loading.setVisibility(View.GONE);
            }
        });
    }
    private void getMoviesRoom(){
        moviesRoomViewModel.getAllMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                adapter.setList(results);
                loading.setVisibility(View.GONE);
            }
        });
    }

}