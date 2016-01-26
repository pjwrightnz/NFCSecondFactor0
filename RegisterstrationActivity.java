package com.example.paul.nfcsecondfactor0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterstrationActivity extends AppCompatActivity {

    //new comment

    Button registerButton;
    EditText userIDEditText, passwordEditText, reenterPasswordEditText, emailEditText;
    ImageView nfcLogo, bTLogo, yorkLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerstration);
    }
}
