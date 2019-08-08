package com.example.represent;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailedActivity extends AppCompatActivity {

    ImageView image;
    TextView position;
    TextView textName;
    TextView currname;
    TextView textParty;
    TextView currparty;
    TextView textWebsite;
    TextView currwebsite;
    TextView textEmail;
    TextView curremail;
    TextView textCom;
    TextView currcom;
    TextView textBill;
    TextView currbill;
    TextView box_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        image = (ImageView) findViewById(R.id.imageView_full);
        image.bringToFront();
        position = (TextView) findViewById(R.id.textView_pos);
        position.bringToFront();

        textName = (TextView) findViewById(R.id.textView);
        textName.bringToFront();
        currname = (TextView) findViewById(R.id.textView2);
        currname.bringToFront();
        textParty = (TextView) findViewById(R.id.textView3);
        textParty.bringToFront();
        currparty = (TextView) findViewById(R.id.textView4);
        currparty.bringToFront();
        textWebsite = (TextView) findViewById(R.id.textView5);
        textWebsite.bringToFront();
        currwebsite = (TextView) findViewById(R.id.textView6);
        currwebsite.bringToFront();
        textEmail = (TextView) findViewById(R.id.textView7);
        textEmail.bringToFront();
        curremail = (TextView) findViewById(R.id.textView8);
        curremail.bringToFront();
        textCom = (TextView) findViewById(R.id.textView9);
        textCom.bringToFront();
        currcom = (TextView) findViewById(R.id.textView10);
        currcom.bringToFront();
        textBill = (TextView) findViewById(R.id.textView15);
        textBill.bringToFront();
        currbill = (TextView) findViewById(R.id.textView16);
        currbill.bringToFront();
        box_detail = (TextView) findViewById(R.id.box_detail);

        String pos = getIntent().getStringExtra("position");
        position.setText(pos);


        final String id = getIntent().getStringExtra("id");
        Picasso.with(DetailedActivity.this).load("http://bioguide.congress.gov/bioguide/photo/"+id.charAt(0)+"/"+id+".jpg").into(image);

        String url = "https://api.propublica.org/congress/v1/members/"+id+".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getJSONArray("results").getJSONObject(0).getString("first_name") + " " +
                            response.getJSONArray("results").getJSONObject(0).getString("last_name");
                    String party = response.getJSONArray("results").getJSONObject(0).getString("current_party");
                    String website = response.getJSONArray("results").getJSONObject(0).getString("url");
                    String email = response.getJSONArray("results").getJSONObject(0).getJSONArray("roles").getJSONObject(0).getString("contact_form");

                    if (website.equals("null")) {
                        website = "None Found";
                    }
                    if (email.equals("null")) {

                        email = "None Found";
                    }
                    if (party.equals("D")){
                        String temp_party = "Democratic";
                        currparty.setText(temp_party);
                        box_detail.setBackgroundResource(R.drawable.personal_blue);
                    }
                    if (party.equals("R")){
                        String temp_party = "Republican";
                        currparty.setText(temp_party);
                        box_detail.setBackgroundResource(R.drawable.personal_red);
                    }
                    if (party.equals("ID")){
                        String temp_party = "Independent";
                        currparty.setText(temp_party);
                        box_detail.setBackgroundResource(R.drawable.personal_i_box);
                    }
                    currname.setText(name);

                    currwebsite.setText(website);
                    curremail.setText(email);

                    String url = "https://api.propublica.org/congress/v1/members/"+id+".json";
                    findCommittees(url, id);



                } catch (JSONException e) {
                    return;
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
        Volley.newRequestQueue(DetailedActivity.this).add(jsonObjectRequest);
    }
    public void findCommittees(String url, final String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int num_committees = response.getJSONArray("results").getJSONObject(0).getJSONArray("roles").getJSONObject(0)
                            .getJSONArray("committees").length();
                    int counter = 0;
                    String committees = "";
                    while (counter < num_committees) {
                        committees += String.valueOf(counter+1) + ". " +response.getJSONArray("results").getJSONObject(0).getJSONArray("roles").getJSONObject(0)
                                .getJSONArray("committees").getJSONObject(counter).getString("name") + "\n";

                        counter++;
                    }
                    committees = committees.substring(0, committees.length() - 2);
                    currcom.setText(String.valueOf(committees));

                    String billurl = "https://api.propublica.org/congress/v1/members/"+id+"/bills/introduced.json";
                    findBills(billurl);


                } catch (JSONException e) {
                    currcom.setText("Failure");
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
        Volley.newRequestQueue(DetailedActivity.this).add(jsonObjectRequest);
    }

    public void findBills(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int num_bills = response.getJSONArray("results").getJSONObject(0).getJSONArray("bills").length();
                    int counter = 0;
                    String bills = "";
                    if (num_bills > 5) {
                        while (counter < 5) {
                            String bill = response.getJSONArray("results").getJSONObject(0).getJSONArray("bills")
                                    .getJSONObject(counter).getString("short_title");
                            if (bill.substring(bill.length()-1).equals(".")) {
                                bill = bill.substring(0, bill.length() - 1);
                            }
                            String date = response.getJSONArray("results").getJSONObject(0).getJSONArray("bills").getJSONObject(counter).getString("introduced_date") + "\n";

                            bills += date + bill + "\n"+"\n";
                            //bills += String.valueOf(counter+1) + ". "+bill + "\n"  + "              Date Introduced: " + date;
                            counter++;
                        }
                        currbill.setText(bills);
                    } else {
                        while (counter < num_bills) {
                            String bill = response.getJSONArray("results").getJSONObject(0).getJSONArray("bills")
                                    .getJSONObject(counter).getString("short_title");
                            if (bill.substring(bill.length()-1).equals(".")) {
                                bill = bill.substring(0, bill.length() - 1);
                            }
                            String date = response.getJSONArray("results").getJSONObject(0).getJSONArray("bills").getJSONObject(counter).getString("introduced_date") + "\n";
                            bills += date + bill + "\n"+"\n";
                            counter++;
                        }
                        currbill.setText(bills);
                    }



                } catch (JSONException e) {
                    currbill.setText("Failure");
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
        Volley.newRequestQueue(DetailedActivity.this).add(jsonObjectRequest);
    }
}
