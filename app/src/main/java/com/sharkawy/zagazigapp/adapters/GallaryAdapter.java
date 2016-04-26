package com.sharkawy.zagazigapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.dataModels.Photo;
import com.squareup.picasso.Picasso;

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

        String yt_thumbnail_url = "http://mashaly.net/" + trailer.getPhotoURL();
//        String yt_thumbnail_url = "http://mashaly.net/" +"/places_imgs/icons/0.jpg";
        Picasso.with(getContext()).load(yt_thumbnail_url).into(viewHolder.imageView);

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


}
