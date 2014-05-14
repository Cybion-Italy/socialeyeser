package it.cybion.socialeyeser.influence.guice;

import com.sun.jersey.api.core.ClasspathResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import it.cybion.socialeyeser.influence.resources.InfluencersResource;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.HashMap;
import java.util.Map;

public class InfluenceResourcesModule extends JerseyServletModule {
    
    @Override
    protected void configureServlets() {

        //TODO why singleton? due to the random generator?
        bind(InfluencersResource.class).asEagerSingleton();

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
        initParams.put(ServletContainer.RESOURCE_CONFIG_CLASS,
                ClasspathResourceConfig.class.getName());
        serve("/*").with(GuiceContainer.class);
        filter("/*").through(GuiceContainer.class, initParams);
    }
    
}