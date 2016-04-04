package com.sharkawy.zagazigapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.RecyclerItemClickListener;
import com.sharkawy.zagazigapp.adapters.SearchResultAdapter;
import com.sharkawy.zagazigapp.dataModels.Place;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    SearchResultAdapter adapter ;
    RecyclerView.LayoutManager layoutManager ;
    private List<Place> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list= new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerListview_searchResult);
        layoutManager=new LinearLayoutManager(this);
        adapter=new SearchResultAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

//        list.add(new Place());
//        list.add(new Place());
//        list.add(new Place());
//        list.add(new Place());
//        list.add(new Place());
//        list.add(new Place());
//        list.add(new Place());
//
//        adapter.notifyDataSetChanged();
        adapter.add(new Place());
        adapter.add(new Place());
        adapter.add(new Place());
        adapter.add(new Place());
        adapter.add(new Place());
        adapter.add(new Place());
        adapter.add(new Place());
        adapter.add(new Place());

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        adapter.getItem(position);
                        Intent i = new Intent(SearchResultActivity.this,DetailedItemActivity.class);
                        startActivity(i);
                    }

                })
        );
    }

}
