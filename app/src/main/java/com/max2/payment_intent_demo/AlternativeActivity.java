package com.max2.payment_intent_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.max2.veeaconnect.sdk.ui.authentication.AuthActivity;
import com.max2.veeaconnect.sdk.ui.payments.PaymentsActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlternativeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bntLogin)
    protected void onButtonLoginClick() {
        startActivity(new Intent(this, AuthActivity.class));
    }

    @OnClick(R.id.btnPayments)
    protected void onButtonPaymentsClick() {
        startActivity(new Intent(this, PaymentsActivity.class));
    }
}
