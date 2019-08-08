package com.example.represent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class MainActivity extends AppCompatActivity {


    EditText longitude;
    EditText latitude;
    private FusedLocationProviderClient mFusedLocationClient;
    double longi;
    double lati;
    public static TextView data;
    private RequestQueue mQueue;
    ImageView image;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void zip_openCongressionalActivity (View view) {
        AlertDialog.Builder zipBuilder = new AlertDialog.Builder(MainActivity.this);
        View zipView = getLayoutInflater().inflate(R.layout.input_zip, null);

        final EditText ziptext = (EditText) zipView.findViewById(R.id.editText_enterzip);
        Button button = (Button) zipView.findViewById(R.id.find_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String zip = String.valueOf(ziptext.getText());
                String url = "https://api.geocod.io/v1.3/geocode?q=" + zip +"&fields=cd&api_key=dbbb257f062440d7bb20efd7577958dd5b00455";

                final Intent intent1 = new Intent(MainActivity.this, CongressionalActivity.class);
                final Intent intent2 = new Intent(MainActivity.this, ExtraZipActivity.class);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int num_districts = response.getJSONArray("results").getJSONObject(0).getJSONObject("fields").
                                    getJSONArray("congressional_districts").length();

                            if (num_districts == 1) {
                                String longi = response.getJSONArray("results").getJSONObject(0).getJSONObject("location").getString("lng");
                                String lati = response.getJSONArray("results").getJSONObject(0).getJSONObject("location").getString("lat");
                                intent1.putExtra("status", "Zip");
                                intent1.putExtra("longi", longi);
                                intent1.putExtra("lati", lati);
                                startActivity(intent1);
                            } else {
                                String state = response.getJSONArray("results").getJSONObject(0).getJSONObject("address_components").getString("state");
                                String first_district = String.valueOf(response.getJSONArray("results").getJSONObject(0).getJSONObject("fields").
                                        getJSONArray("congressional_districts").getJSONObject(0).getString("district_number"));
                                String second_district = String.valueOf(response.getJSONArray("results").getJSONObject(0).getJSONObject("fields").
                                        getJSONArray("congressional_districts").getJSONObject(1).getString("district_number"));
                                intent2.putExtra("first_district",first_district);
                                intent2.putExtra("second_district",second_district);
                                intent2.putExtra("state", state);
                                intent2.putExtra("zipcode", zip);
                                startActivity(intent2);


                            }

                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);
            }
        });
        zipBuilder.setView(zipView);
        AlertDialog dialog = zipBuilder.create();
        dialog.show();

    }




    public void current_openCongressionalActivity (View view) {
        Intent intent = new Intent(this, CongressionalActivity.class);
        intent.putExtra("status", "Current Location");
        startActivity(intent);
    }

    public void random_openCongressionalActivity (View view) {
        Intent intent = new Intent(this, CongressionalActivity.class);
        intent.putExtra("status", "Random");
        startActivity(intent);
    }


}

