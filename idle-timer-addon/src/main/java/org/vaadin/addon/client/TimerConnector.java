package org.vaadin.addon.client;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.vaadin.addon.Timer;

import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@Connect(Timer.class)
public class TimerConnector extends AbstractComponentConnector {

    private class IdleTimer extends com.google.gwt.user.client.Timer {

        private static final int PERIOD = 1000;

        private int remainingSeconds;

        @Override
        public void run() {
            final int min = (int) TimeUnit.SECONDS.toMinutes(remainingSeconds);
            final int sec = (int) TimeUnit.SECONDS.toSeconds(
                    remainingSeconds - TimeUnit.MINUTES.toSeconds(min));

            getWidget().setText(format(min, sec));

            // Notifications
            if (getState().notifySeconds.contains(remainingSeconds)) {
                rpc.notifyPass(remainingSeconds);
            }

            if (--remainingSeconds < 0) {
                rpc.timeout();
                cancel();
            }
        }

        public void startTimer() {
            scheduleRepeating(PERIOD);
        }
    }

//    private static final String STORAGE_KEY_TIMESTAMP = "org.vaadin.addon.idletimer.timestamp";

    private TimerServerRpc rpc = RpcProxy.create(TimerServerRpc.class, this);

//    private JavaScriptObject storageCallback;

    private final IdleTimer timer = new IdleTimer();

    private long timeLimit;

    public TimerConnector() {

        registerRpc(TimerClientRpc.class, new TimerClientRpc() {
            @Override
            public void start(long timestamp) {
                timeLimit = timestamp;
                updateTimer(timestamp);
                resumeTimer();
            }

            @Override
            public void disable() {
                pauseTimer();
            }

            @Override
            public void enable() {
                resumeTimer();
            }

            @Override
            public void stop() {
                pauseTimer();
                getWidget().clearText();
            }

            @Override
            public void resetTimer(long timestamp) {
                timeLimit = timestamp;
                updateTimer(timestamp);

//                updateLastChange();
            }
        });

//        storageCallback = addStorageListener();
    }

    private void pauseTimer() {
        if (timer.isRunning()) {
            timer.cancel();
        }
    }

    private void resumeTimer() {
        if (!timer.isRunning()) {
            timer.startTimer();
        }
    }

    private void updateTimer(long timestamp) {
        timer.remainingSeconds = (int) TimeUnit.MILLISECONDS
                .toSeconds(timestamp - new Date().getTime());
    }

//    private void handleStorageEvent(StorageEvent event) {
//        if (STORAGE_KEY_TIMESTAMP.equals(event.getKey())) {
//            // TODO: 05/02/2018 Check if all browsers behave well
//            rpc.requestStatus();
//        }
//    }

//    private void updateLastChange() {
//        Storage.getLocalStorageIfSupported().setItem(STORAGE_KEY_TIMESTAMP,
//                Long.toString(new Date().getTime()));
//    }

//    private native JavaScriptObject addStorageListener()/*-{
//        var self = this;
//        var callbackFunc = $entry(function(e) {
//            self.@org.vaadin.addon.client.TimerConnector::handleStorageEvent(*)(e);
//        });
//        $wnd.addEventListener("storage", callbackFunc, false);
//        return callbackFunc;
//    }-*/;

    private String format(int minutes, int seconds) {
        return format(getState().format, minutes, seconds);
    }

    private native String format(String formatString, int minutes, int seconds)/*-{
        return $wnd.sprintf(formatString, minutes, seconds);
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

//        removeStorageListener(storageCallback);
    }

//    private native void removeStorageListener(JavaScriptObject callback)/*-{
//        $wnd.removeEventListener("storage", callback, false);
//    }-*/;

    @Override
    public TimerWidget getWidget() {
        return (TimerWidget) super.getWidget();
    }

    @Override
    public TimerState getState() {
        return (TimerState) super.getState();
    }
}
