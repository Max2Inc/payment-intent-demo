package com.max2.payment_intent_demo;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.max2.payment_intent_demo.events.CarrierEvent;
import com.max2.veeaconnect.sdk.domain.entities.receipt.Receipt;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {
    private static final String PACKAGE_NAME = "com.connect.max2.veeaconnect.dev";
    private static final String ACTIVITY_NAME = "com.connect.max2.walla.ui.SplashScreenActivity";
    private static final String ACTION_PAYMENTS = "com.connect.max2.walla.PAYMENTS";

    private static final String KEY_TICKET = "vc.key.ticket";
    private static final String KEY_TRANSACTION_LOG_ID = "vc.key.transactionlog.id";
    private static final String KEY_BROADCAST_ACTION_NAME = "vc.originating.action.name";

    private static final String VAL_BROADCAST_ACTION_NAME = "com.test.action.hello.World";
    private static final String VAL_TICKET = "{\n" +
            "    \"createdAt\": 1517348247,\n" +
            "    \"items\": [\n" +
            "        {\n" +
            "            \"cost\": 1.23,\n" +
            "            \"name\": \"Default Product\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"taxPercentage\": 0,\n" +
            "            \"totalAfterTax\": 1.23,\n" +
            "            \"totalBeforeTax\": 1.23,\n" +
            "            \"totalTax\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"cost\": 4.56,\n" +
            "            \"name\": \"Default Product\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"taxPercentage\": 0,\n" +
            "            \"totalAfterTax\": 4.56,\n" +
            "            \"totalBeforeTax\": 4.56,\n" +
            "            \"totalTax\": 0\n" +
            "        }\n" +
            "    ],\n" +
            "    \"name\": \"New Ticket\",\n" +
            "    \"tip\": 0,\n" +
            "    \"totalAfterTax\": 5.79,\n" +
            "    \"totalBeforeTax\": 5.79,\n" +
            "    \"totalTax\": 0\n" +
            "}\n";

    private Receipt receivedReceipt;

    @BindView(R.id.btnReversal)
    Button btnReversal;

    @BindView(R.id.btnRefund)
    Button btnRefund;

    @BindView(R.id.etReversalTransactionId)
    EditText etReversalTransaactionId;

    @BindView(R.id.etRefundTransactionId)
    EditText etRefundTransactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        App.getInstance().getEventBus().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().getEventBus().unregister(this);
    }

    @OnClick(R.id.btnPayments)
    protected void onPaymentsClick() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PACKAGE_NAME, ACTIVITY_NAME))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .setAction(ACTION_PAYMENTS)
                .putExtra(KEY_TICKET, VAL_TICKET)
                .putExtra(KEY_BROADCAST_ACTION_NAME, VAL_BROADCAST_ACTION_NAME);

        startActivity(intent);
    }

    @OnClick(R.id.btnTransactionLogs)
    protected void onTransactionLogsClick() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PACKAGE_NAME, ACTIVITY_NAME))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .setAction(ACTION_PAYMENTS)
                .putExtra(KEY_BROADCAST_ACTION_NAME, VAL_BROADCAST_ACTION_NAME);

        startActivity(intent);
    }

    @OnClick(R.id.btnReversal)
    protected void onReversalClick() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PACKAGE_NAME, ACTIVITY_NAME))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .setAction(ACTION_PAYMENTS)
                .putExtra(KEY_BROADCAST_ACTION_NAME, VAL_BROADCAST_ACTION_NAME)
                .putExtra(KEY_TRANSACTION_LOG_ID, Long.parseLong(etReversalTransaactionId.getText().toString()));

        startActivity(intent);
    }

    @OnClick(R.id.btnRefund)
    protected void onRefundClick() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PACKAGE_NAME, ACTIVITY_NAME))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .setAction(ACTION_PAYMENTS)
                .putExtra(KEY_BROADCAST_ACTION_NAME, VAL_BROADCAST_ACTION_NAME)
                .putExtra(KEY_TRANSACTION_LOG_ID, Long.parseLong(etRefundTransactionId.getText().toString()));

        startActivity(intent);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onReceiptReceived(CarrierEvent<Receipt> carrierEvent) {
        receivedReceipt = carrierEvent.getData();

        Toast.makeText(this, "Received transaction status " + carrierEvent.getStatus().toString(),
                Toast.LENGTH_LONG).show();

        if (receivedReceipt != null) {
            etReversalTransaactionId.setText(String.valueOf(receivedReceipt.getTransactionLogId()));
            etRefundTransactionId.setText(String.valueOf(receivedReceipt.getTransactionLogId()));
        }
    }

    @OnTextChanged(R.id.etReversalTransactionId)
    protected void onReversalTransactionIdEntryChanged(CharSequence charSequence) {
        btnReversal.setEnabled(charSequence != null && charSequence.toString().length() > 0);
    }

    @OnTextChanged(R.id.etRefundTransactionId)
    protected void onRefundTransactionIdEntryChanged(CharSequence charSequence) {
        btnRefund.setEnabled(charSequence != null && charSequence.toString().length() > 0);
    }
}
