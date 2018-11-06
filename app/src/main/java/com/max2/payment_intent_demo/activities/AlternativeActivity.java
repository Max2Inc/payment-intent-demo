package com.max2.payment_intent_demo.activities;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.max2.payment_intent_demo.R;
import com.max2.payment_intent_demo.receivers.AuthenticationBroadcastReceiver;
import com.max2.veeaconnect.sdk.Max2Sdk;
import com.max2.veeaconnect.sdk.domain.entities.payments.Ticket;
import com.max2.veeaconnect.sdk.domain.entities.payments.TicketItem;
import com.max2.veeaconnect.sdk.ui.common.dialogs.SimpleMaterialDialog;
import com.max2.veeaconnect.sdk.ui.common.interfaces.AuthenticationRequestSupport;

import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(AlternativeActivityPresenter.class)
public class AlternativeActivity extends NucleusAppCompatActivity<AlternativeActivityPresenter> {
    private final BroadcastReceiver authenticationBroadcastReceiver = new AuthenticationBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative);

        ButterKnife.bind(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(authenticationBroadcastReceiver,
                new IntentFilter(AuthenticationRequestSupport.ACTION));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(authenticationBroadcastReceiver);
        super.onDestroy();
    }

    @OnClick(R.id.bntLogin)
    protected void onButtonLoginClick() {
        Max2Sdk.auth().startAuthentication().subscribe();
    }

    @OnClick(R.id.btnPaymentsDirect)
    protected void onButtonPaymentsDirectClick() {
        Max2Sdk.payments().startTicketPaymentDirect(
                new Ticket.TicketBuilder("ticket name")
                        .addTicketItem(new TicketItem.Builder("ticket item name")
                                .setCost(0.01)
                                .setTaxPercentage(8.875)
                                .setQuantity(1.0)
                                .createTicketItem())
                        .createTicket()
        ).subscribe();
    }

    @OnClick(R.id.btnChangePasscode)
    protected void onButtonChangePasscodeClick() {
        Max2Sdk.payments().startChangePasscode().subscribe();
    }

    @OnClick(R.id.btnSelectTransaction)
    protected void onButtonSelectTransctionClick() {
        Max2Sdk.payments().selectTransactionLog().subscribe();
    }

    @OnClick(R.id.btnRefundTransaction)
    protected void onButtonRefundTransactionClick() {
        if (getPresenter().getTransactionLogToBeRefunded() == null) {
            SimpleMaterialDialog.show(this,
                    R.string.payment_result_info,
                    R.string.payment_select_transaction);
        } else {
            Max2Sdk.payments().startTransactionRefund(
                    getPresenter().getTransactionLogToBeRefunded(), getPresenter().getLinkedTransactionLogs())
                    .subscribe(() -> {
                                //does nothing
                            }, throwable ->
                                    SimpleMaterialDialog.show(this,
                                            getString(R.string.payment_result_error),
                                            throwable.getMessage())
                    );
        }
    }
}
