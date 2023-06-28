package com.example.aufait;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aufait.model.ShopModel;

public class OrderSucceessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_succeess);


        ShopModel shopModel = getIntent().getParcelableExtra("ShopModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(shopModel.getName());
        actionBar.setSubtitle(shopModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(false);


        TextView buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(v -> finish());
    }
}