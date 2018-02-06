package org.vaadin.addon.events;

import java.lang.reflect.Method;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.util.ReflectTools;

public interface TimerTimePassListener extends ConnectorEventListener {
    Method TIME_PASS_METHOD = ReflectTools
            .findMethod(TimerTimePassListener.class, "timePass",
                    TimerTimePassEvent.class);

    void timePass(TimerTimePassEvent event);
}
