package com.sharkawy.zagazigapp.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.adapters.SearchResultAdapter;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.sharkawy.zagazigapp.utilities.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    Spinner sp_areas ;
    ProgressDialog pDialog ;
    private static String TAG = CategoryActivity.class.getSimpleName();

    RecyclerView recyclerView ;
    SearchResultAdapter adapter ;
    RecyclerView.LayoutManager layoutManager ;
    private List<Place> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        int index = getIntent().getExtras().getInt("cat_index");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.msg_loading));

        String [] arr ={"القومية ","شارع المحافظة","مفارق المنصورة","فلل الجامعة","حي الزهور","المنتزة","شارع البحر","المحطة","شارع مديرالامن","عمر افندي","عمارة الاوقاف","حي ثاني","شارع الغشام"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, arr);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        sp_areas = (Spinner) findViewById(R.id.sp_aresa);
        sp_areas.setAdapter(dataAdapter);

        list= new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerListview_category);
        layoutManager=new LinearLayoutManager(this);
        adapter=new SearchResultAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        String URL = "http://www.mashaly.net/handler.php?action=search&category="+index ;
        makeJsonObjectRequest(URL);


        Toast.makeText(CategoryActivity.this,adapter.getItemCount()+"", Toast.LENGTH_LONG).show();

    }

    private void makeJsonObjectRequest(String URL) {
        Toast.makeText(CategoryActivity.this,URL, Toast.LENGTH_LONG).show();

        showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                // Parsing json object response
                hidepDialog();
                try {

//                    Toast.makeText(CategoryActivity.this,response.getString("message").toString(), Toast.LENGTH_LONG).show();
                    if(response.getString("message").toString().equals("success")){

                        Toast.makeText(CategoryActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        JSONArray data = response.getJSONArray("data");

                    }else if(response.getString("message").toString().equals("no data recived")){
                        Toast.makeText(CategoryActivity.this,"no places with this name", Toast.LENGTH_LONG).show();

                    }else if(response.getString("message").toString().equals("sucess")){

                        JSONArray data = response.getJSONArray("data");
                        Toast.makeText(CategoryActivity.this, data.toString(), Toast.LENGTH_LONG).show();
                        for(int i =0 ;i<data.length();i++){
                            adapter.add(new Place(data.getJSONObject(i)));
                        }
                        Toast.makeText(CategoryActivity.this, adapter.getItemCount()+"", Toast.LENGTH_LONG).show();


                    }
                    else{
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
                    Toast.makeText(CategoryActivity.this,"Connection Error.",Toast.LENGTH_LONG).show();
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
