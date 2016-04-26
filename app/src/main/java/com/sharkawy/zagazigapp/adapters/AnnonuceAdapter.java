package com.sharkawy.zagazigapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.dataModels.Annonce;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by T on 4/26/2016.
 */
public class AnnonuceAdapter  extends RecyclerView.Adapter<AnnonuceAdapter.ViewHolder>{

    private final Context pContext;
    private final Annonce pLock = new Annonce();
    private List<Annonce> pObjects;

    public AnnonuceAdapter(Context pContext, List<Annonce> pObjects) {
        this.pContext = pContext;
        this.pObjects = pObjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.annonce_item, parent, false);

        // create a new view
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public Context getpContext() {
        return pContext;
    }
    public void add(Annonce object) {
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
    public void setData(List<Annonce> data) {
        clear();
        for (Annonce product : data) {
            add(product);
        }
    }
    public List<Annonce> getData(){
        return pObjects ;
    }
    public void removeAt(int position) {
        pObjects.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pObjects.size());
    }
    public Annonce getItem(int position){
        return pObjects.get(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.titleView.setText(pObjects.get(position).getPlaceName());
        holder.annonuceDescView.setText(pObjects.get(position).getContent());
//        Picasso.with(getpContext()).load("http://mashaly.net/" +"/places_imgs/icons/0.jpg").into(holder.imageView);
        Picasso.with(getpContext()).load("http://mashaly.net/"+pObjects.get(position).getIconURL()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return pObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //        protected TextView textView;
        protected ImageView imageView;
        protected TextView titleView;
        protected TextView annonuceDescView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photo);
            titleView = (TextView) itemView.findViewById(R.id.tvtitle);
            annonuceDescView = (TextView) itemView.findViewById(R.id.annonceDesc);
        }

    }//holder
}
