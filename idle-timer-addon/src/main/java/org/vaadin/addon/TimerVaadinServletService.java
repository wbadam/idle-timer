package org.vaadin.addon;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.vaadin.server.ClientConnector;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ConnectorTracker;
import com.vaadin.ui.UI;

public class TimerVaadinServletService extends VaadinServletService {

    private final RequestHandler timerHandler = new RequestHandler() {
        @Override
        public boolean handleRequest(VaadinSession session,
                VaadinRequest request,
                VaadinResponse response) throws IOException {
            if (!session.getUIs().isEmpty()) {
                Object attribute = session
                        .getAttribute(Timer.TIMER_CONNECTOR_IDS);
                if (attribute != null) {

                    Set<String> connectorIds = (Set<String>) attribute;

                    Iterator<UI> UIs = session.getUIs().iterator();
                    while (UIs.hasNext()) {
                        ConnectorTracker tracker = UIs.next()
                                .getConnectorTracker();

                        for (String id : connectorIds) {
                            ClientConnector connector = tracker
                                    .getConnector(id);
                            if (connector != null) {
                                ((Timer) connector).resetTimer();
                            }
                        }
                    }
                }
            }
            return false;
        }
    };

    public TimerVaadinServletService(VaadinServlet servlet,
            DeploymentConfiguration deploymentConfiguration) throws
            ServiceException {
        super(servlet, deploymentConfiguration);
    }

    protected List<RequestHandler> createRequestHandlers() throws
            ServiceException {
        List<RequestHandler> requestHandlerList = super.createRequestHandlers();
        requestHandlerList.add(timerHandler);
        return requestHandlerList;
    }
}
