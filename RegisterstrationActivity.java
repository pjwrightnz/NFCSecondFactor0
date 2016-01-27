package com.example.paul.nfcsecondfactor0;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterstrationActivity extends AppCompatActivity {

    //registration activity for creating a new account

    Button createAccountButton;
    EditText userIDEditText, passwordEditText, reenterPasswordEditText, emailEditText;
    ImageView nfcLogo, bTLogo, yorkLogo, loginIcon, pwIcon, emailIcon, repwIcon;

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
        nfcLogo.setImageResource(R.drawable.nfclogo);

        //set up icons
        loginIcon = (ImageView) findViewById(R.id.userIDIcon);
        loginIcon.setImageResource(R.drawable.ic_perm_identity_black_24dp);

        pwIcon = (ImageView) findViewById(R.id.passwordIcon);
        pwIcon.setImageResource(R.drawable.ic_lock_black_24dp);

        repwIcon = (ImageView) findViewById(R.id.reenterPasswordIcon);
        repwIcon.setImageResource(R.drawable.ic_lock_black_24dp);

        emailIcon = (ImageView) findViewById(R.id.emailIcon);
        repwIcon.setImageResource(R.drawable.ic_email_black_24dp);

        //setup editTexts
        userIDEditText = (EditText) findViewById(R.id.userIDInput);
        passwordEditText = (EditText) findViewById(R.id.passwordInput);
        reenterPasswordEditText = (EditText) findViewById(R.id.passwordInput);
        emailEditText = (EditText) findViewById(R.id.passwordInput);

        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkNull(userIDEditText.getText().toString()) || checkNull(passwordEditText.getText().toString()) || checkNull(reenterPasswordEditText.getText().toString()) || checkNull(emailEditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please complete all fields.", Toast.LENGTH_LONG).show();
                }

                if (!passwordEditText.getText().toString().equals(reenterPasswordEditText.getText().toString())) {
                    passwordEditText.setText("");
                    reenterPasswordEditText.setText("");
                    Toast.makeText(getApplicationContext(), "Your passwords do not match, please reenter.", Toast.LENGTH_LONG).show();
                }

                if (UserData.userData.containsKey(userIDEditText.getText().toString())) {
                    passwordEditText.setText("");
                    reenterPasswordEditText.setText("");
                    Toast.makeText(getApplicationContext(), "This UserID is already in use, please select a different UserID.", Toast.LENGTH_LONG).show();
                } else {

                }


            }
        });


    }

    public static Boolean checkNull(String input) {

        if (input.isEmpty()) {

            return true;
        } else {
            return false;
        }
    }
}
