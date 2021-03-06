package org.vaadin.addon.client;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.AbstractComponentState;

public class TimerState extends AbstractComponentState {

    public static final String EVENT_STOP = "stop";
    public static final String EVENT_PASS = "pass";

    public int resetSeconds;
    public long timeDifference;

    public List<Integer> notifySeconds = new ArrayList<>();

    public String format = "%02d:%02d";
}