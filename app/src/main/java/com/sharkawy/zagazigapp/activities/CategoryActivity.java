package com.sharkawy.zagazigapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.RecyclerItemClickListener;
import com.sharkawy.zagazigapp.adapters.SearchResultAdapter;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.sharkawy.zagazigapp.utilities.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    Spinner sp_areas , sp_tags;
    ProgressDialog pDialog ;
    private static String TAG = CategoryActivity.class.getSimpleName();

    RecyclerView recyclerView ;
    SearchResultAdapter adapter ;
    RecyclerView.LayoutManager layoutManager ;
    private List<Place> list;

    String URL ,area,sub_cat;
    int index ;
    boolean isFirstTime=true ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        index = getIntent().getExtras().getInt("cat_index");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.msg_loading));

        String [] arr ={"كل المناطق","القومية ","شارع المحافظة","مفارق المنصورة","فلل الجامعة","حي الزهور","المنتزة","شارع البحر","المحطة","شارع مديرالامن","عمر افندي","حي ثاني","شارع الغشام" ,"عمارة الاوقاف"};
        String [] subcategory = {"الكل","مطاعم","كافيهات","سينمات","هدوم ولادى","هدوم بناتى","هدوم اطفال","موبيلات ولابات","جيم شبابي","جيم بناتى","مراكز تجميل","قاعات افراح","ستوديو تصوير","فوتوجرافيك","مستشفيات","عيادات","خدمات عربيات"};
        String [] serviceTags={"سندوتشات","بيتزا","كشري","مشويات","حلويات","كريب","اكل بيتي","هدوم خروج","بدل","احذية","توكيلات","هدوم خروج","بيجامات ولانجري","اكسسورات وميك اب","ششنط واحذية"};

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, arr);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> tagsAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, subcategory);
        tagsAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        tagsAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        sp_areas = (Spinner) findViewById(R.id.sp_aresa);
        sp_areas.setAdapter(dataAdapter);

        sp_tags= (Spinner) findViewById(R.id.sp_subCats);
        sp_tags.setAdapter(tagsAdapter);

        list= new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerListview_category);
        layoutManager=new LinearLayoutManager(this);
        adapter=new SearchResultAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        area="";
        sub_cat="";

//        Toast.makeText(CategoryActivity.this,adapter.getItemCount()+"", Toast.LENGTH_LONG).show();

        sp_areas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    isFirstTime=false;
                    area=position+"";
                }else {
                    area="";
                }
                if(!isFirstTime){
                    makeJsonObjectRequest();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_tags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    isFirstTime=false;
                    sub_cat = position+"";
                }else {
                    sub_cat="";
                }

                if(!isFirstTime){
                    makeJsonObjectRequest();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent i = new Intent(CategoryActivity.this, DetailedItemActivity.class);
                        i.putExtra("Item",adapter.getItem(position).getObject().toString());
                        startActivity(i);
                    }

                })
        );
        makeJsonObjectRequest();

    }

    private void makeJsonObjectRequest() {
        URL = "http://176.32.230.50/zagapp.com/handler.php?action=search&category="+index+"&area="+area+ "&sub_category="+sub_cat;

        String tmp_url = "http://176.32.230.50/zagapp.com/handler.php?action=search&name=";
        Toast.makeText(CategoryActivity.this,URL, Toast.LENGTH_LONG).show();

        showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                // Parsing json object response
                hidepDialog();
                adapter.clear();
                try {
//                    Toast.makeText(CategoryActivity.this,response.getString("message").toString(), Toast.LENGTH_LONG).show();
                    if(response.getString("message").toString().equals("sucess")){

//                        Toast.makeText(CategoryActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        JSONArray data = response.getJSONArray("data");

                    }else if(response.getString("message").toString().equals("no data recived")){
                        Toast.makeText(CategoryActivity.this,"no places with this name", Toast.LENGTH_LONG).show();

                    }else if(response.getString("message").toString().equals("success")){

                        JSONArray data = response.getJSONArray("data");
//                        Toast.makeText(CategoryActivity.this, data.toString(), Toast.LENGTH_LONG).show();
                        adapter.clear();
                        for(int i =0 ;i<data.length();i++){
                            adapter.add(new Place(data.getJSONObject(i)));
//                            Toast.makeText(CategoryActivity.this,data.getJSONObject(i).getString("icoImage"), Toast.LENGTH_LONG).show();
                        }

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
