package com.example.Defaultx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.androidhive.loginandregister.R;

/**
 * Created by rahul on 06/11/2015.
 */
public class MainPage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to main.xml
        setContentView(R.layout.main);

        Button btnCheckOut = (Button) findViewById(R.id.btnCheckOut);
        // Listening to Login Screen link
        btnCheckOut.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing main screen
                // Switching to Login Screen/closing main screen
                finish();
            }
        });
    }
}
