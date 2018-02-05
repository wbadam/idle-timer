package org.vaadin.addon.client;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.vaadin.addon.Timer;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.storage.client.StorageEvent;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@Connect(Timer.class)
public class TimerConnector extends AbstractComponentConnector {

    private static final String STORAGE_KEY = "org.vaadin.addon.idletimer.timestamp";

    private TimerServerRpc rpc = RpcProxy.create(TimerServerRpc.class, this);

    private JavaScriptObject storageCallback;

    private long timeLimit;

    public TimerConnector() {

        registerRpc(TimerClientRpc.class, new TimerClientRpc() {
            @Override
            public void resetTimer(long timestamp) {
                timeLimit = timestamp;
                storeTimestamp(timestamp);
                updateTimer(timestamp);
            }
        });

        storageCallback = addStorageListener();
    }

    private void updateTimer(long timestamp) {
        getWidget().setRemainingSeconds((int) TimeUnit.MILLISECONDS
                .toSeconds(timestamp - new Date().getTime()));
    }

    private void handleStorageEvent(StorageEvent event) {
        if (STORAGE_KEY.equals(event.getKey())) {
            timeLimit = Long.valueOf(event.getNewValue());
            updateTimer(timeLimit);
        }
    }

    private native void storeTimestamp(Long timestamp)/*-{
        $wnd.localStorage.setItem(@org.vaadin.addon.client.TimerConnector::STORAGE_KEY, timestamp);
    }-*/;

    private native JavaScriptObject addStorageListener()/*-{
        var self = this;
        var callbackFunc = $entry(function(e) {
            self.@org.vaadin.addon.client.TimerConnector::handleStorageEvent(*)(e);
        });
        $wnd.addEventListener("storage", callbackFunc, false);
        return callbackFunc;
    }-*/;

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        if (stateChangeEvent.hasPropertyChanged("timeDifference")) {
            getWidget().setTimeDifference(getState().timeDifference);
        }
    }

    @Override
    public void onUnregister() {
        super.onUnregister();

        removeStorageListener(storageCallback);
    }

    private native void removeStorageListener(JavaScriptObject callback)/*-{
        $wnd.removeEventListener("storage", callback, false);
    }-*/;

    @Override
    public TimerWidget getWidget() {
        return (TimerWidget) super.getWidget();
    }

    @Override
    public TimerState getState() {
        return (TimerState) super.getState();
    }
}
