package com.example.vi5h.cinemanow;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //Declaring SQLite DB
    DatabaseHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDB = new DatabaseHelper(this);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnReg = (Button) findViewById(R.id.btnReg);
        final EditText username = (EditText) findViewById(R.id.txtUsername);
        final EditText password = (EditText) findViewById(R.id.txtPassword);

        //On click Listener for Login button
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String strUsername = username.getText().toString();
                String strPass = password.getText().toString();

                //If the Username and Password text fields are empty
                if (strUsername.length() == 0 & strPass.length() == 0)
                {
                    Toast.makeText(LoginActivity.this,
                            "Cannot have Username and Password fields empty!", Toast.LENGTH_LONG).show();
                    //Checks if the text fields are empty
                    EditText[] allFields = {username, password};

                    //Reds out the text fields, using for loop to check if the fields are still null
                    for(EditText edit : allFields)
                    {
                        if(TextUtils.isEmpty(edit.getText()))
                        {
                            // EditText was empty
                            for (int i = 0; i < allFields.length; i++) {
                                EditText currentField = allFields[i];
                                currentField.setError("This field is Required!");
                                currentField.requestFocus();
                            }
                        }
                    }
                }
                else
                {
                    /*Sets pass as a string from the data retrieved from DB along
                    side the username passed in as a parameter */
                    String pass = myDB.verifyLogin(strUsername);
                    if (strPass.equals(pass))
                    {
                        //Once the Username and Password is correct User-type is retrieved
                        Cursor cursor = myDB. fetchUserType(strUsername);
                        Intent intent = null;
                        switch (cursor.getString(0))
                        {
                            case "Admin":
                                intent = new Intent(LoginActivity.this, AdminActivity.class);
                                break;

                            case "Customer":
                                intent = new Intent(LoginActivity.this, LocationActivity.class);
                                Toast.makeText(LoginActivity.this,
                                        "Login Successful.", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(intent);

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,
                                "The Username or Password entered is invalid! Please try again.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //On click Listener for Register button
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }


}
