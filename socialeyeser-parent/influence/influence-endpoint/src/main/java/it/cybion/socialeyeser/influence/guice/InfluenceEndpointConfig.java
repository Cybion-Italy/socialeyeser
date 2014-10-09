package it.cybion.socialeyeser.influence.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import it.cybion.commons.guice.PropertiesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;

public class InfluenceEndpointConfig extends GuiceServletContextListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InfluenceEndpointConfig.class);
    
    @Override
    public Injector getInjector() {
    
        final Injector injector = Guice.createInjector(
                new PropertiesModule("/socialeyeser.properties"),
                new InfluenceResourcesModule());
        
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
