package com.max2.payment_intent_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class VeeaConnectBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "VCBroadcastReceiver";
    private static final String KEY_STATUS = "vc.key.status";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getExtras() != null && intent.getExtras().containsKey(KEY_STATUS)) {
            Log.d(TAG, "received intent with status " + intent.getIntExtra(KEY_STATUS, -1));

            Toast.makeText(context, "Received VC status " + intent.getIntExtra(KEY_STATUS, -1),
                    Toast.LENGTH_LONG).show();
        }
    }
}
