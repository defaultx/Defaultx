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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;

public class CheckInActivity extends Activity
{
    private static String serverIp = "192.168.56.1";
    private static int port = 8081;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
        Button btnContinue = (Button) findViewById(R.id.btnContinue);
        TextView email = (TextView) findViewById(R.id.email);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passCode = null;
                try {
                    Socket connection = new Socket(serverIp,port); //open connection with my local server ip
                    DataInputStream input = new DataInputStream(connection.getInputStream());
                    DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                    output.writeUTF(String.valueOf(email.getText()));
                    passCode = input.readUTF();
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, passCode, duration);
                    toast.show();
                    //System.out.println("Passcode: "+ passCode);
                    input.close();
                    output.flush();
                    output.close();
                    connection.close();
                } catch (UnknownHostException e) {
                    System.out.println("problem connecting to specified address");
                } catch (IOException e) {
                    System.out.println("problem connecting to port");
                }
                // Switching to New Code screen
                Intent i = new Intent(getApplicationContext(), login2Activity.class);
                i.putExtra("passCode", passCode);
                startActivity(i);
            }
        });

    }


}