package org.vaadin.addon.client;

import org.vaadin.addon.Timer;

import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@Connect(Timer.class)
public class TimerConnector extends AbstractComponentConnector {

    TimerServerRpc rpc = RpcProxy.create(TimerServerRpc.class, this);

    public TimerConnector() {

        registerRpc(TimerClientRpc.class, new TimerClientRpc() {
            @Override
            public void resetTimer(int seconds) {
                getWidget().setRemainingSeconds(seconds);
            }
        });
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
    }

    @Override
    public TimerWidget getWidget() {
        return (TimerWidget) super.getWidget();
    }

    @Override
    public TimerState getState() {
        return (TimerState) super.getState();
    }
}
