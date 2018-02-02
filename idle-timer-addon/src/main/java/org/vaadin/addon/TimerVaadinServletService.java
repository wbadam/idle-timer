package org.vaadin.addon;

import java.io.IOException;
import java.util.List;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;

public class TimerVaadinServletService extends VaadinServletService {

    public TimerVaadinServletService(VaadinServlet servlet,
            DeploymentConfiguration deploymentConfiguration) throws
            ServiceException {
        super(servlet, deploymentConfiguration);
    }

    protected List<RequestHandler> createRequestHandlers() throws
            ServiceException {
        List<RequestHandler> requestHandlerList = super.createRequestHandlers();

        requestHandlerList.add(new RequestHandler() {

            public boolean handleRequest(VaadinSession session,
                    VaadinRequest request,
                    VaadinResponse response) throws
                    IOException {
                if (!session.getUIs().isEmpty()) {
                    // TODO: 02/02/2018
                }
                return false;
            }

        });
        return requestHandlerList;
    }
}
