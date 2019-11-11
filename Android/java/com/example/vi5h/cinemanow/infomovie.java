package com.example.vi5h.cinemanow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class infomovie extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movieinfo);
        getIntents();
    }

    //Gets intents passed as extras from the Movie selection screens
    private void getIntents()
    {
        if(getIntent().hasExtra("poster_URL") && getIntent().hasExtra("movie_Name")
                && getIntent().hasExtra("movie_Plot"))
        {
            String posterURL = getIntent().getStringExtra("poster_URL");
            String movieName = getIntent().getStringExtra("movie_Name");
            String moviePlot = getIntent().getStringExtra("movie_Plot");
            attachPoster(posterURL, movieName, moviePlot);
        }
    }

    //Attach data from the Intents
    private void attachPoster(String posterURL, String movieName, String moviePlot)
    {
        TextView  name = (TextView) findViewById(R.id.txtMovieName);
        name.setText(movieName);
        TextView plot = (TextView) findViewById(R.id.txtPlot);
        plot.setText(moviePlot);
        ImageView poster = (ImageView) findViewById(R.id.moviePoster);
        Glide.with(this).asBitmap().load(posterURL).into(poster);

    }
}
