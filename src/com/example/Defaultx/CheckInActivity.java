package com.example.Defaultx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.androidhive.loginandregister.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class CheckInActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
        TextView newCodeScreen = (TextView) findViewById(R.id.new_code);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        // Listening to request code link
        newCodeScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to New Code screen
                Intent i = new Intent(getApplicationContext(), RequestCode.class);
                startActivity(i);
            }
        });

        // Listening to log in button
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo wInfo = wifiManager.getConnectionInfo();
                String macAddress = wInfo.getMacAddress();
                        try {
                            Socket connection = new Socket("109.79.110.244",85); //open connection with my local server ip
                            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                            output.writeUTF(macAddress);
                            output.flush();
                            output.close();
                            connection.close();
                        } catch (UnknownHostException e) {
                            System.out.println("problem connecting to specified address");
                        } catch (IOException e) {
                            System.out.println("problem connecting to port");
                        }
                // Switching to New Code screen
                Intent i = new Intent(getApplicationContext(), MainPage.class);
                i.putExtra("KEY_StringMacAddress", macAddress);
                startActivity(i);
            }
        });
    }
}