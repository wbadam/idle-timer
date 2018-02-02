package org.vaadin.addon;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;

public class TimerVaadinServlet extends VaadinServlet {

    protected VaadinServletService createServletService(
            DeploymentConfiguration deploymentConfiguration) throws
            ServiceException {
        VaadinServletService vaadinServletService = new TimerVaadinServletService(
                this, deploymentConfiguration);
        vaadinServletService.init();
        return vaadinServletService;
    }
}
