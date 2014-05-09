package it.cybion.socialeyeser;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TestJerseyServletModule extends JerseyServletModule {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TestJerseyServletModule.class);
    
    @Override
    protected void configureServlets() {
    
        bind(InfluencersService.class);
        
        bindJackson();
        routeRequests();
    }
    
    private void bindJackson() {
    
        bind(JacksonJaxbJsonProvider.class).asEagerSingleton();
        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
    }
    
    private void routeRequests() {
    
        // Route all requests through GuiceContainer
        final Map<String, String> initParams = new HashMap<String, String>();
        // initParams.put(ServletContainer.RESOURCE_CONFIG_CLASS,
        // ClasspathResourceConfig.class.getName());
        serve("/*").with(GuiceContainer.class);
        filter("/*").through(GuiceContainer.class, initParams);
    }
    
}
