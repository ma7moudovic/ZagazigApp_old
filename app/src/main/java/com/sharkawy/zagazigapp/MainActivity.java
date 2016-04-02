package com.sharkawy.zagazigapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner s1;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        s1=(Spinner)findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Karim");
        list.add("Ahmed");
        list.add("Mahmoud");
        list.add("Dabour");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);


        s1.setAdapter(dataAdapter);
        btn1=(Button)findViewById(R.id.btn1);
        Animation animation = new TranslateAnimation(0, 500,0, 0);
        animation.setDuration(1000);
        btn1.startAnimation(animation);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "DAbour", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
