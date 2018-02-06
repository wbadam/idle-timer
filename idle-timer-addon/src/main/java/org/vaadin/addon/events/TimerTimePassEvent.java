package org.vaadin.addon.events;

import org.vaadin.addon.Timer;

import com.vaadin.ui.Component;

public class TimerTimePassEvent extends Component.Event {

    private int secondsLeft;

    public TimerTimePassEvent(Timer source, int secondsLeft) {
        super(source);

        this.secondsLeft = secondsLeft;
    }

    public int getSecondsLeft() {
        return this.secondsLeft;
    }
}
