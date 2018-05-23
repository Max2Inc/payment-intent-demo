package com.max2.payment_intent_demo.events;

public class AuthenticationEvent extends AbstractOperationEvent {
    public AuthenticationEvent(boolean authenticated) {
        super(authenticated);
    }
}
