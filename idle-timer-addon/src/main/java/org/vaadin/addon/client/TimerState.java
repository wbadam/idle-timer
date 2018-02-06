package org.vaadin.addon.client;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.AbstractComponentState;

public class TimerState extends AbstractComponentState {

    public static final String EVENT_STOP = "stop";

    public int resetSeconds;
    public long timeDifference;

    public List<Integer> notifySeconds = new ArrayList<>();
}