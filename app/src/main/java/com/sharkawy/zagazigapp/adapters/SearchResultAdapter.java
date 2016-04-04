package com.sharkawy.zagazigapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.dataModels.Place;

import java.util.List;

/**
 * Created by T on 4/3/2016.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{
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
        holder.descView.setText(pObjects.get(position).getDesc());
        holder.tab1.setText(pObjects.get(position).getTag());
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
        protected TextView tab2 ;


        public ViewHolder(View itemView) {
            super(itemView);
//            textView =  (TextView) itemView.findViewById(R.id.list_item);
            imageView = (ImageView) itemView.findViewById(R.id.photo);
            titleView = (TextView) itemView.findViewById(R.id.tvtitle);
            descView = (TextView) itemView.findViewById(R.id.tvdesc);
            tab1 = (TextView) itemView.findViewById(R.id.tvtxt1);
            tab2 = (TextView) itemView.findViewById(R.id.tvtxt2);
        }

    }//holder
}
