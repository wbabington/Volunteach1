package com.example.welsley.volunteach;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonRegister;
    private EditText fullName, userName, email, passWord;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Singleton instantiation of the database
        db = DatabaseHelper.getInstance(this);

        //initialize EditText values
        fullName = (EditText)findViewById(R.id.editPersonName);
        userName = (EditText)findViewById(R.id.editUsername);
        email = (EditText)findViewById(R.id.editEmail);
        passWord = (EditText)findViewById(R.id.editPassword);
        //initialize Button value
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        //setOnClickListener for button
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonRegister){
            registerUser();
        }
    }

    public void registerUser(){
        String r_fullName =fullName.getText().toString();
        String r_userName = userName.getText().toString();
        String r_email = email.getText().toString();
        String r_passWord = passWord.getText().toString();

        if(r_fullName.isEmpty() || r_userName.isEmpty() || r_email.isEmpty() || r_passWord.isEmpty()){
            displayToast("One or more fields is empty");
        }
        else{
            db.addUser(r_userName, r_passWord, r_fullName, r_email);
            //TO DO: redirect user to MainActivity
            displayToast("You have been registered");
            finish();
        }
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
