package com.sharkawy.zagazigapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.google.gson.Gson;
import com.linearlistview.LinearListView;
import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.adapters.GallaryAdapter;
import com.sharkawy.zagazigapp.adapters.TagAdapter;
import com.sharkawy.zagazigapp.dataModels.Photo;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.sharkawy.zagazigapp.dataModels.Tag;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DetailedItemActivity extends AppCompatActivity {
    String EXTRA_IMAGE ="extra_image";
    String EXTRA_IMAGES_OBJECTS ="extra_object";
    private static final String EXTRA_IMAGES_FLAG = "extra_flag";

    TextView title  , desc ,address ,tel ,facebookPage;
    ImageView logo ;
    RecyclerView recyclerView ;
    private LinearListView mTrailersView;
    GallaryAdapter gallaryAdapter ;
    TextView txtView_photos ;
    List<Tag> tags ;
    Place place ;
    boolean exits =false ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String FAVORITES = "Product_Favorite";
    boolean isFavorited =false;
    double latitude , longitude ;
    String fbPageURL ;
    ImageButton imageButtonGallery, imageButtonMenus ;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailed_menu, menu);
        JSONObject jsonObject = null;
        //isFavorited
        try{
            jsonObject = new JSONObject(getIntent().getExtras().getString("Item"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        place = new Place(jsonObject);
        final MenuItem action_favorite = menu.findItem(R.id.action_favorite);
//        Toast.makeText(DetailedItemActivity.this,getCart(DetailedItemActivity.this).get(0).getObject().toString(),Toast.LENGTH_SHORT).show();

        if(getCart(DetailedItemActivity.this)!=null){
            for(int i =0 ; i<getCart(DetailedItemActivity.this).size();i++){
                if(getCart(DetailedItemActivity.this).get(i).getObject().toString().equals(place.getObject().toString())){
                    isFavorited = true ;
                    break;
                }
            }
        }else {
            isFavorited = false ;
        }

        action_favorite.setIcon(isFavorited ?
                R.drawable.abc_btn_rating_star_on_mtrl_alpha :
                R.drawable.abc_btn_rating_star_off_mtrl_alpha);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
//        boolean ico = item.getIcon().equals(R.drawable.abc_btn_rating_star_on_mtrl_alpha );
        switch (id){
            case R.id.action_favorite:
                if(isFavorited){
                    RemoveToFav();
                    item.setIcon(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
                }else {
                    AddToFav();
                    item.setIcon(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void AddToFav(){
        ArrayList<Place> tmp ;
        boolean exists =false;
        if (getCart(DetailedItemActivity.this) != null) {
            tmp = getCart(DetailedItemActivity.this);
            for(int i =0 ; i<tmp.size();i++){
                if(tmp.get(i).getObject().toString().equals(place.getObject().toString())){
                    exists = true ;
                        break;
                }
            }
            if(exists){

            }else{
                tmp.add(place);
                Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show();
            }
        } else {
            tmp = new ArrayList<Place>();
            tmp.add(place);
        }
        saveToCart(DetailedItemActivity.this,tmp);
        isFavorited = true ;
    }
    private void RemoveToFav(){
        ArrayList<Place> tmp =new ArrayList<>();
        boolean exists =false ;
        if (getCart(DetailedItemActivity.this) != null) {
            tmp.addAll(getCart(DetailedItemActivity.this));

            for(int i =0 ; i<tmp.size();i++){
                if(tmp.get(i).getObject().toString().equals(place.getObject().toString())){
                    exists = true ;
                    tmp.remove(i);
                    Toast.makeText(this,"Removed from my Favorites",Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        saveToCart(DetailedItemActivity.this, tmp);
        isFavorited = false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detials);

        String [] subcategory = {"مطاعم","كافيهات","سينمات","هدوم ولادى","هدوم بناتى","هدوم اطفال","موبيلات ولابات","جيم شبابي","جيم بناتى","مراكز تجميل","قاعات افراح","ستوديو تصوير","فوتوجرافيك","مستشفيات","عيادات","خدمات عربيات"};

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        txtView_photos = (TextView) findViewById(R.id.txtView_photos);
        title = (TextView) findViewById(R.id.tvtitle);
//        category = (TextView) findViewById(R.id.tvcat);
        desc = (TextView) findViewById(R.id.tvdesc);
        address = (TextView) findViewById(R.id.tvadd);
        tel = (TextView) findViewById(R.id.tvtel);
        logo = (ImageView) findViewById(R.id.photo);
        facebookPage = (TextView) findViewById(R.id.fbPage);
        imageButtonGallery = (ImageButton) findViewById(R.id.btngallary);
        imageButtonMenus = (ImageButton) findViewById(R.id.btnmenu);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerListview_tags_detailed);
        tags = new ArrayList<>();
        TagAdapter tagAdapter = new TagAdapter(this,tags);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tagAdapter);

        mTrailersView = (LinearListView) findViewById(R.id.detailed_trailers);
        gallaryAdapter = new GallaryAdapter(this, new ArrayList<Photo>());
        mTrailersView.setAdapter(gallaryAdapter);
        //imagesPathes

        try {
            JSONObject jsonObject = new JSONObject(getIntent().getExtras().getString("Item"));

            place = new Place(jsonObject);
            title.setText(place.getName());

//            category.setText(subcategory[Integer.parseInt(place.getCategoryID())-1]);
            desc.setText(place.getDesc());
            address.setText(place.getAddress());
            tel.setText(place.getTelephone());
            if(place.getLat()!=null||place.getLng()!=null){
                if(!place.getLat().equals("null")&&!place.getLng().equals("null")){
                    latitude=Double.parseDouble(place.getLat());
                    longitude =Double.parseDouble(place.getLng());
                }
            }

            if(place.getFbPageURL()!=null){
                fbPageURL=place.getFbPageURL();
            }
//            Picasso.with(this).load("http://mashaly.net/" +place.getImageURL()).into(logo);

//            ImageHandler(place.getImageURL(),logo);
            if (!Glide.isSetup()) {
                GlideBuilder gb = new GlideBuilder(this);
                DiskCache dlw = DiskLruCacheWrapper.get(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myCatch/"), 250 * 1024 * 1024);
                gb.setDiskCache(dlw);
                Glide.setup(gb);
            }
            Glide.with(this)
                    .load("http://176.32.230.50/zagapp.com/"+place.getImageURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(logo);

            for (int i = 0; i < place.getObject().getJSONArray("serviceTags").length(); i++) {
                tags.add(new Tag(place.getObject().getJSONArray("serviceTags").getJSONObject(i)));

                Toast.makeText(DetailedItemActivity.this,tags.get(i).getTag().toString(),Toast.LENGTH_SHORT).show();
            }
            tagAdapter.notifyDataSetChanged();
//            Toast.makeText(this,"count of pics "+place.getObject().getJSONArray("imagesPathes").length()+"",Toast.LENGTH_SHORT).show();
            if(place.getObject().getJSONArray("imagesPathes").length()==0||place.getObject().getJSONArray("imagesPathes")==null){
                mTrailersView.setVisibility(View.GONE);
                txtView_photos.setVisibility(View.GONE);
            }else {
                for (int i = 0; i < place.getObject().getJSONArray("imagesPathes").length(); i++) {
                    gallaryAdapter.add(new Photo(place.getObject().getJSONArray("imagesPathes").getJSONObject(i).getString("path"),place.getObject().getJSONArray("imagesPathes").getJSONObject(i).getString("thumb")));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mTrailersView.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView linearListView, View view,
                                    int position, long id) {
                Intent i = new Intent(DetailedItemActivity.this,PhotoActivity.class);
                i.putExtra(EXTRA_IMAGE,position);
                i.putExtra(EXTRA_IMAGES_OBJECTS,place.getObject().toString());
                startActivity(i);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latitude!=0&&longitude!=0){
                    //?q=<lat>,<long>(Label+Name)
                    String uri = String.format(Locale.ENGLISH, "geo:"+latitude+","+longitude+"?q="+latitude+","+longitude+"(Label+Name)");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
            }
        });
        facebookPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newFacebookIntent(getPackageManager(),fbPageURL));
            }
        });
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tel.getText()!=""||tel.getText()!=null){
                    call(tel.getText().toString());
                }
            }
        });
        imageButtonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedItemActivity.this,GalleryActivity.class);
                intent.putExtra(EXTRA_IMAGES_OBJECTS,place.getObject().toString());
                intent.putExtra(EXTRA_IMAGES_FLAG,"GALLERY");
                startActivity(intent);
            }
        });

        imageButtonMenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedItemActivity.this,GalleryActivity.class);
                intent.putExtra(EXTRA_IMAGES_OBJECTS,place.getObject().toString());
                intent.putExtra(EXTRA_IMAGES_FLAG,"MENUS");
                startActivity(intent);
            }
        });
    }
    public ArrayList<Place> getCart(Context context) {
        ArrayList<Place> favorites= new ArrayList<>();
        JSONArray jsonArray ;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(FAVORITES)) {
            String StringFavorites = sharedpreferences.getString(FAVORITES, null);
            try {
                jsonArray = new JSONArray(StringFavorites);
                favorites = new ArrayList<Place>();
                for(int i = 0 ;i<jsonArray.length();i++){
                    favorites.add(new Place(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else
            return null;
        return favorites;
    }
    public void saveToCart(Context context, List<Place> favorites) {

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        JSONArray jsonArray = new JSONArray();
        for(int i = 0 ;i<favorites.size();i++){
            jsonArray.put(favorites.get(i).getObject());
        }
        editor.clear();
        editor.putString(FAVORITES, jsonArray.toString());
        editor.commit();
//        Toast.makeText(DetailedItemActivity.this,"Saved to your Favorites",Toast.LENGTH_SHORT).show();
    }
    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private void call(String num) {
        Intent in=new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",num,null));
        try{
            startActivity(in);
        }

        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }
    }
}
