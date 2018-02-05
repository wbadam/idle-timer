package org.vaadin.addon.client;

import com.vaadin.shared.communication.ClientRpc;

public interface TimerClientRpc extends ClientRpc {

    void start(long timestamp);
    void disable();
    void enable();
    void stop();
    void resetTimer(long timestamp);
}