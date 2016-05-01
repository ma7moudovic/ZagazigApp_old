package com.sharkawy.zagazigapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.sharkawy.zagazigapp.dataModels.Tag;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 4/3/2016.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{

    RecyclerView.LayoutManager layoutManager;

    private final Context pContext;
    private final Place pLock = new Place();
    private List<Place> pObjects;

    public SearchResultAdapter(Context pContext, List<Place> pObjects) {
        this.pContext = pContext;
        this.pObjects = pObjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row, parent, false);

        // create a new view
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public Context getpContext() {
        return pContext;
    }
    public void add(Place object) {
        synchronized (pLock) {
            pObjects.add(object);
        }
        notifyDataSetChanged();
    }
    public void clear() {
        synchronized (pLock) {
            pObjects.clear();
        }
        notifyDataSetChanged();
    }
    public void setData(List<Place> data) {
        clear();
        for (Place product : data) {
            add(product);
        }
    }
    public List<Place> getData(){
        return pObjects ;
    }
    public void removeAt(int position) {
        pObjects.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pObjects.size());
    }
    public Place getItem(int position){
        return pObjects.get(position);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleView.setText(pObjects.get(position).getName());
        holder.descView.setText(pObjects.get(position).getDesc());
//        holder.descView.setText(pObjects.get(position).getDesc());
//        holder.tab1.setText(pObjects.get(position).getTag());
//        Picasso.with(getpContext()).load("http://mashaly.net/" +"places_imgs//icons//29.jpg").into(holder.imageView);
//        Picasso.with(getpContext()).load("http://mashaly.net/"+pObjects.get(position).getImageURL()).into(holder.imageView);

//        Toast.makeText(getpContext(),pObjects.get(position).getImageURL() , Toast.LENGTH_LONG).show();
        ImageHandler(pObjects.get(position).getImageURL(),holder.imageView);
        try {
            List<Tag> tags = new ArrayList<>();
            for (int i = 0; i < pObjects.get(position).getObject().getJSONArray("serviceTags").length(); i++) {
                tags.add(new Tag(pObjects.get(position).getObject().getJSONArray("serviceTags").getString(i)));
            }
            TagAdapter tagAdapter = new TagAdapter(getpContext(),tags);
            holder.recyclerView.setLayoutManager(new GridLayoutManager(getpContext(),3));
//            holder.recyclerView.setLayoutManager(new LinearLayoutManager(getpContext(),LinearLayoutManager.VERTICAL,false));
            holder.recyclerView.setAdapter(tagAdapter);
            tagAdapter.notifyDataSetChanged();
//            Toast.makeText(getpContext(),tagAdapter.getItemCount()+" tag ",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }

    }

    @Override
    public int getItemCount() {
        return pObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //        protected TextView textView;
        protected ImageView imageView;
        protected TextView titleView;
        protected TextView descView;
        protected TextView tab1 ;
        protected RecyclerView recyclerView ;

        public ViewHolder(View itemView) {
            super(itemView);
//            textView =  (TextView) itemView.findViewById(R.id.list_item);
            imageView = (ImageView) itemView.findViewById(R.id.photo);
            titleView = (TextView) itemView.findViewById(R.id.tvtitle);
            descView = (TextView) itemView.findViewById(R.id.tvdesc);
            tab1 = (TextView) itemView.findViewById(R.id.tvtxt1);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerListview_tags);
        }

    }//holder

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
            Picasso.with(getpContext()).load("http://176.32.230.50/zagapp.com/"+URL).into(new Target() {
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
//                        Toast.makeText(getpContext(), "imageDownloaded", Toast.LENGTH_SHORT).show();
                        imageV.setImageBitmap(bitmap);

                    } catch (Exception e) {
//                        Toast.makeText(getpContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

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
