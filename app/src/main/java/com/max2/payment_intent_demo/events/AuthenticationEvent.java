package com.max2.payment_intent_demo.events;

import com.max2.veeaconnect.sdk.ui.common.interfaces.sdk.AuthenticationRequestSupport;

public class AuthenticationEvent extends AbstractOperationEvent {
    private final AuthenticationRequestSupport.Status extendedStatus;

    public AuthenticationEvent(AuthenticationRequestSupport.Status extendedStatus) {
        super(extendedStatus == AuthenticationRequestSupport.Status.SUCCEEDED);
        this.extendedStatus = extendedStatus;
    }

    public AuthenticationRequestSupport.Status getExtendedStatus() {
        return extendedStatus;
    }
}
