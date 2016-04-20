package com.sharkawy.zagazigapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharkawy.zagazigapp.R;
import com.sharkawy.zagazigapp.dataModels.Place;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailedItemActivity extends AppCompatActivity {

    TextView title ,category , desc ,address ,tel ;
    ImageView logo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detials);

        String [] TAGS = {"مطاعم","كافيهات","سينمات","هدوم ولادى","هدوم بناتى","هدوم اطفال","موبيلات ولابات","جيم شبابي","جيم بناتى","مراكز تجميل","قاعات افراح","ستوديو تصوير","فوتوجرافيك","مستشفيات","عيادات","خدمات عربيات"};

        title = (TextView) findViewById(R.id.tvtitle);
        category = (TextView) findViewById(R.id.tvcat);
        desc = (TextView) findViewById(R.id.tvdesc);
        address = (TextView) findViewById(R.id.tvadd);
        tel = (TextView) findViewById(R.id.tvtel);
        logo = (ImageView) findViewById(R.id.photo);

        try {
            JSONObject jsonObject = new JSONObject(getIntent().getExtras().getString("Item"));

            Place place = new Place(jsonObject);
            title.setText(place.getName());

            category.setText(TAGS[Integer.parseInt(place.getCategoryID())-1]);
            desc.setText(place.getDesc());
            address.setText(place.getAddress());
            tel.setText(place.getTelephone());

            Picasso.with(this).load("http://mashaly.net/" +place.getImageURL()).into(logo);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
