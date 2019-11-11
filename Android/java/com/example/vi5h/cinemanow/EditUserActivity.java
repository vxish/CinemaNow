package com.example.vi5h.cinemanow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditUserActivity extends AppCompatActivity {

    //Declaring SQLite DB
    DatabaseHelper myDB;

    //Declaring UI Elements
    private Button btnDel, btnSave;
    private EditText editText;
    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        myDB = new DatabaseHelper(this);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDelete);
        editText = (EditText) findViewById(R.id.editName);

        //Getting the intent extra from Admin List
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedName = receivedIntent.getStringExtra("name");
        editText.setText(selectedName);

        //On click Listener for Save Button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editText.getText().toString();
                if(!user.equals("")){
                    myDB.updateName(user, selectedID, selectedName);
                    Toast.makeText(EditUserActivity.this,
                            "User details updated!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditUserActivity.this, AdminActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(EditUserActivity.this,
                            "You must enter a name!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //On click Listener for Delete Button
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteUser(selectedID, selectedName);
                editText.setText("");
                Toast.makeText(EditUserActivity.this,
                        "User: "+ selectedName + " deleted from the Database.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditUserActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }
}
