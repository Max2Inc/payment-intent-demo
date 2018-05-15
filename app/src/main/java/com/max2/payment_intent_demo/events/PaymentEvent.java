package com.max2.payment_intent_demo.events;

import com.max2.veeaconnect.sdk.domain.entities.TransactionStatusDetails;
import com.max2.veeaconnect.sdk.domain.entities.receipt.Receipt;

public class PaymentEvent {
    private final TransactionStatusDetails.Status status;
    private final Receipt receipt;

    public PaymentEvent(TransactionStatusDetails.Status status, Receipt receipt) {
        this.status = status;
        this.receipt = receipt;
    }

    public TransactionStatusDetails.Status getStatus() {
        return status;
    }

    public Receipt getData() {
        return receipt;
    }
}
