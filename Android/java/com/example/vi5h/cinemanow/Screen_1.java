package com.example.vi5h.cinemanow;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.List;

public class Screen_1 extends AppCompatActivity
{
    //Declaring SQLite DB
    DatabaseHelper myDB;

    //Declaring Recycler view for use
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_1);
        myDB = new DatabaseHelper(this);
        initRecyclerView();
    }

    private void initRecyclerView(){
        //Creates a List and attach information retrieved from the Database
        List<ScreenModel> screen1Info = myDB.getScreenOneInfo();

        //Logging for Error handling
        for (ScreenModel screenModel : screen1Info){
            Log.d("Name: ", screenModel.getMovieName());
            Log.d("Show Time: ", screenModel.getShowTime());
        }

        //Pointing the Recycler View to the corresponding UI element
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewScreen1);

        //Attaching data to the Adapter
        ScreenAdapter adapter = new ScreenAdapter(this, screen1Info);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }
}
