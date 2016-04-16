package com.example.Defaultx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

/**
 * Created by rahul on 05/11/2015.
 */
public class RequestCode extends Activity {

    //private String serverIp = ((CheckInActivity.MainVar) this.getApplication()).getServer_IP();
    //private int port = ((CheckInActivity.MainVar) this.getApplication()).getServer_port();
    private static String serverIp = "192.169.1.7";
    public static int port = 8080;
    private Context context;
    private int duration;
    private String email_address;
    private boolean success = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newcode);
        Button btnCodeSent = (Button) findViewById(R.id.btnSndCode);
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        TextView email = (TextView) findViewById(R.id.reg_email);
        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing request code screen
                // Switching to Login Screen/closing request code screen
                finish();
            }
        });

        // Listening to log in button
        btnCodeSent.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                email_address = String.valueOf(email.getText());
                new connectToServer().execute();
                // Switching to New Code screen
            }
        });
    }

    /**
     * AsyncTask to run the network operations. It is used to prevent NetworkOnMainThread Exception which is caused by
     * application attempts to perform a networking operation on its main thread
     */
    class connectToServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Socket connection = new Socket(serverIp, port); //open connection with my local server ip
                DataInputStream input = new DataInputStream(connection.getInputStream());
                DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                output.writeUTF(email_address + ",newPass");
                input.close();
                output.flush();
                output.close();
                connection.close();
                success = true;

            } catch (UnknownHostException e) {
                System.out.println("***problem connecting to specified address***");
            } catch (IOException e) {
                System.out.println("***problem connecting to port***" + e);
            }

            return "Excuted";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast toast;
            toast = Toast.makeText(context, "New code request send! Please check your email!", duration);
            toast.show();
            if (success) {
                // Switching to New Code screen
                Intent i = new Intent(getApplicationContext(), codeSent.class);
                startActivity(i);
                finish();
            } else {
                toast = Toast.makeText(context, "No Connection to Server!", duration);
                toast.show();
            }
            toast = Toast.makeText(context, "AsyncTask Executed!", duration);
            toast.show();
        }

        @Override
        protected void onPreExecute() {}
    }
}
