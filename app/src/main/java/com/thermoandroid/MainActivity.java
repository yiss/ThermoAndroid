package com.thermoandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tvSalon;
    TextView tvChambre;
    private String salonValue;
    private String chambreValue;
    private final String urlSalon = "https://hometemp.herokuapp.com/Salon";
    private final String urlChambre = "https://hometemp.herokuapp.com/Chambre-1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSalon = (TextView) findViewById(R.id.salonValue);
        tvChambre = (TextView) findViewById(R.id.chambreValue);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshValues();
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }
    private void refreshValues()
    {
        JsonObjectRequest jsonRequestSalon = new JsonObjectRequest
                (Request.Method.GET, urlSalon, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            double temp = response.getDouble("temp");
                            Log.d("TAG",temp+"");
                            salonValue = temp+"° C";
                            tvSalon.setText(salonValue);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        JsonObjectRequest jsonRequestChambre = new JsonObjectRequest
                (Request.Method.GET, urlChambre, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            double temp = response.getDouble("temp");
                            chambreValue = temp+"° C";
                            tvChambre.setText(chambreValue);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        Volley.newRequestQueue(this).add(jsonRequestSalon);
        Volley.newRequestQueue(this).add(jsonRequestChambre);
    }
}
