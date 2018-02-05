package org.vaadin.addon;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.vaadin.addon.client.TimerClientRpc;
import org.vaadin.addon.client.TimerState;

import com.vaadin.server.Page;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractComponent;

public class Timer extends AbstractComponent {

    static final String TIMER_CONNECTOR_IDS = "timerConnectorIds";

    private boolean refresh = true;

    public Timer() {

    }

    public void start() {
        getRpcProxy(TimerClientRpc.class).start(getExpiryClientTimestamp());
    }

    public void disable() {
        getRpcProxy(TimerClientRpc.class).disable();
    }

    public void enable() {
        getRpcProxy(TimerClientRpc.class).enable();
    }

    public void stop() {
        getRpcProxy(TimerClientRpc.class).stop();
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public Registration addStartListener() {
        // TODO: 05/02/2018  
        return null;
    }

    public Registration addStopListener() {
        // TODO: 05/02/2018  
        return null;
    }

    public Registration addListenerTo(int minutes) {
        // TODO: 05/02/2018
        return null;
    }

    @Override
    public void attach() {
        if (!(getSession().getService() instanceof TimerVaadinServletService)) {
            throw new IllegalArgumentException(
                    "The service is of incorrect type. Make sure to use an"
                            + " instance of TimerVaadinServlet as web servlet.");
        }

        super.attach();

        getState().timeDifference =
                new Date().getTime() - Page.getCurrent().getWebBrowser()
                        .getCurrentDate().getTime();

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

    public void setTimerSeconds(int seconds) {
        getState().resetSeconds = seconds;
    }

    void resetTimer() {
        if (refresh) {
            getRpcProxy(TimerClientRpc.class)
                    .resetTimer(getExpiryClientTimestamp());
        }
    }

    private long getExpiryClientTimestamp() {
        return new Date().getTime() + getState(false).timeDifference
                + TimeUnit.SECONDS.toMillis(getState(false).resetSeconds);
    }

    @Override
    protected TimerState getState() {
        return (TimerState) super.getState();
    }

    @Override
    protected TimerState getState(boolean markAsDirty) {
        return (TimerState) super.getState(markAsDirty);
    }
}
