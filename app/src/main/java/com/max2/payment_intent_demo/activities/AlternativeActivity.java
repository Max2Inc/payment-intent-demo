package com.max2.payment_intent_demo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.max2.payment_intent_demo.App;
import com.max2.payment_intent_demo.R;
import com.max2.payment_intent_demo.events.AuthenticationEvent;
import com.max2.payment_intent_demo.events.PaymentCancelledEvent;
import com.max2.payment_intent_demo.events.PaymentEvent;
import com.max2.veeaconnect.sdk.Max2Sdk;
import com.max2.veeaconnect.sdk.domain.entities.TransactionStatusDetails;
import com.max2.veeaconnect.sdk.domain.entities.payments.Ticket;
import com.max2.veeaconnect.sdk.domain.entities.payments.TicketItem;
import com.max2.veeaconnect.sdk.ui.common.dialogs.SimpleMaterialDialog;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlternativeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative);

        ButterKnife.bind(this);
        App.getInstance().getEventBus().register(this);
    }

    @OnClick(R.id.bntLogin)
    protected void onButtonLoginClick() {
        Max2Sdk.auth().startAuthentication().subscribe();
    }

    @OnClick(R.id.btnPayments)
    protected void onButtonPaymentsClick() {
        Max2Sdk.payments().startTicketPayment(
                new Ticket.TicketBuilder("ticket name")
                        .addTicketItem(new TicketItem.Builder("ticket item name")
                                .setCost(1.23)
                                .setTaxPercentage(8.875)
                                .setQuantity(1.0)
                                .createTicketItem())
                        .createTicket()
        ).subscribe();
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onPaymentResultReceived(PaymentEvent event) {
        if (event.getStatus() == TransactionStatusDetails.Status.AUTHORIZED) {
            SimpleMaterialDialog.show(this,
                    R.string.payment_result_received_title,
                    R.string.payment_result_received_success);
        } else {
            SimpleMaterialDialog.show(this,
                    R.string.payment_result_received_title,
                    R.string.payment_result_received_failure);
        }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onPaymentCancelledReceived(PaymentCancelledEvent event) {
        SimpleMaterialDialog.show(this,
                R.string.payment_result_received_title,
                R.string.payment_result_received_cancelled);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onAuthenticationStatusReceived(AuthenticationEvent event) {
        if (event.isAuthenticated()) {
            SimpleMaterialDialog.show(this,
                    R.string.payment_result_received_title,
                    R.string.authentication_result_received_success);
        }
    }
}
