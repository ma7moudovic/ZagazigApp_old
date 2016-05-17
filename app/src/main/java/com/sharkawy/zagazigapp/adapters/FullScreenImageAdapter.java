package com.sharkawy.zagazigapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.dataModels.Photo;
import com.sharkawy.zagazigapp.utilities.TouchImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 5/15/2016.
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private Activity _activity;
//    private ArrayList<String> _imagePaths;
    private List<Photo> mObjects;
    private LayoutInflater inflater;

    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  List<Photo> mObjects) {
        this._activity = activity;
        this.mObjects = mObjects;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_image_full_screen, container,
                false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
//        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
//        imgDisplay.setImageBitmap(bitmap);

        if (!Glide.isSetup()) {
            GlideBuilder gb = new GlideBuilder(_activity);
            DiskCache dlw = DiskLruCacheWrapper.get(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myCatch/"), 250 * 1024 * 1024);
            gb.setDiskCache(dlw);
            Glide.setup(gb);
        }
        Glide.with(_activity)
                .load("http://176.32.230.50/zagapp.com/"+mObjects.get(position).getPhotoURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgDisplay);

        // close button click event
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                _activity.finish();
//            }
//        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}

