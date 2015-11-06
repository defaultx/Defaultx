package com.example.Defaultx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.androidhive.loginandregister.R;

public class CheckInActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
        TextView newCodeScreen = (TextView) findViewById(R.id.new_code);

        // Listening to request code link
        newCodeScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to New Code screen
                Intent i = new Intent(getApplicationContext(), RequestCode.class);
                startActivity(i);
            }
        });
    }
}