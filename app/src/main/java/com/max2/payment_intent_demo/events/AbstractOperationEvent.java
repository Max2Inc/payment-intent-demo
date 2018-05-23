package com.max2.payment_intent_demo.events;

public abstract class AbstractOperationEvent {
    private final boolean operationSuccessful;

    protected AbstractOperationEvent(boolean operationSuccessful) {
        this.operationSuccessful = operationSuccessful;
    }

    public boolean isOperationSuccessful() {
        return operationSuccessful;
    }
}
