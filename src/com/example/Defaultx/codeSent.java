package com.example.Defaultx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.androidhive.loginandregister.R;

/**
 * Created by g00275669 on 10/11/2015.
 */
    public class codeSent extends Activity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Set View to main.xml
            setContentView(R.layout.codesent);

            Button btnCheckIn = (Button) findViewById(R.id.btnCheckInC);
            // Listening to Login Screen link
            btnCheckIn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    // Closing codeSent screen
                    // Switching to Login Screen/closing main screen
                    finish();
                }
            });
        }
}
