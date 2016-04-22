package com.example.Defaultx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.androidhive.loginandregister.R;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by rahul on 26/12/2015.
 */
public class login2Activity extends Activity {

   // private String serverIp = ((CheckInActivity.MainVar) this.getApplication()).getServer_IP(); //get address from CheckInActivity
   // private int port = ((CheckInActivity.MainVar) this.getApplication()).getServer_port();
   private static String serverIp = "192.169.1.15";
    public static int port = 8080;

    private String macAddress = null;
    private String passCode = null;
    private String email = null;
    private Context context;
    private int duration;
    private String userPass = null;
    private String room = null;

    @Override
    public void onResume(){
        super.onResume();
        passCode = getIntent().getStringExtra("passCode");
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login2);
        TextView newCodeScreen = (TextView) findViewById(R.id.new_code);
        TextView pass_field = (TextView) findViewById(R.id.sec_code);
        TextView status = (TextView) findViewById(R.id.status);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        // Listening to request code link
        newCodeScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                passCode = null; //remove the pass code stored
                // Switching to New Code screen
                Intent i = new Intent(getApplicationContext(), RequestCode.class);
                startActivity(i);
            }
        });

        // Listening to log in button
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                status.setEnabled(false);
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo wInfo = wifiManager.getConnectionInfo();
                macAddress = wInfo.getMacAddress();
                passCode = getIntent().getStringExtra("passCode");
                System.out.println(passCode);
                userPass = String.valueOf(pass_field.getText());
                email = getIntent().getStringExtra("email");
                new connectToServer().execute();
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
                output.writeUTF(macAddress+ ",mac"+ "," + email);
                room = input.readUTF();
                System.out.println("room: " + room);
                input.close();
                output.flush();
                output.close();
                connection.close();
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
            //Toast toast;
            //toast = Toast.makeText(context, passCode, duration); // for debug
            //toast.show();
            if (passCode != null && passCode.equals(userPass)){
                // Switching to New Code screen
                Intent i = new Intent(getApplicationContext(), MainPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //all of the other activities on top will be closed.cant go back
                //i.putExtra("MacAddress", macAddress);
                i.putExtra("room", room);
                startActivity(i);
                finish();
            }
            else {
                status.setText(""); //clear text
                status.setText("Wrong Security Code! Try Again!");
                status.setEnabled(true);
            }
        }

        @Override
        protected void onPreExecute() {}
    }
}