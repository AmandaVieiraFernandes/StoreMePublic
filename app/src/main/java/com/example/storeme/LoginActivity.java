package com.example.storeme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button login_button;
    private int counter;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        counter = 3;
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);

        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter>1) {
                    if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("caller", "LoginActivity");
                        startActivity(i);
                        finish();
                    } else {
                        counter--;
                        Toast.makeText(getApplicationContext(), "Wrong username or password (" + counter + " trials left)", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Account blocked, please contact admin", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void onSignUpClick(View view) {
        Toast.makeText(getApplicationContext(), "Sign up here", Toast.LENGTH_SHORT).show();
    }
}
