package org.vaadin.addon.events;

import org.vaadin.addon.Timer;

import com.vaadin.ui.Component;

public class TimerStopEvent extends Component.Event {

    public TimerStopEvent(Timer source) {
        super(source);
    }
}
