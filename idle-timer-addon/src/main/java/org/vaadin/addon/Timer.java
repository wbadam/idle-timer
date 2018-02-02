package org.vaadin.addon;

import java.util.HashSet;
import java.util.Set;

import org.vaadin.addon.client.TimerClientRpc;
import org.vaadin.addon.client.TimerState;

import com.vaadin.ui.AbstractComponent;

public class Timer extends AbstractComponent {

    static final String TIMER_CONNECTOR_IDS = "timerConnectorIds";

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

        addConnectorIdToSession(getConnectorId());
    }

    @Override
    public void detach() {
        super.detach();

        removeConnectorIdFromSession(getConnectorId());
    }

    @SuppressWarnings("unchecked")
    private void addConnectorIdToSession(String connectorId) {
        if (getSession().getAttribute(TIMER_CONNECTOR_IDS) == null) {
            getSession()
                    .setAttribute(TIMER_CONNECTOR_IDS, new HashSet<String>());
        }
        ((Set<String>) getSession().getAttribute(TIMER_CONNECTOR_IDS))
                .add(connectorId);
    }

    @SuppressWarnings("unchecked")
    private void removeConnectorIdFromSession(String connectorId) {
        ((Set<String>) getSession().getAttribute(TIMER_CONNECTOR_IDS))
                .remove(connectorId);
    }

    void resetTimer() {
        getRpcProxy(TimerClientRpc.class).resetTimer(getState().resetSeconds);
    }

    @Override
    protected TimerState getState() {
        return (TimerState) super.getState();
    }
}
