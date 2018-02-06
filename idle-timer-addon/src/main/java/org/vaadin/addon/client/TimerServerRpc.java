package org.vaadin.addon.client;

import com.vaadin.shared.communication.ServerRpc;

public interface TimerServerRpc extends ServerRpc {

    void timeout();

    void notifyPass(int remainingSeconds);
}
