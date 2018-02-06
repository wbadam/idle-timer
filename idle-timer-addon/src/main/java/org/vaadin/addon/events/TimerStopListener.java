package org.vaadin.addon.events;

import java.lang.reflect.Method;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.util.ReflectTools;

public interface TimerStopListener extends ConnectorEventListener {
    Method TIMER_STOP_METHOD = ReflectTools
            .findMethod(TimerStopListener.class, "timerStop",
                    TimerStopEvent.class);

    void timerStop(TimerStopEvent event);
}
