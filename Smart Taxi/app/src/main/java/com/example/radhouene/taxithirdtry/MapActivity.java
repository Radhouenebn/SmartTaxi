package com.example.radhouene.taxithirdtry;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

public class MapActivity extends AppCompatActivity {
    // Google Map
    GoogleMap googleMap;
    Marker UserMarket, TaxiMarker;
    private double Lat, Lon;
    private String Username;
    private static final String URL = "";
    private static final String URLREQUEST = "";


    //// ROLE OF USER ////
    private int intRole = 0;


    // Session Manager Class
    SessionManager session;


    /////  GETTER AND SETTER OF MY VARIABLES//////////

    public void setLat(double Lat) {
        this.Lat = Lat;
    }

    public void setLon(double Lon) {
        this.Lon = Lon;
    }

    public double getLat() {
        return this.Lat;
    }

    public double getLon() {
        return this.Lon;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn() == false) {
            Intent goLogin = new Intent(this, Accueil.class);
            startActivity(goLogin);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        } else {

            try {

                GPSTracker gps = new GPSTracker(this);
                if (gps.canGetLocation()) {


                    /////////// GET INFORMATION OF USER WHO IS LOGED /////////////
                    HashMap<String, String> User = session.getUserDetails();
                    String UserName = User.get("name");
                    String Role = User.get("role");
                    this.intRole = Integer.parseInt(Role);
                    this.Username = User.get("username");


                    this.Lat = gps.getLatitude(); // returns latitude
                    this.Lon = gps.getLongitude(); // returns longitude


                    /////////INITIALIZE THE MAP
                    googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                            R.id.map)).getMap();


                    LatLng CURRENT_LOCATION = new LatLng(this.Lat, this.Lon);
                    //Get current position on map
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CURRENT_LOCATION, 15);
                    googleMap.animateCamera(update);


                    //////////////// PRINT TO DEBUG //////////////////
                    Log.e("Mi name is:", User.toString());


                    ///////// DEPENDING WHAT IS THE ROLE IT CHANGE THE MARKERT IMG

                    /////////// ROLE 1   --  USER
                    if (this.intRole == 1) {

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        googleMap.setMyLocationEnabled(true);
                        UpdateGPS(this.Username);

                        /////////// ROLE 2   --  TAXI DRIVER
                    }else if(this.intRole == 2){

                        this.TaxiMarker = this.googleMap.addMarker(new MarkerOptions()
                                .position(CURRENT_LOCATION)
                                .title(UserName)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi))
                        );
                        UpdateGPS(this.Username);

                        /////////// ROLE 3   --  ADMINISTRATOR
                    }else {

                        googleMap.setMyLocationEnabled(true);
                        UpdateGPS(this.Username);
                    }



                } else{
                    gps.showSettingsAlert();
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected void UpdateGPS(final String Email){
        GPSTracker gps = new GPSTracker(this);
        final double lat = gps.getLatitude();
        final double lon = gps.getLongitude();

        Thread t = new Thread() {

            public void run() {

                while(true){

                    try{


                        HttpClient client = new DefaultHttpClient();
                        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                        HttpResponse response;
                        JSONObject json = new JSONObject();


                        try {

                            HttpPost post = new HttpPost(URL);
                            json.put("Lat",lat);
                            json.put("Lon", lon);
                            json.put("UserEmail", Email);


                            StringEntity se = new StringEntity( json.toString() );
                            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                            post.setEntity(se);
                            response = client.execute(post);

                            Log.e("Data to update",json.toString());

			                    /*Checking response */
                            if(response!=null){
                                InputStream in = response.getEntity().getContent(); //Get the data in the entity

                                // Convert data InputStream into String
                                final String result = slurp(in);

                                // Print on LogCat to debugin purpose
                                // Log.e("Personal message",result);

                                // Conver String to Json



                                try {


                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {

                                            JSONObject reader = null;

                                            try {

                                                reader = new JSONObject(result);

                                                JSONObject StatusRequest  = reader.getJSONObject("Result");
                                                //  int Status = Integer.parseInt(StatusRequest.getString("Status"));


                                                JSONArray TaxiDrivers = StatusRequest.getJSONArray("TaxiDrivers");

                                                int lenArray = TaxiDrivers.length();

                                                for(int i=0; i<lenArray;i++){

                                                    JSONObject resultArrayJson  = TaxiDrivers.getJSONObject(i);

                                                    String UserID = resultArrayJson.getString("UserID");
                                                    String Username = resultArrayJson.getString("Username");
                                                    String Email = resultArrayJson.getString("Email");
                                                    String Phone = resultArrayJson.getString("Phone");
                                                    String Latitude = resultArrayJson.getString("Latitude");
                                                    String Longitude = resultArrayJson.getString("Longitude");
                                                    String TaxiID = resultArrayJson.getString("TaxiID");
                                                    String CarModel = resultArrayJson.getString("CarModel");
                                                    String CabNumber = resultArrayJson.getString("CabNumber");


                                                    ////////////// THIS IS FOR DEBUGIN PURPOSE /////////////

				     									     /*   Log.e("UserID",UserID);
				     									        Log.e("Username",Username);
				     									        Log.e("Email",Email);
				     									        Log.e("Phone",Phone);
				     									        Log.e("Latitude",Latitude);
				     									        Log.e("Longitude",Longitude);
				     									        Log.e("TaxiID",TaxiID);
				     									        Log.e("CarModel",CarModel);
				     									        Log.e("CabNumber",CabNumber);
				     									     */



                                                    ///////////////////////////////////////////////////////////////////////////////////////////////
                                                    ///////////////////// NOW IT IS NECESSARY TO PUT THE TAXIS ON THE MAP /////////////////////////
                                                    ///////////////////////////////////////////////////////////////////////////////////////////////
                                                    double Lat = Double.parseDouble(Latitude);
                                                    double Lon = Double.parseDouble(Longitude);



                                                    LatLng Taxi_Location = new LatLng(Lat,Lon);

                                                    googleMap.addMarker(new MarkerOptions()
                                                            .position(Taxi_Location)
                                                            .title(CarModel)
                                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));



                                                } // End for loop


                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }



                                        }
                                    });
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }



                            }

                        } catch(Exception e) {
                            e.printStackTrace();

                        }




                    } catch(Exception e) {
                        e.printStackTrace();

                    }

                } // End while loop

            }
        };

        t.start();


    }




}
