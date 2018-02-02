package org.vaadin.addon.client;

import java.util.concurrent.TimeUnit;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;

public class TimerWidget extends Label {

    private class IdleTimer extends Timer {

        private static final int PERIOD = 1000;

        @Override
        public void run() {
            final long min = TimeUnit.SECONDS.toMinutes(remainingSeconds);
            final long sec = TimeUnit.SECONDS.toSeconds(
                    remainingSeconds - TimeUnit.MINUTES.toSeconds(min));

            StringBuilder time = new StringBuilder();
            if (min < 10) {
                time.append("0");
            }
            time.append(min);
            time.append(":");
            if (sec < 10) {
                time.append("0");
            }
            time.append(sec);

            setText(time.toString());

            remainingSeconds--;
        }

        public void startTimer() {
            scheduleRepeating(PERIOD);
        }
    }

    private long remainingSeconds;

    public TimerWidget() {
        setStyleName("idle-timer");

        new IdleTimer().startTimer();
    }

    public void setRemainingSeconds(int seconds) {
        this.remainingSeconds = seconds;
    }
}