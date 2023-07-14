package com.example.aufait;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aufait.model.Menu;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView thumbImage;
    private TextView menuName;
    private TextView menuPrice;
    private Button addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        thumbImage = findViewById(R.id.thumbImage);
        menuName = findViewById(R.id.menuName);
        menuPrice = findViewById(R.id.menuPrice);
        addToCartButton = findViewById(R.id.addToCartButton);

        Intent intent = getIntent();
        if (intent != null) {
            Menu menu = intent.getParcelableExtra("Menu");
            if (menu != null) {
                // Set the item details in the views
                menuName.setText(menu.getName());
                menuPrice.setText("Price: ₹" + menu.getPrice());

                // Load the item image using Glide or any other image loading library
                Glide.with(this)
                        .load(menu.getUrl())
                        .into(thumbImage);

                // Set an OnClickListener for the add to cart button
                addToCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCart(menu);
                    }
                });
            }
        }
    }

    private void addToCart(Menu menu) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("AddedMenu", menu);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
