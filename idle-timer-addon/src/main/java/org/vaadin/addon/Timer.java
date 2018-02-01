package org.vaadin.addon;

import org.vaadin.addon.client.TimerState;

import com.vaadin.ui.AbstractComponent;

public class Timer extends AbstractComponent {

    public Timer() {

    }

    @Override
    protected TimerState getState() {
        return (TimerState) super.getState();
    }
}
