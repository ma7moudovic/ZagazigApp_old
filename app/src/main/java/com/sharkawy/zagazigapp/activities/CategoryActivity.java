package com.sharkawy.zagazigapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sharkawy.zagazigapp.R;

public class CategoryActivity extends AppCompatActivity {

    Spinner sp_areas ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String [] arr ={"القومية ","شارع المحافظة","مفارق المنصورة","فلل الجامعة","حي الزهور","المنتزة","شارع البحر","المحطة","شارع مديرالامن","عمر افندي","عمارة الاوقاف","حي ثاني","شارع الغشام"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, arr);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        sp_areas = (Spinner) findViewById(R.id.sp_aresa);
        sp_areas.setAdapter(dataAdapter);

    }
}
