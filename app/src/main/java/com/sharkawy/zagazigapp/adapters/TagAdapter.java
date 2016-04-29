package com.sharkawy.zagazigapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.sharkawy.zagazigapp.dataModels.Tag;

import java.util.List;

/**
 * Created by T on 4/22/2016.
 */
public class TagAdapter  extends RecyclerView.Adapter<TagAdapter.ViewHolder>{

    private final Context pContext;
    private final Place pLock = new Place();
    private List<Tag> pObjects;

    public TagAdapter(Context pContext, List<Tag> pObjects) {
        this.pContext = pContext;
        this.pObjects = pObjects;
    }

    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag, parent, false);

        // create a new view
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public Context getpContext() {
        return pContext;
    }
    public void add(Tag object) {
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
    public void setData(List<Tag> data) {
        clear();
        for (Tag tag : data) {
            add(tag);
        }
    }
    public List<Tag> getData(){
        return pObjects ;
    }
    public void removeAt(int position) {
        pObjects.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pObjects.size());
    }
    public Tag getItem(int position){
        return pObjects.get(position);
    }

    @Override
    public void onBindViewHolder(TagAdapter.ViewHolder holder, int position) {
        holder.tagView.setText(pObjects.get(position).getTag());
    }

    @Override
    public int getItemCount() {
        return pObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //        protected TextView textView;
        protected TextView tagView;

        public ViewHolder(View itemView) {
            super(itemView);
            tagView = (TextView) itemView.findViewById(R.id.tv_tag);
        }//holder
    }
}
