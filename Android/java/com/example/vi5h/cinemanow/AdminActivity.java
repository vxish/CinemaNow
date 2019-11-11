package com.example.vi5h.cinemanow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    //Declaring SQLite DB
    DatabaseHelper myDB;

    //Declaring Buttons
    private Button btnViewU, btnViewL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        myDB = new DatabaseHelper(this);
        btnViewU = (Button) findViewById(R.id.btnViewUsers);
        btnViewL = (Button) findViewById(R.id.btnAdminLocation);


        //On click Listener for View Users Button
        btnViewU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ViewUsersActivity.class);
                startActivity(intent);
            }
        });

        //On click Listener for View Locations Button
        btnViewL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
    }
}
