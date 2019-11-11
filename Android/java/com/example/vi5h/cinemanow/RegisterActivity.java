package com.example.vi5h.cinemanow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    //Declaring SQLite DB
    DatabaseHelper myDB;

    //Declaring UI elements
    EditText  name, username, password1, password2 ;
    Button btnReg;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    UserModel user = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDB = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.txtRegName);
        username = (EditText) findViewById(R.id.txtRegUsername);
        password1 = (EditText) findViewById(R.id.txtRegPass1);
        password2 = (EditText) findViewById(R.id.txtRegPass2);
        radioGroup = (RadioGroup) findViewById(R.id.rdoUserType);
        btnReg = (Button) findViewById(R.id.btnRegUser);
        Register();
    }

    public void Register(){
        btnReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                String strName = name.getText().toString();
                String strUsername = username.getText().toString();
                String strPass1 = password1.getText().toString();
                String strPass2 = password2.getText().toString();
                String strUserType = radioButton.getText().toString();
                user.setName(strName);
                user.setUsername(strUsername);
                user.setPass(strPass1);
                user.setUserType(strUserType);
                boolean passOk = strPass1.equals(strPass2);
                boolean nullEntries = strName.length() == 0 || strUsername.length() == 0 ||
                        strPass1.length() == 0 || strPass2.length() == 0;

                //Checks if the text fields are empty
                EditText[] allFields = {name, username, password1, password2};
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

                if (nullEntries)
                {
                    Toast.makeText(RegisterActivity.this,
                            "Must fill in all the Fields!", Toast.LENGTH_LONG).show();
                }
                else if(passOk)
                {
                    myDB.registerUser(user);
                    Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(intent);
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,
                            "Registration Failed! Passwords don't match!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
