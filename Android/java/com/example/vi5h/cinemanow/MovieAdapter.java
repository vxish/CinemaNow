package com.example.vi5h.cinemanow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

//This Class is used to declare and attach data to various UI elements for list of 3 movies
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
{

    private Context con;
    private List<MovieModel> movieList;
    private ArrayList<String> mPosters = new ArrayList<>();

    public MovieAdapter(Context con, List<MovieModel> movieList, ArrayList<String> posters) {
        this.con = con;
        this.movieList = movieList;
        mPosters = posters;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        Glide.with(con).asBitmap().load(mPosters.get(position)).into(holder.imageView);
        final MovieModel movie = movieList.get(position);
        holder.textViewTitle.setText(movie.getName());
        holder.textViewDuration.setText(movie.getDuration());

        //Code to view more info about the movie
        holder.parentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, infomovie.class);
                intent.putExtra("poster_URL", mPosters.get(position));
                intent.putExtra("movie_Name", movie.getName());
                intent.putExtra("movie_Plot", movie.getPlot());
                con.startActivity(intent);
            }
        });

        //Code to go to screen 1
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, Screen_1.class);
                intent.putExtra("movie_ID", movie.getId());
                con.startActivity(intent);
            }
        });
        //Code to go to screen 2
        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, Screen_2.class);
                intent.putExtra("movie_ID", movie.getId());
                con.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textViewTitle, textViewDuration;
        RelativeLayout parentLayout;
        Button button1, button2;

        public MovieViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewDuration = (TextView) itemView.findViewById(R.id.textViewDuration);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
            button1 = (Button) itemView.findViewById(R.id.btnScreen1);
            button2 = (Button) itemView.findViewById(R.id.btnScreen2);
        }
    }
}
