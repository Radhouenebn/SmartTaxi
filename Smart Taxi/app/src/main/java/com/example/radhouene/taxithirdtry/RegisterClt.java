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

import static android.widget.Toast.LENGTH_LONG;

public class RegisterClt extends AppCompatActivity {
    public final static String URL = "http://192.168.1.7:8080/serverside/webapi/login/signin";
    EditText fullname,username,password,repassword,phone,email;
    Button btnsbm;
    String sfullname,susername,spass,srepass,sphone,smail;
    public final static String key_fullname = "fullname";
    public final static String key_username = "username";
    public final static String key_mail = "mail";
    public final static String key_phone = "phone";
    public final static String key_password = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_clt);
        btnsbm = (Button) findViewById(R.id.btnRegister);
        fullname = (EditText) findViewById(R.id.RegFullname);
        username = (EditText) findViewById(R.id.RegUsername);
        email = (EditText) findViewById(R.id.RegEmail);
        phone = (EditText) findViewById(R.id.RegPhone);
        password = (EditText) findViewById(R.id.RegPass);
        repassword = (EditText) findViewById(R.id.RegPass2);
        btnsbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sfullname = fullname.getText().toString();
                        susername = username.getText().toString();
                        smail = email.getText().toString();
                        sphone = phone.getText().toString();
                        spass = password.getText().toString();
                        srepass = repassword.getText().toString();
                        if (sfullname.matches("")) {
                            Toast.makeText(RegisterClt.this, "Full name is required", LENGTH_LONG).show();
                        }
                        else if (susername.matches("")) {
                            Toast.makeText(RegisterClt.this, "Username is required", LENGTH_LONG).show();
                        }
                        else if (smail.matches("")){
                            Toast.makeText(RegisterClt.this, "E-mail is required", LENGTH_LONG).show();
                        }
                        else if(sphone.matches("")) {
                        Toast.makeText(RegisterClt.this, "phone is required", LENGTH_LONG).show();
                        }
                        else if (spass.matches("")) {
                        Toast.makeText(RegisterClt.this, "password is required", LENGTH_LONG).show();
                        }
                        else if (!(spass.equals(srepass))) {
                            Toast.makeText(RegisterClt.this, "Verify your password", LENGTH_LONG).show();
                        }
                        else {
                        RegisterHttpJson();}

                    }
                    }

        );
    }

    protected void RegisterHttpJson(){
        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                //HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                // Print on LogCat to debugin purpose
                Log.e("Personal message","I am inside of the thread");

                try {

                    HttpPost post = new HttpPost(URL);
                    json.put(key_fullname, sfullname);
                    json.put(key_username, susername);
                    json.put(key_password,spass);
                    json.put(key_mail,smail);
                    json.put(key_phone,sphone);

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

        int Status = Integer.parseInt(CallBackResult.getString("Status"));

        if(Status == 1){
            Toast.makeText(this,"User added ! Wait the admin's confirmation",Toast.LENGTH_LONG).show();
            Intent goLogin = new Intent(this, Accueil.class);
            startActivity(goLogin);


        }else if(Status == 0){

            Toast.makeText(this,"User is alredy registred  !",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"Error System ! please contact the administrator",Toast.LENGTH_LONG).show();
        }
    }
}
