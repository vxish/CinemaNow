package com.example.vi5h.cinemanow;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity
{
    //Declaring SQLite DB
    DatabaseHelper myDB;

    //Declaring the list view
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        myDB = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.locationList);
        populateListview();
    }

    private void populateListview()
    {
        //Retrieves the cursor data from DB and adds the data to list view
        Cursor data = myDB.getLocation();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext())
        {
            listData.add(data.getString(1));
        }
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        //On click listener for items inside the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String area = adapterView.getItemAtPosition(i).toString();
                Cursor data = myDB.getLocationId(area);
                int areaID = -1;
                while(data.moveToNext()){
                    areaID = data.getInt(0);
                }
                if (areaID > -1){
                    Toast.makeText(LocationActivity.this, "Welcome to CinemaNow, " + area, Toast.LENGTH_SHORT).show();

                    //Re-Directs to Coventry Cinema
                    if (areaID == 1)
                    {
                        Intent movieIntent = new Intent(LocationActivity.this, CoventryScreen.class);
                        movieIntent.putExtra("ID", areaID);
                        movieIntent.putExtra("Name", area);
                        startActivity(movieIntent);
                    }
                    //Re-Directs to London Cinema
                    if (areaID == 2)
                    {
                        Intent movieIntent = new Intent(LocationActivity.this, LondonScreen.class);
                        movieIntent.putExtra("ID", areaID);
                        movieIntent.putExtra("Name", area);
                        startActivity(movieIntent);
                    }
                    //Re-Directs to Worcester Cinema
                    if (areaID == 3)
                    {
                        Intent movieIntent = new Intent(LocationActivity.this, WorcesterScreen.class);
                        movieIntent.putExtra("ID", areaID);
                        movieIntent.putExtra("Name", area);
                        startActivity(movieIntent);
                    }

                } else {
                    Toast.makeText(LocationActivity.this, " No ID associated with that Area!" + areaID, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
