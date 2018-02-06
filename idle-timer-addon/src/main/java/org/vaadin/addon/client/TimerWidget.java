package org.vaadin.addon.client;

import com.google.gwt.user.client.ui.Label;

public class TimerWidget extends Label {

    private long timeDifference;

    public TimerWidget() {
        setStyleName("idle-timer");
    }

    void clearText() {
        setText("");
    }

    public void setTimeDifference(long timeDifference) {
        this.timeDifference = timeDifference;
    }
}