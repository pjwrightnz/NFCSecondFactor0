package com.example.paul.nfcsecondfactor0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterstrationActivity extends AppCompatActivity {

    //registration activity for creating a new account

    Button registerButton;
    EditText userIDEditText, passwordEditText, reenterPasswordEditText, emailEditText;
    ImageView nfcLogo, bTLogo, yorkLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerstration);

        //set logos at top of activity
        bTLogo = (ImageView) findViewById(R.id.btLogo);
        bTLogo.setImageResource(R.drawable.bt_logo);

        yorkLogo = (ImageView) findViewById(R.id.uniLogo);
        yorkLogo.setImageResource(R.drawable.unilogo);

        //set NFC logo
        nfcLogo = (ImageView) findViewById(R.id.nfcLogo);
        nfcLogo.setImageResource(R.drawable.nfcsign);

    }
}
