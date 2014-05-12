package it.cybion.socialeyeser.alerting.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.ServletContextEvent;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TestServiceConfig extends GuiceServletContextListener {

    private final AlertingEndpointModule alertingEndpointModule;

    public TestServiceConfig() {

        //TODO inject also mock dependencies as needed
        alertingEndpointModule = new AlertingEndpointModule();

    }

    @Override
    protected Injector getInjector() {

        return Guice.createInjector(this.alertingEndpointModule);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
