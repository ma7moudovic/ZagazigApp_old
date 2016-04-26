package com.sharkawy.zagazigapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linearlistview.LinearListView;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.adapters.GallaryAdapter;
import com.sharkawy.zagazigapp.adapters.TagAdapter;
import com.sharkawy.zagazigapp.dataModels.Photo;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.sharkawy.zagazigapp.dataModels.Tag;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailedItemActivity extends AppCompatActivity {
    String EXTRA_IMAGE ="extra_image";
    TextView title ,category , desc ,address ,tel ;
    ImageView logo ;
    RecyclerView recyclerView ;
    private LinearListView mTrailersView;
    GallaryAdapter gallaryAdapter ;
    List<Tag> tags ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detials);

        String [] subcategory = {"مطاعم","كافيهات","سينمات","هدوم ولادى","هدوم بناتى","هدوم اطفال","موبيلات ولابات","جيم شبابي","جيم بناتى","مراكز تجميل","قاعات افراح","ستوديو تصوير","فوتوجرافيك","مستشفيات","عيادات","خدمات عربيات"};
        String [] serviceTags={"سندوتشات","بيتزا","كشري","مشويات","حلويات","كريب","اكل بيتي","هدوم خروج","بدل","احذية","توكيلات","هدوم خروج","بيجامات ولانجري","اكسسورات وميك اب","ششنط واحذية"};

        title = (TextView) findViewById(R.id.tvtitle);
        category = (TextView) findViewById(R.id.tvcat);
        desc = (TextView) findViewById(R.id.tvdesc);
        address = (TextView) findViewById(R.id.tvadd);
        tel = (TextView) findViewById(R.id.tvtel);
        logo = (ImageView) findViewById(R.id.photo);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerListview_tags_detailed);
        tags = new ArrayList<>();
        TagAdapter tagAdapter = new TagAdapter(this,tags);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(tagAdapter);

        mTrailersView = (LinearListView) findViewById(R.id.detailed_trailers);
        gallaryAdapter = new GallaryAdapter(this, new ArrayList<Photo>());
        mTrailersView.setAdapter(gallaryAdapter);
        //imagesPathes

//        gallaryAdapter.add(new Photo());
//        gallaryAdapter.add(new Photo());
//        gallaryAdapter.add(new Photo());
//        gallaryAdapter.add(new Photo());

        try {
            JSONObject jsonObject = new JSONObject(getIntent().getExtras().getString("Item"));

            Place place = new Place(jsonObject);
            title.setText(place.getName());

            category.setText(subcategory[Integer.parseInt(place.getCategoryID())-1]);
            desc.setText(place.getDesc());
            address.setText(place.getAddress());
            tel.setText(place.getTelephone());

            Picasso.with(this).load("http://mashaly.net/" +place.getImageURL()).into(logo);

            Toast.makeText(this,place.getObject().getJSONArray("serviceTags")+"",Toast.LENGTH_SHORT).show();
            for (int i = 0; i < place.getObject().getJSONArray("serviceTags").length(); i++) {
                tags.add(new Tag(place.getObject().getJSONArray("serviceTags").getString(i)));
            }


            tagAdapter.notifyDataSetChanged();


            for (int i = 0; i < place.getObject().getJSONArray("imagesPathes").length(); i++) {
                gallaryAdapter.add(new Photo(place.getObject().getJSONArray("imagesPathes").getJSONObject(i).getString("path")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        mTrailersView.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView linearListView, View view,
                                    int position, long id) {
//                Trailer trailer = (Trailer) mTrailerAdapter.getItem(position);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
//                startActivity(intent);

                Intent i = new Intent(DetailedItemActivity.this,PhotoActivity.class);
                i.putExtra(EXTRA_IMAGE,gallaryAdapter.getItem(position).getPhotoURL());
                startActivity(i);
            }
        });

    }
}
