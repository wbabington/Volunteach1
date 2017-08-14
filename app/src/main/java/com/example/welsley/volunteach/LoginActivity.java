package com.example.welsley.volunteach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView volunteachLogo;
    private Button buttonLogin, buttonRegister;
    private EditText editUsername, editPassword;
    private DatabaseHelper db;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Singleton instantiation of the database
        db = DatabaseHelper.getInstance(this);

        session = new Session(this);

        volunteachLogo = (ImageView)findViewById(R.id.volunteach_logo);
        editUsername = (EditText)findViewById(R.id.editUsername);
        editPassword = (EditText)findViewById(R.id.editPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        if(session.Login()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonLogin:
                login();
                break;
            case R.id.buttonRegister:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:

        }
    }

    private void login(){
        String l_userName = editUsername.getText().toString();
        String l_passWord = editPassword.getText().toString();

        if(db.getUser(l_userName, l_passWord)){
            session.setLogin(l_userName, l_passWord);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        else if(!db.getUser(l_userName, l_passWord)){
            Toast.makeText(getApplicationContext(), "User credentials do not exist", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Username or password is incorrect", Toast.LENGTH_LONG).show();
        }
    }
}
