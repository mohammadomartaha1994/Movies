package com.android.movies.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.movies.Model.Result;
import com.android.movies.R;
import com.android.movies.Rest.EndPoints;
import com.android.movies.ViewModel.MoviesRoomViewModel;
import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {
    String backdrop_path,original_language,release_date,overview,original_title,poster_path;
    Double vote_average,popularity;
    int vote_count,movie_id;
    ImageView t_movieImage;
    MoviesRoomViewModel moviesRoomViewModel;
    RatingBar t_movieRate;
    TextView t_original_title,t_rate,t_overview,t_release_date,t_original_language,t_popularity;
    ImageButton t_bookmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        moviesRoomViewModel = new ViewModelProvider(InfoActivity.this).get(MoviesRoomViewModel.class);
        t_original_title= findViewById(R.id.original_title);
        t_rate= findViewById(R.id.rate);
        t_overview= findViewById(R.id.overview);
        t_release_date= findViewById(R.id.release_date);
        t_original_language= findViewById(R.id.original_language);
        t_popularity= findViewById(R.id.popularity);
        t_bookmark= findViewById(R.id.bookmark);
        t_movieRate = findViewById(R.id.movieRate);
        t_movieImage = findViewById(R.id.movieImage);
        t_movieImage.setClipToOutline(true);

        Bundle information = getIntent().getExtras();
        movie_id=information.getInt("movie_id");
        backdrop_path=information.getString("backdrop_path");
        popularity=information.getDouble("popularity");
        original_language=information.getString("original_language");
        release_date=information.getString("release_date");
        overview=information.getString("overview");
        vote_average=information.getDouble("vote_average");
        vote_count=information.getInt("vote_count");
        original_title=information.getString("original_title");
        poster_path =information.getString("poster_path");

        t_original_title.setText(original_title);
        t_original_language.setText(original_language);
        t_overview.setText(overview);
        t_popularity.setText(popularity.toString());
        t_release_date.setText(release_date);
        t_rate.setText(vote_count+"("+vote_average+"/10)");
        t_movieRate.setRating((float) (vote_average/2));
        String url = EndPoints.IMAGE_MOVIES+backdrop_path;
        Log.d("pathtt",url);
        Picasso.get().load(url).into(t_movieImage);

        moviesRoomViewModel.getMovieItem(movie_id+"").observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result results) {
                if (results!=null)
                {
                    t_bookmark.setVisibility(View.GONE);
                }
                else{
                    t_bookmark.setVisibility(View.VISIBLE);
                    t_bookmark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moviesRoomViewModel.insert(new Result(movie_id,backdrop_path,popularity
                                    ,original_language,release_date,overview,vote_average,vote_count,original_title,poster_path));
                            t_bookmark.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Added to Bookmarks",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


    }
}