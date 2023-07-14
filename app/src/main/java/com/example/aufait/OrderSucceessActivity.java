package com.example.aufait;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderSucceessActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_succeess);

        TextView buttonDone = findViewById(R.id.buttonDone);

        buttonDone.setOnClickListener(v -> {

                // Finish the activity and navigate back to the main activity
                Intent intent = new Intent(OrderSucceessActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

        });
    }
}
