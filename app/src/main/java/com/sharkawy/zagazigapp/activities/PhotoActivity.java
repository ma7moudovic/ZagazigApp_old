package com.sharkawy.zagazigapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.adapters.FullScreenImageAdapter;
import com.sharkawy.zagazigapp.dataModels.Photo;
import com.sharkawy.zagazigapp.utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity {

    ImageView imageView ;
    String EXTRA_IMAGE ="extra_image";
    String EXTRA_IMAGES_OBJECTS ="extra_object";
    String URL ;
    int position =0 ;
    private Utils utils;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    JSONObject jsonObject ;
    ArrayList<Photo> arrayList = new ArrayList<Photo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        if(getIntent()!=null&&getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(EXTRA_IMAGE)||getIntent().getExtras().containsKey(EXTRA_IMAGES_OBJECTS)){
                position = getIntent().getExtras().getInt(EXTRA_IMAGE);
                try {
                    jsonObject = new JSONObject(getIntent().getExtras().getString(EXTRA_IMAGES_OBJECTS));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        utils = new Utils(getApplicationContext());
        adapter = new FullScreenImageAdapter(this,arrayList);
        viewPager.setAdapter(adapter);

        try {
            if(jsonObject.getJSONArray("imagesPathes").length()==0||jsonObject.getJSONArray("imagesPathes")==null){

            }else {
                for (int i = 0; i < jsonObject.getJSONArray("imagesPathes").length(); i++) {
                    arrayList.add(new Photo(jsonObject.getJSONArray("imagesPathes").getJSONObject(i).getString("path"),jsonObject.getJSONArray("imagesPathes").getJSONObject(i).getString("thumb")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
        // displaying selected image first
        viewPager.setCurrentItem(position);
    }

}
