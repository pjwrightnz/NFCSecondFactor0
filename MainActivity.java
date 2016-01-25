package com.example.paul.nfcsecondfactor0;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView btLogo;
    ImageView yorkLogo;
    ImageView nfcLogo;
    ImageView loginIcon;
    ImageView pwIcon;
    Boolean loginAccepted = false;
    EditText userIDInput;
    EditText passwordInput;
    Button loginButton;

    private NfcAdapter loginNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getActionBar().setIcon(R.mipmap.ic_launcher);

        // assign logos to image views
        btLogo = (ImageView) findViewById(R.id.btLogo);
        btLogo.setImageResource(R.drawable.bt_logo);

        yorkLogo = (ImageView) findViewById(R.id.uniLogo);
        yorkLogo.setImageResource(R.drawable.unilogo);

        //assign logos for login and password
        loginIcon = (ImageView) findViewById(R.id.userIDIcon);
        loginIcon.setImageResource(R.drawable.ic_perm_identity_black_24dp);

        pwIcon = (ImageView) findViewById(R.id.passwordIcon);
        pwIcon.setImageResource(R.drawable.ic_lock_black_24dp);

        //asign NFC logo
        nfcLogo = (ImageView) findViewById(R.id.nfcLogo);
        nfcLogo.setImageResource(R.drawable.nfclogo);

        //assign UserID and Password edit texts
        userIDInput = (EditText) findViewById(R.id.userIDInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        //assign Buttons
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginDetails();
                showToast();
            }
        });

        //  nfc code
        loginNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //  check if there is an NFC capability of the phone
        if (loginNfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC. Second Factor Authentication unavailable", Toast.LENGTH_LONG).show();
        }
// check NFC is enabled
        if (!loginNfcAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Please activate NFC to allow second factor authentication and press Back to return to the application.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }

       // loginNfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_B | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,null);
    }


    private void showToast() {
        Context context = getApplicationContext();
        CharSequence loginToastMessage = "Login failed, please try again.";
        if (loginAccepted) {
            loginToastMessage = "Login Successful.";
            userIDInput.setText("");
            passwordInput.setText("");
        }
        final Toast toastLogin = Toast.makeText(context, loginToastMessage, Toast.LENGTH_LONG);
        toastLogin.show();
    }

    private void checkLoginDetails() {
        if (passwordInput.getText().toString().equals(UserData.checkUserData(userIDInput.getText().toString()))) {
            loginAccepted = true;
            userIDInput.setText(UserData.checkUserData(userIDInput.getText().toString()));
        }
    }
//    private void onTagDiscovered(Context context, Intent intent) {
//        Toast.makeText(getApplicationContext(), "tag read!!!", Toast.LENGTH_LONG).show();
//    }

}
