package com.example.Defaultx;

import android.app.Activity;
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
        TextView macAddressField = (TextView) findViewById(R.id.macAdressField);
        //macAddressField.append(mac);
        macAddressField.setText(getIntent().getStringExtra("MacAddress"));

        nfcAdpt = NfcAdapter.getDefaultAdapter(this);
        // Check if the smartphone has NFC
        if (nfcAdpt == null) {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_LONG).show();
            finish();
        }
        // Check if NFC is enabled
        if (!nfcAdpt.isEnabled()) {
            Toast.makeText(this, "Enable NFC before using the app", Toast.LENGTH_LONG).show();
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
