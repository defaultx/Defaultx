package com.example.Defaultx;

import android.app.Activity;
import android.app.Application;
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
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class CheckInActivity extends Activity {

    private static String serverIp = "192.169.1.15";
    //private static String serverIp = "192.168.56.1";
    public static int port = 8080;
    private Socket connection;
    private DataInputStream input;
    private DataOutputStream output;

    private Context context;
    private int duration;
    private boolean success = false;
    protected String passCode = null;
    protected String email_address = null;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
        Button btnContinue = (Button) findViewById(R.id.btnContinue);
        TextView status = (TextView) findViewById(R.id.status);
        TextView email = (TextView) findViewById(R.id.email);
        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        if (!isNetworkAvailable()) {
            status.setText("");
            status.setText("No network connection!");
            status.setEnabled(true);
        }

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_address = String.valueOf(email.getText());
                if (email_address != null && email_address.contains("@")) {
                    new connectToServer().execute();
                } else {
                    status.setText("");
                    status.setText("Please enter a valid email!");
                    status.setEnabled(true);
                }
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

    /**
     * AsyncTask to run the network operations. It is used to prevent NetworkOnMainThread Exception which is caused by
     * application attempts to perform a networking operation on its main thread
     */
    class connectToServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                connection = new Socket(serverIp, port); //open connection with my local server ip
                input = new DataInputStream(connection.getInputStream());
                output = new DataOutputStream(connection.getOutputStream());
                output.writeUTF(email_address + ",getpass");
                passCode = input.readUTF();
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
            TextView status = (TextView) findViewById(R.id.status);
            /* //for debugging
            Toast toast;
            toast = Toast.makeText(context, passCode, duration);
            toast.show();*/
            if (success) {
                // Switching to New Code screen
                Intent i = new Intent(getApplicationContext(), login2Activity.class);
                i.putExtra("passCode", passCode);
                i.putExtra("email", email_address);
                startActivity(i);
            } else {
                status.setText("");
                status.setText("No connection to server!");
                status.setEnabled(true);
            }
        }

        @Override
        protected void onPreExecute() {
        }
    }
}
