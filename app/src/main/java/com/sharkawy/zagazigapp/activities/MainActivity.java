package com.sharkawy.zagazigapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.RecyclerItemClickListener;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.sharkawy.zagazigapp.utilities.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner s1;
    Button btn_cat_1 ,btn_cat_2,btn_cat_3,btn_cat_4,btn_cat_5;
    ImageButton Image_btn_fav ,Image_btn_offer , Image_btn_job ,Image_btn_course ;
    EditText search_input ;
    TabLayout tabLayout ;
    ProgressDialog pDialog ;
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        s1=(Spinner)findViewById(R.id.spinner);
         search_input = (EditText) findViewById(R.id.etsearch);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.msg_loading));

        String [] arr ={"كل المناطق","القومية ","شارع المحافظة","مفارق المنصورة","فلل الجامعة","حي الزهور","المنتزة","شارع البحر","المحطة","شارع مديرالامن","عمر افندي","حي ثاني","شارع الغشام" ,"عمارة الاوقاف"};
        String [] TAGS = {"مطاعم","كافيهات","سينمات","هدوم ولادى","هدوم بناتى","هدوم اطفال","موبيلات ولابات","جيم شبابي","جيم بناتى","مراكز تجميل","قاعات افراح","ستوديو تصوير","فوتوجرافيك","مستشفيات","عيادات","خدمات عربيات"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, arr);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

//        final TabLayout.Tab favorites = tabLayout.newTab();
//        final TabLayout.Tab offers = tabLayout.newTab();
//        final TabLayout.Tab job = tabLayout.newTab();
//        final TabLayout.Tab courses = tabLayout.newTab();
//        final TabLayout.Tab home = tabLayout.newTab();

//        favorites.setText("Favorites");
//        offers.setText("Offers");
//        job.setText("Job");
//        courses.setText("Courses");
//        home.setText("Home");

//        tabLayout.addTab(home,0);
//        tabLayout.addTab(favorites, 1);
//        tabLayout.addTab(offers, 2);
//        tabLayout.addTab(job, 3);
//        tabLayout.addTab(courses,4);

//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                if(tab.getPosition()==1){
////                    Toast.makeText(MainActivity.this,"Favorities", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(MainActivity.this,FavorivtesActivity.class);
//                    i.putExtra("type",1);
//                    startActivity(i);
//
//                }else if(tab.getPosition()==2){
////                    Toast.makeText(MainActivity.this,"Offers", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(MainActivity.this,SectorActivity.class);
//                    i.putExtra("type",2);
//                    startActivity(i);
//                }else if(tab.getPosition()==3){
////                    Toast.makeText(MainActivity.this,"JOBS", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(MainActivity.this,SectorActivity.class);
//                    i.putExtra("type",3);
//                    startActivity(i);
//                }else if(tab.getPosition()==4){
////                    Toast.makeText(MainActivity.this,"Courses", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(MainActivity.this,SectorActivity.class);
//                    i.putExtra("type",4);
//                    startActivity(i);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        s1.setAdapter(dataAdapter);
        btn_cat_1=(Button)findViewById(R.id.btn_cat_1);
        btn_cat_2=(Button)findViewById(R.id.btn_cat_2);
        btn_cat_3=(Button)findViewById(R.id.btn_cat_3);
        btn_cat_4=(Button)findViewById(R.id.btn_cat_4);
        btn_cat_5=(Button)findViewById(R.id.btn_cat_5);

        Image_btn_fav = (ImageButton) findViewById(R.id.Btn_fav);
        Image_btn_offer = (ImageButton) findViewById(R.id.Btn_offer);
        Image_btn_job = (ImageButton) findViewById(R.id.Btn_job);
        Image_btn_course = (ImageButton) findViewById(R.id.Btn_course);

        Image_btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Toast.makeText(MainActivity.this,"Favorities", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,FavorivtesActivity.class);
                    i.putExtra("type",1);
                    startActivity(i);
            }
        });
        Image_btn_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Toast.makeText(MainActivity.this,"Offers", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,SectorActivity.class);
                    i.putExtra("type",2);
                    startActivity(i);
            }
        });
        Image_btn_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Toast.makeText(MainActivity.this,"JOBS", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,SectorActivity.class);
                    i.putExtra("type",3);
                    startActivity(i);
            }
        });
        Image_btn_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"Courses", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,SectorActivity.class);
                    i.putExtra("type",4);
                    startActivity(i);
            }
        });
        Animation animation = new TranslateAnimation(0, 500,0, 0);
        animation.setDuration(1000);
        btn_cat_1.startAnimation(animation);
        btn_cat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CategoryActivity.class);
                i.putExtra("cat_index",1);
                startActivity(i);
            }
        });
        btn_cat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CategoryActivity.class);
                i.putExtra("cat_index",2);
                startActivity(i);
            }
        });
        btn_cat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CategoryActivity.class);
                i.putExtra("cat_index",3);
                startActivity(i);
            }
        });
        btn_cat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CategoryActivity.class);
                i.putExtra("cat_index",4);
                startActivity(i);
            }
        });
        btn_cat_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CategoryActivity.class);
                i.putExtra("cat_index",5);
                startActivity(i);
            }
        });

        search_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    String url="";
                    if(search_input.getText().length()!=0){
                        String tmp_url = "http://176.32.230.50/zagapp.com/handler.php?action=search&name=";
                        if(s1.getSelectedItemPosition()==0){

                            url ="http://176.32.230.50/zagapp.com/handler.php??action=search&&name="+search_input.getText();
                        }else {
                            url ="http://176.32.230.50/zagapp.com/handler.php??action=search&area="+s1.getSelectedItemPosition()+"&name="+search_input.getText();
                        }
                        String URL = null;
                        try {
                            URL = URLDecoder.decode(URLEncoder.encode(url, "iso8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(MainActivity.this,, Toast.LENGTH_SHORT).show();
                        makeJsonObjectRequest(url);
//                        Intent i = new Intent(MainActivity.this,SearchResultActivity.class);
//                        startActivity(i);
                    }

                    return true;
                }
                return false;
            }
        });

    }
    private void makeJsonObjectRequest(String URL) {
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

//                        Toast.makeText(MainActivity.this,response.toString(), Toast.LENGTH_LONG).show();
                        JSONArray data = response.getJSONArray("data");

                        Intent intent = new Intent(MainActivity.this,SearchResultActivity.class);
                        intent.putExtra("data",data.toString());
                        startActivity(intent);

                    }else if(response.getString("message").toString().equals("no data recived")){
                        Toast.makeText(MainActivity.this,"no places with this name", Toast.LENGTH_LONG).show();

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
                    Toast.makeText(MainActivity.this,"Connection Error.",Toast.LENGTH_LONG).show();
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
