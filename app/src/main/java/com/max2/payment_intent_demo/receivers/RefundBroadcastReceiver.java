package com.max2.payment_intent_demo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.max2.payment_intent_demo.App;
import com.max2.payment_intent_demo.events.PaymentCancelledEvent;
import com.max2.payment_intent_demo.events.PaymentEvent;
import com.max2.veeaconnect.sdk.domain.Log;
import com.max2.veeaconnect.sdk.domain.entities.TransactionStatusDetails;
import com.max2.veeaconnect.sdk.domain.entities.receipt.Receipt;
import com.max2.veeaconnect.sdk.ui.common.interfaces.PaymentRequestSupport;

public class RefundBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "RefundBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getExtras() != null) {
                Log.d(TAG, "Received intent with status " +
                        TransactionStatusDetails.Status.fromInt(intent.getIntExtra(PaymentRequestSupport.KEY_TRANSACTION_STATUS,
                                TransactionStatusDetails.Status.AUTHORIZE_FAILED.intVal())) +
                        " and receipt " + intent.getStringExtra(PaymentRequestSupport.KEY_TRANSACTION_RECEIPT)
                );

                App.getInstance().getEventBus().post(new PaymentEvent(
                        TransactionStatusDetails.Status.fromInt(intent.getIntExtra(PaymentRequestSupport.KEY_TRANSACTION_STATUS,
                                TransactionStatusDetails.Status.AUTHORIZE_FAILED.intVal())),
                        intent.getExtras().containsKey(PaymentRequestSupport.KEY_TRANSACTION_RECEIPT) ? App.getInstance().getGson().fromJson(
                                intent.getStringExtra(PaymentRequestSupport.KEY_TRANSACTION_RECEIPT), Receipt.class) : null
                ));
            } else {
                Log.d(TAG, "Received empty intent");

                App.getInstance().getEventBus().post(new PaymentCancelledEvent());
            }
        }
    }
}
