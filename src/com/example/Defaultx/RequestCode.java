package com.example.Defaultx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.androidhive.loginandregister.R;

/**
 * Created by rahul on 05/11/2015.
 */
public class RequestCode extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to newcode.xml
        setContentView(R.layout.newcode);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing request code screen
                // Switching to Login Screen/closing request code screen
                finish();
            }
        });
    }
}
