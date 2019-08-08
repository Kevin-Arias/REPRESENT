package com.example.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EnterZipActivity extends AppCompatActivity {


    EditText zipcode;
    TextView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_zip);

        temp = (TextView) findViewById(R.id.temp);
        zipcode = (EditText) findViewById(R.id.ZipCode);
    }

    public void searchZip(View view) {
        final String zip = String.valueOf(zipcode.getText());
        String url = "https://api.geocod.io/v1.3/geocode?q=" + zip +"&fields=cd&api_key=dbbb257f062440d7bb20efd7577958dd5b00455";

        final Intent intent1 = new Intent(this, CongressionalActivity.class);
        final Intent intent2 = new Intent(this, ExtraZipActivity.class);
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
                        temp.setText(state + ", " +first_district + ", " + second_district);

                    }

                } catch (JSONException e) {
                    temp.setText("Failure");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(EnterZipActivity.this).add(jsonObjectRequest);
    }
}
