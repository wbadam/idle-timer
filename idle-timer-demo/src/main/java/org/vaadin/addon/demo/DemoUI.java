package org.vaadin.addon.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addon.Timer;
import org.vaadin.addon.TimerVaadinServlet;
import org.vaadin.addon.events.TimerTimePassEvent;
import org.vaadin.addon.events.TimerTimePassListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("Timer Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends TimerVaadinServlet {

    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(new Button("Btn", e -> Notification.show("clicked")));

        Timer timer = new Timer(3, "%d minutes and %02d seconds left");
        timer.setTimerSeconds(130);
        timer.start();
        layout.addComponent(timer);

        timer.addListenerTo(2, new TimerTimePassListener() {
            @Override
            public void timePass(TimerTimePassEvent event) {
                System.out.println("2 minutes");
            }
        });

        timer.addListenerTo(1, new TimerTimePassListener() {
            @Override
            public void timePass(TimerTimePassEvent event) {
                System.out.println("1 minutes");
            }
        });

        setContent(layout);
    }
}
