package com.example.vi5h.cinemanow;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewUsersActivity extends AppCompatActivity {

    //Declaring SQLite DB
    DatabaseHelper myDB;

    //Declaring list view
    private ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        myDB = new DatabaseHelper(this);
        mlistView = (ListView) findViewById(R.id.viewUsersList);
        populateListView();

    }

    private void populateListView(){

        //Retrieves the cursor data from DB and adds the data to list view
        Cursor data = myDB.getUsers();
        ArrayList<String> listdata = new ArrayList<>();
        while (data.moveToNext()){
            listdata.add(data.getString(1));
        }
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, listdata);
        mlistView.setAdapter(adapter);

        //On click listener for items inside of list view
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Cursor data = myDB.getUserID(name);

                //Checking if there is data passed
                int userID = -1;
                while(data.moveToNext()){
                    userID = data.getInt(0);

                    if (userID > -1){
                        Intent intent = new Intent(ViewUsersActivity.this, EditUserActivity.class);
                        intent.putExtra("id", userID);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
