package com.sharkawy.zagazigapp.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.dataModels.Photo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity {

    ImageView imageView ;
    String EXTRA_IMAGE ="extra_image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageView = (ImageView) findViewById(R.id.imageID);
        if(getIntent()!=null&&getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(EXTRA_IMAGE)){
                Picasso.with(this).load("http://mashaly.net/"+getIntent().getExtras().getString(EXTRA_IMAGE)).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            String root = Environment.getExternalStorageDirectory().toString();
                            File myDir = new File(root + "/ZagApp");
                            if (!myDir.exists()) {
                                myDir.mkdirs();
                            }
                            String name = new Date().toString() + ".jpg";
                            myDir = new File(myDir, name);
                            FileOutputStream out = new FileOutputStream(myDir);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();
                            Toast.makeText(PhotoActivity.this,"imageDownloaded",Toast.LENGTH_SHORT).show();
                        } catch(Exception e){}
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {}
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
                });
            }
        }
    }
}
