package com.example.represent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CongressionalActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;

    ImageView first_senator;
    ImageView second_senator;
    ImageView rep_house;

    Button more_info1;
    Button more_info2;
    Button more_info3;
    TextView my_location;
    String fs_id;
    String ss_id;
    String rh_id;
    TextView name_fs;
    TextView party_fs;
    TextView email_fs;
    TextView website_fs;
    TextView name_ss;
    TextView party_ss;
    TextView email_ss;
    TextView website_ss;
    TextView name_rh;
    TextView party_rh;
    TextView email_rh;
    TextView website_rh;
    TextView box1;
    TextView box2;
    TextView box3;
    TextView name1;
    TextView name2;
    TextView name3;
    TextView party1;
    TextView party2;
    TextView party3;
    TextView website1;
    TextView website2;
    TextView website3;
    TextView email1;
    TextView email2;
    TextView email3;
    TextView des1;
    TextView des2;
    TextView des3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional);

        name1 = (TextView) findViewById(R.id.textView11);
        name1.bringToFront();
        name2 = (TextView) findViewById(R.id.textView18);
        name2.bringToFront();
        name3 = (TextView) findViewById(R.id.textView22);
        name3.bringToFront();

        party1 = (TextView) findViewById(R.id.textView12);
        party1.bringToFront();
        party2 = (TextView) findViewById(R.id.textView19);
        party2.bringToFront();
        party3 = (TextView) findViewById(R.id.textView23);
        party3.bringToFront();

        website1 = (TextView) findViewById(R.id.textView13);
        website1.bringToFront();
        website2 = (TextView) findViewById(R.id.textView20);
        website2.bringToFront();
        website3 = (TextView) findViewById(R.id.textView24);
        website3.bringToFront();

        email1 = (TextView) findViewById(R.id.textView14);
        email1.bringToFront();
        email2 = (TextView) findViewById(R.id.textView21);
        email2.bringToFront();
        email3 = (TextView) findViewById(R.id.textView25);
        email3.bringToFront();

        des1 = (TextView) findViewById(R.id.description1);
        des1.bringToFront();
        des2 = (TextView) findViewById(R.id.description2);
        des2.bringToFront();
        des3 = (TextView) findViewById(R.id.description3);
        des3.bringToFront();

        box1 = (TextView) findViewById(R.id.box1);
        box2 = (TextView) findViewById(R.id.box2);
        box3 = (TextView) findViewById(R.id.box3);
        first_senator = (ImageView) findViewById(R.id.first_senator);
        first_senator.bringToFront();
        second_senator = (ImageView) findViewById(R.id.second_senator);
        second_senator.bringToFront();
        rep_house = (ImageView) findViewById(R.id.rep_house);
        rep_house.bringToFront();


        name_fs = (TextView) findViewById(R.id.name_fs);
        name_fs.bringToFront();
        party_fs = (TextView) findViewById(R.id.party_fs);
        party_fs.bringToFront();
        email_fs = (TextView) findViewById(R.id.email_fs);
        email_fs.bringToFront();
        website_fs = (TextView) findViewById(R.id.website_fs);
        website_fs.bringToFront();

        name_ss = (TextView) findViewById(R.id.name_ss);
        name_ss.bringToFront();
        party_ss = (TextView) findViewById(R.id.party_ss);
        party_ss.bringToFront();
        email_ss = (TextView) findViewById(R.id.email_ss);
        email_ss.bringToFront();
        website_ss = (TextView) findViewById(R.id.website_ss);
        website_ss.bringToFront();

        name_rh = (TextView) findViewById(R.id.name_rh);
        name_rh.bringToFront();
        party_rh = (TextView) findViewById(R.id.party_rh);
        party_rh.bringToFront();
        email_rh = (TextView) findViewById(R.id.email_rh);
        email_rh.bringToFront();
        website_rh = (TextView) findViewById(R.id.website_rh);
        website_rh.bringToFront();

        more_info1 = (Button) findViewById(R.id.more_info1);
        more_info2 = (Button) findViewById(R.id.more_info2);
        more_info3 = (Button) findViewById(R.id.more_info3);
        my_location = (TextView) findViewById(R.id.location);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fs_id = "";
        ss_id = "";
        rh_id = "";

        String status = getIntent().getStringExtra("status");

        if (status.equals("Random")) {
            InputStream is = getResources().openRawResource(R.raw.uszips);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Random rand = new Random();
            int random_number = rand.nextInt(41485) + 1;
            int counter = 0;
            String longi = "";
            String lati = "";
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    if (counter == random_number) {
                        String[] words = line.split(",");
                        longi = words[2];
                        lati = words[1];
                        counter++;
                    }
                    counter++;
                }
                locateInfo(longi, lati);
            } catch (IOException e) {
                return;
            }

        }


        // Current Location

        if (status.equals("Current Location")) {

            //CHECK PERMISSION
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

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    String longi = String.valueOf(longitude);
                    String lati = String.valueOf(latitude);

                    locateInfo(longi, lati);

                }
            });
        }

        if (status.equals("Zip")) {
            String longitude = getIntent().getStringExtra("longi");
            String latitude = getIntent().getStringExtra("lati");
            locateInfo(longitude, latitude);
        }

    }

    public void locateInfo(String longi, String lati) {

        String url = "https://api.geocod.io/v1.3/reverse?q="+lati+","+longi+"&fields=cd&api_key=dbbb257f062440d7bb20efd7577958dd5b00455";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String district = String.valueOf(response.getJSONArray("results").getJSONObject(0).getJSONObject("fields").getJSONArray("congressional_districts").getJSONObject(0).getInt("district_number"));
                    String city = response.getJSONArray("results").getJSONObject(0).getJSONObject("address_components").getString("city");
                    String state = response.getJSONArray("results").getJSONObject(0).getJSONObject("address_components").getString("state");
                    my_location.setText(city + ", " + state);
                    String temp = "";
                    String url2 = "https://api.propublica.org/congress/v1/members/senate/"+state+"/"+district+"/current.json";
                    senateInfo(url2);

                    String url3 = "https://api.propublica.org/congress/v1/members/house/"+state+"/"+district+"/current.json";
                    representativeInfo(url3);

                } catch (JSONException e) {
                    my_location.setText("Failure");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(CongressionalActivity.this).add(jsonObjectRequest);
    }

    public void senateInfo(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String first_id = response.getJSONArray("results").getJSONObject(0).getString("id");
                    String first_name = response.getJSONArray("results").getJSONObject(0).getString("name");
                    String first_party = response.getJSONArray("results").getJSONObject(0).getString("party");


                    String second_id = response.getJSONArray("results").getJSONObject(1).getString("id");
                    String second_name = response.getJSONArray("results").getJSONObject(1).getString("name");
                    String second_party = response.getJSONArray("results").getJSONObject(1).getString("party");


                    fs_id = first_id;
                    ss_id = second_id;

                    String fs_url = "https://api.propublica.org/congress/v1/members/"+first_id+".json";
                    TextView[] first_textviews = new TextView[5];
                    first_textviews[0] = name_fs;
                    first_textviews[1] = party_fs;
                    first_textviews[2] = email_fs;
                    first_textviews[3] = website_fs;
                    first_textviews[4] = box1;
                    personalInfo(fs_url, first_name, first_party, first_textviews);
                    Picasso.with(CongressionalActivity.this).load("http://bioguide.congress.gov/bioguide/photo/"+first_id.charAt(0)+"/"+first_id+".jpg").into(first_senator);

                    String ss_url = "https://api.propublica.org/congress/v1/members/"+second_id+".json";
                    TextView[] second_textviews = new TextView[5];
                    second_textviews[0] = name_ss;
                    second_textviews[1] = party_ss;
                    second_textviews[2] = email_ss;
                    second_textviews[3] = website_ss;
                    second_textviews[4] = box2;
                    personalInfo(ss_url, second_name, second_party, second_textviews);
                    Picasso.with(CongressionalActivity.this).load("http://bioguide.congress.gov/bioguide/photo/"+second_id.charAt(0)+"/"+second_id+".jpg").into(second_senator);


                } catch (JSONException e) {
                    name_fs.setText("Failure");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-Key", "3xbEfPN0E13wxizg1DFNo3u3Oimjkf4TgsPqyuQq ");
                return params;
        }
        };
        Volley.newRequestQueue(CongressionalActivity.this).add(jsonObjectRequest);
    }


    public void representativeInfo(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String id = response.getJSONArray("results").getJSONObject(0).getString("id");
                    String name = response.getJSONArray("results").getJSONObject(0).getString("name");
                    String party = response.getJSONArray("results").getJSONObject(0).getString("party");

                    rh_id = id;

                    String rh_url = "https://api.propublica.org/congress/v1/members/"+id+".json";
                    TextView[] rh_textviews = new TextView[5];
                    rh_textviews[0] = name_rh;
                    rh_textviews[1] = party_rh;
                    rh_textviews[2] = email_rh;
                    rh_textviews[3] = website_rh;
                    rh_textviews[4] = box3;
                    personalInfo(rh_url, name, party, rh_textviews);
                    Picasso.with(CongressionalActivity.this).load("http://bioguide.congress.gov/bioguide/photo/"+id.charAt(0)+"/"+id+".jpg").into(rep_house);

                } catch (JSONException e) {
                    name_fs.setText("Failure");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-Key", "3xbEfPN0E13wxizg1DFNo3u3Oimjkf4TgsPqyuQq ");
                return params;
            }
        };
        Volley.newRequestQueue(CongressionalActivity.this).add(jsonObjectRequest);

    }

    public void personalInfo(String url, final String name, final String party, final TextView[] t) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String website = response.getJSONArray("results").getJSONObject(0).getString("url");
                    String email = response.getJSONArray("results").getJSONObject(0).getJSONArray("roles").getJSONObject(0).getString("contact_form");
                    if (website.equals("null")) {
                        website = "None Found";
                        t[2].setClickable(false);
                    }
                    if (email.equals("null")) {
                        t[3].setClickable(false);
                        email = "None Found";
                    }
                    if (party.equals("D")){
                        String temp_party = "Democratic";
                        t[1].setText(temp_party);
                        t[4].setBackgroundResource(R.drawable.box);
                    }
                    if (party.equals("R")){
                        String temp_party = "Republican";
                        t[1].setText(temp_party);
                        t[4].setBackgroundResource(R.drawable.box_red);
                    }
                    if (party.equals("I")){
                        String temp_party = "Independent";
                        t[1].setText(temp_party);
                        t[4].setBackgroundResource(R.drawable.i_box);
                    }
                    t[0].setText(name);
                    t[2].setText(website);
                    t[3].setText(email);

                } catch (JSONException e) {
                    name_fs.setText("Failure");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-API-Key", "3xbEfPN0E13wxizg1DFNo3u3Oimjkf4TgsPqyuQq ");
                return params;
            }
        };
        Volley.newRequestQueue(CongressionalActivity.this).add(jsonObjectRequest);
    }

    public void first_openDetailedActivity (View view) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("position", "US Senator");
        intent.putExtra("id", fs_id);
        startActivity(intent);
    }

    public void second_openDetailedActivity (View view) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("position", "US Senator");
        intent.putExtra("id", ss_id);
        startActivity(intent);
    }

    public void rep_openDetailedActivity (View view) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("position", "Rep. From House");
        intent.putExtra("id", rh_id);
        startActivity(intent);
    }


}
