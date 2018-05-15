package com.max2.payment_intent_demo.events;

public class AuthenticationEvent {
    private final boolean authenticated;

    public AuthenticationEvent(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
