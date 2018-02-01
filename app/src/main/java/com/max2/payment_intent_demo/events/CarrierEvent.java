package com.max2.payment_intent_demo.events;

import com.max2.veeaconnect.sdk.domain.entities.TransactionStatusDetails;

public class CarrierEvent<T> {
    private final TransactionStatusDetails.Status status;
    private final T data;

    public CarrierEvent(TransactionStatusDetails.Status status, T data) {
        this.status = status;
        this.data = data;
    }

    public TransactionStatusDetails.Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
