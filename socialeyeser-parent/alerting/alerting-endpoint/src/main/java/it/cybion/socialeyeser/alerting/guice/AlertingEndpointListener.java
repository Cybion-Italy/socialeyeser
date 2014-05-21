package it.cybion.socialeyeser.alerting.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;

public class AlertingEndpointListener extends GuiceServletContextListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertingEndpointListener.class);
    
    @Override
    public Injector getInjector() {
    
        final Injector injector = Guice.createInjector(new AlertingEndpointModule());
        
        return injector;
    }
    
    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
    
        LOGGER.info("on context destroyed");
        try {
            servletContextEvent.getServletContext()
                    .getAttribute(Injector.class.getName());
            
        } catch (final Exception e) {
            LOGGER.error("Unexpected error during shutdown: " + e.getMessage());
        }
        
        LOGGER.info("context destroyed");
    }
    
}
