package com.example.radhouene.taxithirdtry;


import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {
    public final static String URL = "http://192.168.8.8:8080/serverside/webapi/user/login";
    public final static String key_username = "username";
    public final static String key_password = "password";
    Button registerlink, btmsbm;
    String strUser, strPass;
    EditText Username, Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = (EditText) findViewById(R.id.LogEmail);
        Pass = (EditText) findViewById(R.id.LogPass);
        btmsbm = (Button) findViewById(R.id.btnLogin);
        btmsbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        strUser = Username.getText().toString();
                        strPass = Pass.getText().toString();
                        if (strUser.matches("")) {
                            Toast.makeText(MainActivity.this, "Username is required", LENGTH_LONG).show();
                        } else if (strPass.matches("")) {
                            Toast.makeText(MainActivity.this, "Pasword is required", LENGTH_LONG).show();
                        } else {
                            LoginHttpJson(strUser, strPass);

                        }
                    }
                }
        );
        registerlink = (Button) findViewById(R.id.LinkRegisterId);
        registerlink.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.radhouene.taxithirdtry.RegisterClt");
                        startActivity(intent);
                    }
                }
        );
    }


    protected void LoginHttpJson(final String Username,final String Password){


        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                //HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                // Print on LogCat to debugin purpose
                Log.e("Personal message","I am inside of the thread");
                Log.e("Personal message",Username + " " +Password);

                try {

                    HttpPost post = new HttpPost(URL);
                    json.put(key_username, Username);
                    json.put(key_password, Password);


                    StringEntity se = new StringEntity( json.toString() );
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

	                    /*Checking response */
                    if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity

                        // Convert data InputStream into String
                        String result = slurp(in);

                        // Print on LogCat to debugin purpose
                        Log.e("Personal message",result);

                        // Conver String to Json

                        JSONObject reader = new JSONObject(result);

                        respond(reader);

                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();


    }


    public static String slurp(final InputStream is) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }





    public void respond(JSONObject CallBackResult) throws JSONException {

			 /*
			  * If Result Status is 0 = "Error Login. Wrong email and password "
			  * If Result Status is 1 = "Success Login"
			  * If Result Status is 2 = "Unknow error"
              *
			  */

        JSONObject StatusRequest  = CallBackResult.getJSONObject("Result");
        int Status = Integer.parseInt(StatusRequest.getString("Status"));
        JSONObject UserRequest  = CallBackResult.getJSONObject("User");
        String User_username = UserRequest.getString("username");
        String User_fullname = UserRequest.getString("fullname");


        if(Status == 1){
            //// Success here is necessary to Handle all information about user

            // Session Manager
            //SessionManager session = new SessionManager(getApplicationContext());
            //session.createLoginSession(User_username, User_fullname);


            //Intent goLogin = new Intent(this, MapCustomerActivity.class);
            //startActivity(goLogin);


        }else if(Status == 0){

            Toast.makeText(this,"Invalid Username or password !",Toast.LENGTH_LONG);

        }else{
            Toast.makeText(this,"Error System ! please contact the administrator",Toast.LENGTH_LONG);
        }
    }
}
