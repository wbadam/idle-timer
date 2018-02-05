package org.vaadin.addon.client;

import com.vaadin.shared.communication.ClientRpc;

public interface TimerClientRpc extends ClientRpc {

    void resetTimer(long timestamp);
}