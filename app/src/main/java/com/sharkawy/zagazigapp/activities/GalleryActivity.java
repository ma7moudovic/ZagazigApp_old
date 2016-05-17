package com.sharkawy.zagazigapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.adapters.ThumbAdapter;
import com.sharkawy.zagazigapp.dataModels.Image;
import com.sharkawy.zagazigapp.dataModels.Photo;
import com.sharkawy.zagazigapp.utilities.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private static final String EXTRA_IMAGES_FLAG = "extra_flag";
    private String TAG = MainActivity.class.getSimpleName();
    private static final String endpoint = "http://api.androidhive.info/json/glide.json";
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private ThumbAdapter mAdapter;
    private RecyclerView recyclerView;
    JSONObject jsonObject ;
    String EXTRA_IMAGES_OBJECTS ="extra_object";
    int position =0 ;
    String FLAG ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new ThumbAdapter(getApplicationContext(), images);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if(getIntent()!=null&&getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(EXTRA_IMAGES_OBJECTS)){
                try {
                    jsonObject = new JSONObject(getIntent().getExtras().getString(EXTRA_IMAGES_OBJECTS));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(getIntent().getExtras().containsKey(EXTRA_IMAGES_FLAG)){
                FLAG=getIntent().getExtras().getString(EXTRA_IMAGES_FLAG);
            }
        }
        if(FLAG.equals("GALLERY")){

            try{
                if(jsonObject.getJSONArray("galleryImagesPaths").length()==0||jsonObject.getJSONArray("galleryImagesPaths")==null){
                    Toast.makeText(GalleryActivity.this,"No Photos for that place",Toast.LENGTH_SHORT).show();

                }else {
                    for (int i = 0; i < jsonObject.getJSONArray("galleryImagesPaths").length(); i++) {
                        images.add(new Image(jsonObject.getJSONArray("galleryImagesPaths").getJSONObject(i)));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(FLAG.equals("MENUS")){
            try{
                if(jsonObject.getJSONArray("productsImagesPaths").length()==0||jsonObject.getJSONArray("productsImagesPaths")==null){
                    Toast.makeText(GalleryActivity.this,"No Menus for that place",Toast.LENGTH_SHORT).show();
                }else {
                    for (int i = 0; i < jsonObject.getJSONArray("productsImagesPaths").length(); i++) {
                        images.add(new Image(jsonObject.getJSONArray("productsImagesPaths").getJSONObject(i)));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        recyclerView.addOnItemTouchListener(new ThumbAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new ThumbAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
