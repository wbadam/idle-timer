package org.vaadin.addon;

import org.vaadin.addon.client.TimerState;

import com.vaadin.ui.AbstractComponent;

public class Timer extends AbstractComponent {

    public Timer() {

    }

    @Override
    public void attach() {
        if (!(getSession().getService() instanceof TimerVaadinServletService)) {
            throw new IllegalArgumentException(
                    "The service is of incorrect type. Make sure to use an"
                            + " instance of TimerVaadinServlet as web servlet.");
        }

        super.attach();
    }

    @Override
    protected TimerState getState() {
        return (TimerState) super.getState();
    }
}
