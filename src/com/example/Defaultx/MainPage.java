package com.example.Defaultx;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.androidhive.loginandregister.R;

/**
 * Created by rahul on 06/11/2015.
 */
public class MainPage extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NfcAdapter nfcAdpt;
        // Set View to main.xml
        setContentView(R.layout.main);
        TextView macAddressField = (TextView) findViewById(R.id.room_field);
        //macAddressField.append(mac);
        //macAddressField.setText(getIntent().getStringExtra("MacAddress"));
        macAddressField.setText("Your room number: " + getIntent().getStringExtra("room"));

        nfcAdpt = NfcAdapter.getDefaultAdapter(this);
        // Check if the smartphone has NFC
        if (nfcAdpt == null) {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_LONG).show();
            finish();
        }
        // Check if NFC is enabled
        if (!nfcAdpt.isEnabled()) {
            //Toast.makeText(this, "Enable NFC before using the app", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Please activate NFC and press Back to return to the application!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
        Button btnCheckOut = (Button) findViewById(R.id.btnCheckOut);
        // Listening to Login Screen link
        btnCheckOut.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing main screen
                // Switching to Login Screen/closing main screen
                finish();
            }
        });

       /* MimeRecord mimeRecord = new MimeRecord();
        mimeRecord.setMimeType("text/plain");
        mimeRecord.setData("This is my data".getBytes("UTF-8"));*/


    }

}
