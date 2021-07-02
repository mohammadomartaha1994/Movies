package com.android.movies.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.android.movies.Model.Result;
import com.android.movies.R;
import com.android.movies.Rest.EndPoints;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> implements Filterable {
    private List<Result> moviesList = new ArrayList<>();
    private List<Result> moviesListFilter;
    private OnItemClickListener mListener;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position + 2;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(moviesList.get(position).getOriginalTitle());
        holder.movieRateNum.setText("("+moviesList.get(position).getVoteAverage()+" / "+"10)");
        Picasso.get().load(EndPoints.IMAGE_MOVIES+moviesList.get(position).getPosterPath()).into(holder.movieImage);
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setList(List<Result> uList) {
        this.moviesList = uList;
        moviesListFilter = new ArrayList<>(moviesList);
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle, movieRateNum;
        ImageView  movieImage;
        CardView movieItem;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieRateNum = itemView.findViewById(R.id.movieRateNum);
            movieImage = itemView.findViewById(R.id.movieImage);
            movieItem = itemView.findViewById(R.id.movieItem);
            movieItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    if (mListener != null && index != RecyclerView.NO_POSITION){
                        mListener.OnItemClick(moviesList.get(index));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{void OnItemClick(Result result);}
    public void OnItemClickListener(OnItemClickListener listerner){mListener=listerner;}

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Result> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(moviesListFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Result item : moviesListFilter) {
                    if (item.getOriginalTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            moviesList.clear();
            moviesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
