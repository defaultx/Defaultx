package com.example.Defaultx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class CheckInActivity extends Activity {
    private static String serverIp = "192.169.1.7";
    //private static String serverIp = "192.168.56.1";
    private static int port = 8081;
    private Socket connection;
    private DataInputStream input;
    private DataOutputStream output;

    private Context context;
    private int duration;
    private boolean success = false;
    protected String passCode = null;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
        Button btnContinue = (Button) findViewById(R.id.btnContinue);
        TextView email = (TextView) findViewById(R.id.email);
        TextView status = (TextView) findViewById(R.id.status);
        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        if (!isNetworkAvailable()) {
            status.setText("No network connection!");
            status.setEnabled(true);
            Toast toast = Toast.makeText(context, "No network connection!", duration);
            toast.show();
        }

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new connectToServer().execute();
                if (success) {
                    // Switching to New Code screen
                    Intent i = new Intent(getApplicationContext(), login2Activity.class);
                    i.putExtra("passCode", passCode);
                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(context, "No Connection to Server!", duration);
                    toast.show();
                    //status.clearComposingText();
                    status.setText("No connection to server!");
                    status.setEnabled(true);
                }
                Toast toast = Toast.makeText(context, "AsyncTask Executed!", duration);
                toast.show();
            }
        });
    }

    /**
     * function to check if there is network connectivity
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    class connectToServer extends AsyncTask<String, Void, String> {

        String email_address = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                connection = new Socket(serverIp, port); //open connection with my local server ip
            } catch (UnknownHostException e) {
                System.out.println("***problem connecting to specified address***");
            } catch (IOException e) {
                System.out.println("***problem connecting to port***");
            }

            return "Excuted";
        }
        @Override
        protected void onPostExecute(String result) {
            TextView email = (TextView) findViewById(R.id.email);
            TextView status = (TextView) findViewById(R.id.status);
            try {
                input = new DataInputStream(connection.getInputStream());
                output = new DataOutputStream(connection.getOutputStream());
                email_address = String.valueOf(email.getText());
                Toast toast1 = Toast.makeText(context, email_address, duration);
                toast1.show();
                if (email_address != null && email_address.contains("@")) {
                    output.writeBytes("helllloooooo");
                    output.writeUTF(email_address);
                    passCode = input.readUTF();

                    Toast toast = Toast.makeText(context, passCode, duration);
                    toast.show();
                    input.close();
                    output.flush();
                    output.close();
                    connection.close();
                    success = true;
                } else {
                    status.setText("Please enter a valid email!");
                    status.setEnabled(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            @Override
            protected void onPreExecute() {}
        }
    }