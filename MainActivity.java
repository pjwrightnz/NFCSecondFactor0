package com.example.paul.nfcsecondfactor0;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

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

//method is called when a new intent is thrown to the main activity. The logic then handles the
    // NFC data and captures the Ndef information and parses it. The card ID is then added to the userID
    // and password and sent to the server for authentication
    @Override
    public void onNewIntent(Intent intent) {

        ArrayList msgs = new ArrayList<>();

        if (intent.hasExtra((NfcAdapter.EXTRA_TAG))) {

           Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(parcelables != null && parcelables.length > 0) {
                readTextFromMessage((NdefMessage)parcelables[0]);
            }


            Toast.makeText(this, intent.getStringExtra(NfcAdapter.EXTRA_TAG), Toast.LENGTH_LONG).show();
        }

//
//            if (rawMsgs != null) {
//                for (int i = 0; i < rawMsgs.length; i++) {
//                    msgs.add(rawMsgs[i]);
//                }
//                Toast.makeText(this, "NFC card recognised!", Toast.LENGTH_LONG).show();

    //        Toast.makeText(this, msgs.get(0).toString(), Toast.LENGTH_LONG).show();


       // Toast.makeText(this, getIntent().getAction().toString(), Toast.LENGTH_LONG).show();

        super.onNewIntent(intent);
    }

    //read the Ndef Message here and convert to a String.

    private void readTextFromMessage(NdefMessage ndefMessage) {

        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length>0) {

            NdefRecord ndefRecord = ndefRecords[0];
            short tnf = ndefRecord.getTnf();
            if(tnf == NdefRecord.TNF_WELL_KNOWN) {

            }
        } else {
            Toast.makeText(this, "no ndef msgs", Toast.LENGTH_SHORT).show();
        }

    }


    //if app is paused
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


    private void handleIntent(Intent intent) {

    }

   /**
    * Created by Paul on 1/02/2016.
    * Background task for reading the data. Do not block the UI thread while reading.  *
    */

    class TagReadTask extends AsyncTask<Tag, Void, String> {


        private String readNFCRecordText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

            byte[] payload = record.getPayload();

            // Get the Text Encoding
            String textEncoding = "UTF-16";
            if ((payload[0] & 128) == 0) {
                textEncoding = "UTF-8";
            }

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {

                    return ndefRecord.toString();

                }
            }
            return null;
        }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            userIDInput.setText("Read content: " + result);
        }
    }

    }

}
