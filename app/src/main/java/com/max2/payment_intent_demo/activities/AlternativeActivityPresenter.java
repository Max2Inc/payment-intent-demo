package com.max2.payment_intent_demo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.max2.payment_intent_demo.App;
import com.max2.payment_intent_demo.R;
import com.max2.payment_intent_demo.events.AuthenticationEvent;
import com.max2.payment_intent_demo.events.ChangePasscodeEvent;
import com.max2.payment_intent_demo.events.PaymentCancelledEvent;
import com.max2.payment_intent_demo.events.PaymentEvent;
import com.max2.payment_intent_demo.events.SelectTransactionEvent;
import com.max2.veeaconnect.sdk.domain.entities.TransactionLog;
import com.max2.veeaconnect.sdk.domain.entities.TransactionStatusDetails;
import com.max2.veeaconnect.sdk.ui.common.dialogs.SimpleMaterialDialog;
import com.max2.veeaconnect.sdk.ui.common.interfaces.sdk.AuthenticationRequestSupport;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import nucleus.presenter.RxPresenter;
import rx.functions.Action1;

public class AlternativeActivityPresenter extends RxPresenter<AlternativeActivity> {
    private TransactionLog transactionLogToBeRefunded;
    private HashMap<Long, TransactionLog> linkedTransactionLogs;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        App.getInstance().getEventBus().register(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        App.getInstance().getEventBus().unregister(this);
    }


    @SuppressWarnings("unused")
    @Subscribe
    public void onPaymentResultReceived(PaymentEvent event) {
        deliver(view -> {
            if (event.getStatus() == TransactionStatusDetails.Status.AUTHORIZED) {
                SimpleMaterialDialog.show(view,
                        R.string.payment_result_received_title,
                        R.string.payment_result_received_success);
            } else {
                SimpleMaterialDialog.show(view,
                        R.string.payment_result_received_title,
                        R.string.payment_result_received_failure);
            }
        });
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onPaymentCancelledReceived(PaymentCancelledEvent event) {
        deliver(view -> SimpleMaterialDialog.show(view,
                R.string.payment_result_received_title,
                R.string.payment_result_received_cancelled)
        );
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onAuthenticationStatusReceived(AuthenticationEvent event) {
        if (event.isOperationSuccessful()) {
            deliver(view -> SimpleMaterialDialog.show(view,
                    R.string.payment_result_received_title,
                    R.string.authentication_result_received_success)
            );
        } else if (event.getExtendedStatus() == AuthenticationRequestSupport.Status.CANCELLED) {
            deliver(view -> SimpleMaterialDialog.show(view,
                    R.string.payment_result_received_title,
                    R.string.authentication_result_received_cancel)
            );
        } else {
            deliver(view -> SimpleMaterialDialog.show(view,
                    R.string.payment_result_received_title,
                    R.string.authentication_result_received_fail)
            );
        }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onChangePasscodeReceived(ChangePasscodeEvent event) {
        if (event.isOperationSuccessful()) {
            deliver(view -> SimpleMaterialDialog.show(view,
                    R.string.payment_passcode_change_title,
                    R.string.payment_passcode_change_successful)
            );
        }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onTransactionSelected(SelectTransactionEvent event) {
        this.transactionLogToBeRefunded = event.getTransactionLog();
        this.linkedTransactionLogs = event.getLinkedTransactionLogs();

        deliver(view -> SimpleMaterialDialog.show(view,
                view.getString(R.string.payment_transaction_select_title),
                String.format(view.getString(R.string.payment_transaction_select_message),
                        transactionLogToBeRefunded.getId()))
        );
    }

    public TransactionLog getTransactionLogToBeRefunded() {
        return transactionLogToBeRefunded;
    }

    public HashMap<Long, TransactionLog> getLinkedTransactionLogs() {
        return linkedTransactionLogs;
    }

    private void deliver(Action1<AlternativeActivity> action1) {
        view()
                .filter(view -> view != null)
                .compose(deliverFirst())
                .subscribe(split((view, result) -> action1.call(view)));
    }
}
