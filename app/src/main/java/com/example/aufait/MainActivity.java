package com.example.aufait;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aufait.adapters.ShopListAdapter;
import com.example.aufait.model.ShopModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ShopListAdapter.ShopListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<ShopModel> shopModelList =  getShopData();

        initRecyclerView(shopModelList);
    }

    private void initRecyclerView(List<ShopModel> shopModelList ) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShopListAdapter adapter = new ShopListAdapter(shopModelList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<ShopModel> getShopData() {
        InputStream is = getResources().openRawResource(R.raw.shop);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        ShopModel[] shopModels =  gson.fromJson(jsonStr, ShopModel[].class);
        List<ShopModel> restList = Arrays.asList(shopModels);

        return  restList;

    }

    @Override
    public void onItemClick(ShopModel shopModel) {
        Intent intent = new Intent(MainActivity.this, ShopMenuActivity.class);
        intent.putExtra("ShopModel", shopModel);
        startActivity(intent);

    }
}