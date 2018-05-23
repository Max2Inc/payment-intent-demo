package com.max2.payment_intent_demo.events;

import com.max2.veeaconnect.sdk.domain.entities.TransactionLog;

import java.util.HashMap;

public class SelectTransactionEvent extends AbstractOperationEvent {
    private final TransactionLog transactionLog;
    private final HashMap<Long, TransactionLog> linkedTransactionLogs;

    public SelectTransactionEvent(boolean operationSuccessful,
                                  TransactionLog transactionLog, HashMap<Long, TransactionLog> linkedTransactionLogs) {
        super(operationSuccessful);
        this.transactionLog = transactionLog;
        this.linkedTransactionLogs = linkedTransactionLogs;
    }

    public TransactionLog getTransactionLog() {
        return transactionLog;
    }

    public HashMap<Long, TransactionLog> getLinkedTransactionLogs() {
        return linkedTransactionLogs;
    }
}
