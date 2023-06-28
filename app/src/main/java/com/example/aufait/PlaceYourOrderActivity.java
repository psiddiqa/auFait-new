package com.example.aufait;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aufait.adapters.PlaceYourOrderAdapter;
import com.example.aufait.model.Menu;
import com.example.aufait.model.ShopModel;

public class PlaceYourOrderActivity extends AppCompatActivity {

    private EditText inputName, inputAddress, inputCity, inputState, inputZip,inputCardNumber, inputCardExpiry, inputCardPin ;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;
    private SwitchCompat switchDelivery;
    private boolean isDeliveryOn;
    private PlaceYourOrderAdapter placeYourOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        ShopModel shopModel = getIntent().getParcelableExtra("ShopModel");

        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardExpiry = findViewById(R.id.inputCardExpiry);
        inputCardPin = findViewById(R.id.inputCardPin);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        switchDelivery = findViewById(R.id.switchDelivery);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(shopModel);
            }
        });

        switchDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                inputAddress.setVisibility(View.VISIBLE);
                inputCity.setVisibility(View.VISIBLE);
                inputState.setVisibility(View.VISIBLE);
                inputZip.setVisibility(View.VISIBLE);
                tvDeliveryChargeAmount.setVisibility(View.VISIBLE);
                tvDeliveryCharge.setVisibility(View.VISIBLE);
                isDeliveryOn = true;
                calculateTotalAmount(shopModel);
            } else {
                inputAddress.setVisibility(View.GONE);
                inputCity.setVisibility(View.GONE);
                inputState.setVisibility(View.GONE);
                inputZip.setVisibility(View.GONE);
                tvDeliveryChargeAmount.setVisibility(View.GONE);
                tvDeliveryCharge.setVisibility(View.GONE);
                isDeliveryOn = false;
                calculateTotalAmount(shopModel);
            }
        });
        initRecyclerView(shopModel);
        calculateTotalAmount(shopModel);
    }

    private void calculateTotalAmount(ShopModel shopModel) {
        float subTotalAmount = 0f;

        for(Menu m : shopModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        tvSubtotalAmount.setText("₹"+String.format("%.2f", subTotalAmount));
        if(isDeliveryOn) {
            tvDeliveryChargeAmount.setText("₹"+String.format("%.2f", shopModel.getDelivery_charge()));
            subTotalAmount += shopModel.getDelivery_charge();
        }
        tvTotalAmount.setText("₹"+String.format("%.2f", subTotalAmount));
    }

    private void onPlaceOrderButtonClick(ShopModel shopModel) {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputCity.getText().toString())) {
            inputCity.setError("Please enter city ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputState.getText().toString())) {
            inputState.setError("Please enter zip ");
            return;
        }else if( TextUtils.isEmpty(inputCardNumber.getText().toString())) {
            inputCardNumber.setError("Please enter card number ");
            return;
        }else if( TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
            inputCardExpiry.setError("Please enter card expiry ");
            return;
        }else if( TextUtils.isEmpty(inputCardPin.getText().toString())) {
            inputCardPin.setError("Please enter card pin/cvv ");
            return;
        }
        //start success activity..
        Intent i = new Intent(PlaceYourOrderActivity.this, OrderSucceessActivity.class);
        i.putExtra("ShopModel", shopModel);
        startActivityForResult(i, 1000);
    }

    private void initRecyclerView(ShopModel shopModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(shopModel.getMenus());
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}