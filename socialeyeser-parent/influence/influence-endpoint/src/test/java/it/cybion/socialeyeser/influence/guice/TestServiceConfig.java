package it.cybion.socialeyeser.influence.guice;

import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;


/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TestServiceConfig extends GuiceServletContextListener {
    
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new TestJerseyServletModule()
                );
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        
    }
}
