package com.sharkawy.zagazigapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.adapters.AnnonuceAdapter;
import com.sharkawy.zagazigapp.adapters.SearchResultAdapter;
import com.sharkawy.zagazigapp.dataModels.Annonce;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.sharkawy.zagazigapp.utilities.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SectorActivity extends AppCompatActivity {

    ProgressDialog pDialog ;
    private static String TAG = SectorActivity.class.getSimpleName();
    String url ="";
    RecyclerView recyclerView ;
    LinearLayoutManager layoutManager ;
    AnnonuceAdapter annonuceAdapter ;
    ArrayList<Annonce> list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.msg_loading));

        list=new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerListView_announces);
        layoutManager=new LinearLayoutManager(this);
        annonuceAdapter=new AnnonuceAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(annonuceAdapter);

        if(getIntent().getExtras().getInt("type")==1){
            url="http://www.mashaly.net/handler.php?action=announce&type=offer";
        }else if(getIntent().getExtras().getInt("type")==2){
            url="http://www.mashaly.net/handler.php?action=announce&type=job";
        }else if(getIntent().getExtras().getInt("type")==3){
            url="http://www.mashaly.net/handler.php?action=announce&type=course";
        }

        makeJsonObjectRequest(url);

    }
    private void makeJsonObjectRequest(String URL) {
//        Toast.makeText(SectorActivity.this, URL, Toast.LENGTH_LONG).show();

        showpDialog();
//        String URL ="http://www.mashaly.net/zag.php?filter="+s1.getSelectedItem().toString().replace(" ","%20");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                // Parsing json object response
                hidepDialog();
//                Toast.makeText(MainActivity.this,response.toString(), Toast.LENGTH_LONG).show();
                try {
                    if(response.getString("message").toString().equals("success")){

//                        Toast.makeText(SectorActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        JSONArray data = response.getJSONArray("data");
                        for(int i =0 ;i<data.length();i++){
                            annonuceAdapter.add(new Annonce(data.getJSONObject(i)));
                        }

                    }else if(response.getString("message").toString().equals("no data recived")){
                        Toast.makeText(SectorActivity.this,"no places with this name", Toast.LENGTH_LONG).show();

                    } else{
                        Toast.makeText(getApplicationContext(),
                                "Something went wrong..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                hidepDialog();

                String responseBody = null;
                JSONObject jsonObject=null ;
                try {

                    responseBody = new String( error.networkResponse.data, "utf-8" );
                    jsonObject = new JSONObject( responseBody );

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SectorActivity.this,"Connection Error.",Toast.LENGTH_LONG).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
