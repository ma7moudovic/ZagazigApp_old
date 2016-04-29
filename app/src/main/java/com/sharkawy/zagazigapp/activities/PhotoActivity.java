package com.sharkawy.zagazigapp.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
//    String URL = "http://mashaly.net/places_imgs//icons//29.jpg" ;
    String URL ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        if(getIntent()!=null&&getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(EXTRA_IMAGE)) {

                URL = getIntent().getExtras().getString(EXTRA_IMAGE);
            }
        }
        imageView = (ImageView) findViewById(R.id.imageID);

        ImageHandler(URL,imageView);

    }

    private void ImageHandler(final String URL , final ImageView imageV) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/ZagApp/"+URL.replace("/","_"));
        if (myDir.exists()) {
            // Do Whatever you want sdcard exists
            Toast.makeText(PhotoActivity.this, "Exists", Toast.LENGTH_SHORT).show();
            Bitmap bitmap1 = BitmapFactory.decodeFile(myDir.getAbsolutePath());
            imageV.setImageBitmap(bitmap1);

        }
        else{
            Toast.makeText(PhotoActivity.this, "not Exists", Toast.LENGTH_SHORT).show();
            Picasso.with(this).load("http://mashaly.net/"+URL).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    try {
                        String root = Environment.getExternalStorageDirectory().toString();
                        File myDir = new File(root + "/ZagApp");
                        if (!myDir.exists()) {
                            myDir.mkdirs();
                        }
//                    String name = new Date().toString() + ".jpg";
                        String name = URL.replace("/","_");

                        myDir = new File(myDir, name);
                        FileOutputStream out = new FileOutputStream(myDir);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.flush();
                        out.close();
                        Toast.makeText(PhotoActivity.this, "imageDownloaded", Toast.LENGTH_SHORT).show();
                        imageV.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        Toast.makeText(PhotoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }
    }
}
