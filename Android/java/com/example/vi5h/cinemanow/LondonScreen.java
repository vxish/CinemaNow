package com.example.vi5h.cinemanow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class LondonScreen extends AppCompatActivity {

    //Declaring SQLite DB
    DatabaseHelper myDB;

    //Declaring the Recycler View for Use
    RecyclerView recyclerView;

    //Declaring a String Array to hold poster URLS
    private ArrayList<String> posterURLS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new DatabaseHelper(this);
        setContentView(R.layout.activity_london_screen);
        initPosterBitmaps();
    }

    //Adding URLS to the Array as Strings
    private void initPosterBitmaps(){
        //Avengers
        posterURLS.add("https://cdn.empireonline.com/jpg/70/0/0/4000/4000/aspectfit/0/0/0/0/0/0/c/articles/5ac5f157d325f7f7065e76b6/avengers-infinity-war-imax.jpg");

        //Red
        posterURLS.add("https://ia.media-imdb.com/images/M/MV5BMTA3MDkxOTc4NDdeQTJeQWpwZ15BbWU4MDAxNzgyNTQz._V1_SY1000_CR0,0,674,1000_AL_.jpg");

        //Hiest
        posterURLS.add("https://assets.voxcinemas.com/posters/P_HO00005143.jpg");

        initRecyclerView();
    }

    private void initRecyclerView(){
        //Creates a List and attach information retrieved from the Database
        List<MovieModel> allMovies = myDB.getMovieInfo();

        //Logging for Error handling
        for (MovieModel movieModel : allMovies){
            Log.d("Movies: ", movieModel.getName());
            Log.d("Duration: ", movieModel.getDuration());
            Log.d("Plot: ", movieModel.getPlot());
        }
        //Pointing the Recycler View to the corresponding UI element
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLdn);

        //Attaching data to the Adapter
        MovieAdapter adapter = new MovieAdapter(this, allMovies, posterURLS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }
}
