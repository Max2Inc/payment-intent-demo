package com.max2.payment_intent_demo.events;

public class ChangePasscodeEvent {
    private final boolean operationSucceeded;

    public ChangePasscodeEvent(boolean operationSucceeded) {
        this.operationSucceeded = operationSucceeded;
    }

    public boolean hasOperationSucceeded() {
        return operationSucceeded;
    }
}
