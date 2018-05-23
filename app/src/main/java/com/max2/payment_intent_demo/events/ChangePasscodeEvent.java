package com.max2.payment_intent_demo.events;

public class ChangePasscodeEvent extends AbstractOperationEvent {
    public ChangePasscodeEvent(boolean operationSucceeded) {
        super(operationSucceeded);
    }
}
