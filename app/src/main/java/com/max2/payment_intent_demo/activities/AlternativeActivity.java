package com.max2.payment_intent_demo.activities;

import android.os.Bundle;

import com.max2.payment_intent_demo.R;
import com.max2.veeaconnect.sdk.Max2Sdk;
import com.max2.veeaconnect.sdk.domain.entities.payments.Ticket;
import com.max2.veeaconnect.sdk.domain.entities.payments.TicketItem;
import com.max2.veeaconnect.sdk.ui.common.dialogs.SimpleMaterialDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(AlternativeActivityPresenter.class)
public class AlternativeActivity extends NucleusAppCompatActivity<AlternativeActivityPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative);

        ButterKnife.bind(this);
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
                    .subscribe();
        }
    }
}
