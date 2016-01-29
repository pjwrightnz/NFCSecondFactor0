package com.example.paul.nfcsecondfactor0;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    public static String userID;
    ImageView btLogo, yorkLogo, nfcLogo, loginIcon, pwIcon;
    Boolean loginAccepted = false;
    EditText userIDInput, passwordInput;
    Button loginButton, registerButton;
    private NfcAdapter loginNfcAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set Icon
        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.nfcsign);
        menu.setDisplayUseLogoEnabled(true);

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
        //login button
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserIDExists();
                checkLoginDetails();
                showToast();
                Intent newBTWebIntent = new Intent(MainActivity.this, BTWebSiteActivity.class);
                if (loginAccepted) {
                    startActivityForResult(newBTWebIntent, 0);
                }
            }
        });




        //set softkeyboard action listener for logging in
        passwordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkUserIDExists();
                    checkLoginDetails();
                    showToast();
                    Intent newBTWebIntent = new Intent(MainActivity.this, BTWebSiteActivity.class);
                    startActivity(newBTWebIntent);
                    return true;
                }
                return false;
            }
        });

        //register button
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newRegisterIntent = new Intent(MainActivity.this, RegisterstrationActivity.class);
                startActivityForResult(newRegisterIntent, 0);
            }
        });
        //populate user persistance with a test user
        UserDataPersistance.fillUserData();

        //  nfc code
        loginNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //  check if there is an NFC capability of the phone
        if (loginNfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC. Second Factor Authentication unavailable", Toast.LENGTH_LONG).show();
        }
        // check NFC is enabled
        if (!loginNfcAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Please activate NFC to allow second factor authentication and press Back to return to the application.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }
        //loginNfcAdapter.enableReaderMode(this, 0, NfcAdapter.FLAG_READER_NFC_B | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,null);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void showToast() {
        Context context = getApplicationContext();
        CharSequence loginToastMessage = "Login failed, please try again.";
        if (loginAccepted) {
            loginToastMessage = "Login Successful.";
            userIDInput.setText("");
            passwordInput.setText("");
        }
        Toast toastLogin = Toast.makeText(context, loginToastMessage, Toast.LENGTH_LONG);
        toastLogin.show();
    }

    private boolean checkUserIDExists() {
        if (UserDataPersistance.userData.containsKey(userIDInput.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    private void checkLoginDetails() {
        if (UserDataPersistance.checkUserData(userIDInput.getText().toString()))
            if (passwordInput.getText().toString().equals(UserDataPersistance.userData.get(userIDInput.getText().toString()).getPassword())) {
                loginAccepted = true;
                //userIDInput.setText(UserDataPersistance.checkUserData(userIDInput.getText().toString()));
            } else {
                showToast();
            }
        else {
            showToast();
        }
    }



    @Override
    public void onNewIntent(Intent intent) {
        Toast.makeText(this, "NFC card recognised!", Toast.LENGTH_LONG).show();
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags((intent.FLAG_RECEIVER_REPLACE_PENDING));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};
        loginNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

        super.onResume();
    }

    @Override
    protected void onPause() {
        loginNfcAdapter.disableForegroundDispatch(this);

        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1) {
            userIDInput.setText(data.getStringExtra(MainActivity.userID));
        }


    }

 //   @Override
//    protected void onNewIntent(Intent intent) {
//        /**
//         * This method gets called, when a new Intent gets associated with the current activity instance.
//         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
//         * at the documentation.
//         *
//         * In our case this method gets called, when the user attaches a Tag to the device.
//         */
//        handleIntent(intent);
//    }

    private void handleIntent(Intent intent) {

    }




//    private void onTagDiscovered(Context context, Intent intent) {
//        Toast.makeText(getApplicationContext(), "tag read!!!", Toast.LENGTH_LONG).show();
//    }

}
