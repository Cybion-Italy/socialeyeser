package it.cybion.socialeyeser.guice;

import it.cybion.commons.guice.PropertiesModule;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class WebappConfig extends GuiceServletContextListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WebappConfig.class);
    
    @Override
    public Injector getInjector() {
    
        final Injector injector = Guice.createInjector(new PropertiesModule(
                "/socialeyeser.properties"), new WebappModule());
        
        return injector;
    }
    
    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
    
        LOGGER.info("on context destroyed");
        try {
            final Injector injector = (Injector) servletContextEvent.getServletContext()
                    .getAttribute(Injector.class.getName());
            
        } catch (final Exception e) {
            LOGGER.error("Unexpected error during shutdown: " + e.getMessage());
        }
        
        LOGGER.info("context destroyed");
    }
    
}
