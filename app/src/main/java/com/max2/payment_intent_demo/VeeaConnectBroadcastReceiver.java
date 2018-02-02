package com.max2.payment_intent_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.max2.payment_intent_demo.events.CarrierEvent;
import com.max2.veeaconnect.sdk.domain.entities.TransactionStatusDetails;
import com.max2.veeaconnect.sdk.domain.entities.receipt.Receipt;

public class VeeaConnectBroadcastReceiver extends BroadcastReceiver {
    private static final String KEY_STATUS = "vc.key.status"; // to be eventually available via sdk
    private static final String KEY_RECEIPT = "vc.key.receipt"; // to be eventually available via sdk

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            App.getInstance().getEventBus().post(new CarrierEvent<>(
                    TransactionStatusDetails.Status.fromInt(intent.getIntExtra(KEY_STATUS,
                            TransactionStatusDetails.Status.AUTHORIZE_FAILED.intVal())),
                    intent.getExtras().containsKey(KEY_RECEIPT) ? App.getInstance().getGson().fromJson(
                            intent.getStringExtra(KEY_RECEIPT), Receipt.class) : null
            ));
        }
    }
}
