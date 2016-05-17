package com.sharkawy.zagazigapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.activities.PhotoActivity;
import com.sharkawy.zagazigapp.dataModels.Photo;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


/**
 * Created by T on 4/26/2016.
 */
public class GallaryAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final Photo photo = new Photo();
    private List<Photo> mObjects;

    public GallaryAdapter(Context context, List<Photo> mObjects) {
        this.mContext = context;
        this.mObjects = mObjects;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Context getContext() {
        return mContext;
    }

    public void add(Photo object) {
        synchronized (photo) {
            mObjects.add(object);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        synchronized (photo) {
            mObjects.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Photo getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_photo, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        final Photo trailer = (Photo) getItem(position);

        viewHolder = (ViewHolder) view.getTag();

        String yt_thumbnail_url = "http://mashaly.net/" + trailer.getPhotoThumb();
//        String yt_thumbnail_url = "http://mashaly.net/" +"/places_imgs/icons/0.jpg";
//        Picasso.with(getContext()).load(yt_thumbnail_url).into(viewHolder.imageView);
//        ImageHandler(trailer.getPhotoThumb(),viewHolder.imageView);
        if (!Glide.isSetup()) {
            GlideBuilder gb = new GlideBuilder(getContext());
            DiskCache dlw = DiskLruCacheWrapper.get(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myCatch/"), 250 * 1024 * 1024);
            gb.setDiskCache(dlw);
            Glide.setup(gb);
        }
        Glide.with(getContext())
                .load("http://176.32.230.50/zagapp.com/"+trailer.getPhotoURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.imageView);

//        viewHolder.nameView.setText(trailer.getName());

        return view;
    }

    public static class ViewHolder {
        public final ImageView imageView;
//        public final TextView nameView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.trailer_image);
//            nameView = (TextView) view.findViewById(R.id.trailer_name);
        }
    }
/*
    private void ImageHandler(final String URL , final ImageView imageV) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/ZagApp/"+URL.replace("/","_"));
        if (myDir.exists()) {
            // Do Whatever you want sdcard exists
            Bitmap bitmap1 = BitmapFactory.decodeFile(myDir.getAbsolutePath());
            imageV.setImageBitmap(bitmap1);
        }
        else{
//            Toast.makeText(getContext(), "not Exists", Toast.LENGTH_SHORT).show();
            Picasso.with(getContext()).load("http://176.32.230.50/zagapp.com/"+URL).resize(150,150).into(new Target() {
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
//                        Toast.makeText(getContext(), "imageDownloaded", Toast.LENGTH_SHORT).show();
                        imageV.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
//                    Toast.makeText(getContext(),errorDrawable.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
//                    Toast.makeText(getContext(),placeHolderDrawable.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
*/
}
