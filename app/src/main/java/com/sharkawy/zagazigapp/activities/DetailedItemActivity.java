package com.sharkawy.zagazigapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linearlistview.LinearListView;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.adapters.GallaryAdapter;
import com.sharkawy.zagazigapp.adapters.TagAdapter;
import com.sharkawy.zagazigapp.dataModels.Photo;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.sharkawy.zagazigapp.dataModels.Tag;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailedItemActivity extends AppCompatActivity {
    String EXTRA_IMAGE ="extra_image";
    TextView title ,category , desc ,address ,tel ;
    ImageView logo ;
    RecyclerView recyclerView ;
    private LinearListView mTrailersView;
    GallaryAdapter gallaryAdapter ;
    List<Tag> tags ;
    Place place ;
    boolean exits =false ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String FAVORITES = "Product_Favorite";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailed_menu, menu);
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(getIntent().getExtras().getString("Item"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        place = new Place(jsonObject);
        final MenuItem action_favorite = menu.findItem(R.id.action_favorite);

        if(getCart(DetailedItemActivity.this)!=null&&getCart(DetailedItemActivity.this).contains(place)){
            action_favorite.setIcon(android.R.drawable.star_big_on);
        }else {
            action_favorite.setIcon(android.R.drawable.star_big_off);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        boolean ico = item.getIcon().equals(android.R.drawable.star_big_on );
        switch (id){
            case R.id.action_favorite:
                if(ico){
                    RemoveToFav();
//                    item.setIcon(android.R.drawable.star_big_off);
                }else {
                    AddToFav();
                    item.setIcon(android.R.drawable.star_big_on);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void AddToFav(){
        ArrayList<Place> tmp ;
        if (getCart(DetailedItemActivity.this) != null) {
            tmp = getCart(DetailedItemActivity.this);
            if(tmp.contains(place)){
                Toast.makeText(this,"Exists",Toast.LENGTH_SHORT).show();
            }else{
                tmp.add(place);
                Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show();
            }
        } else {
            tmp = new ArrayList<Place>();
            tmp.add(place);
        }
        saveToCart(DetailedItemActivity.this,tmp);
    }
    private void RemoveToFav(){
        ArrayList<Place> tmp =new ArrayList<>();
        if (getCart(DetailedItemActivity.this) != null) {
            tmp.addAll(getCart(DetailedItemActivity.this));
            if(tmp.contains(place)){
                tmp.remove(place);
                Toast.makeText(this,"Removed from my Favorites",Toast.LENGTH_SHORT).show();
            }
        }
        saveToCart(DetailedItemActivity.this, tmp);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detials);

        String [] subcategory = {"مطاعم","كافيهات","سينمات","هدوم ولادى","هدوم بناتى","هدوم اطفال","موبيلات ولابات","جيم شبابي","جيم بناتى","مراكز تجميل","قاعات افراح","ستوديو تصوير","فوتوجرافيك","مستشفيات","عيادات","خدمات عربيات"};
        String [] serviceTags={"سندوتشات","بيتزا","كشري","مشويات","حلويات","كريب","اكل بيتي","هدوم خروج","بدل","احذية","توكيلات","هدوم خروج","بيجامات ولانجري","اكسسورات وميك اب","ششنط واحذية"};

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

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

        try {
            JSONObject jsonObject = new JSONObject(getIntent().getExtras().getString("Item"));

            place = new Place(jsonObject);
            title.setText(place.getName());

            category.setText(subcategory[Integer.parseInt(place.getCategoryID())-1]);
            desc.setText(place.getDesc());
            address.setText(place.getAddress());
            tel.setText(place.getTelephone());

//            Picasso.with(this).load("http://mashaly.net/" +place.getImageURL()).into(logo);

            ImageHandler(place.getImageURL(),logo);
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
                Intent i = new Intent(DetailedItemActivity.this,PhotoActivity.class);
                i.putExtra(EXTRA_IMAGE,gallaryAdapter.getItem(position).getPhotoURL());
                startActivity(i);
            }
        });

    }
    public ArrayList<Place> getCart(Context context) {
        List<Place> favorites;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(FAVORITES)) {
            String jsonFavorites = sharedpreferences.getString(FAVORITES, null);
            Gson gson = new Gson();
            Place[] favoriteItems = gson.fromJson(jsonFavorites,
                    Place[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Place>(favorites);
        } else
            return null;
        return (ArrayList<Place>) favorites;
    }
    public void saveToCart(Context context, List<Place> favorites) {

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
        Toast.makeText(DetailedItemActivity.this,"Saved to your Favorites",Toast.LENGTH_SHORT).show();
    }
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
            Picasso.with(DetailedItemActivity.this).load("http://mashaly.net/"+URL).into(new Target() {
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
                        Toast.makeText(DetailedItemActivity.this, "imageDownloaded", Toast.LENGTH_SHORT).show();
                        imageV.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        Toast.makeText(DetailedItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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
