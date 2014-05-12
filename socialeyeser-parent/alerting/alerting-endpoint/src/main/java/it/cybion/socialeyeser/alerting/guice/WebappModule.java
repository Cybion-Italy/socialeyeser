package it.cybion.socialeyeser.alerting.guice;

import com.google.inject.Provides;
import com.google.inject.ProvisionException;
import com.google.inject.Singleton;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import it.cybion.socialeyeser.alerting.AlertsResource;
import it.cybion.socialeyeser.alerting.exceptionmappers.IllegalArgumentExceptionMapper;
import it.cybion.socialeyeser.alerting.exceptionmappers.NoSuchElementExceptionMapper;
import it.cybion.socialeyeser.alerting.representations.AlertPage;
import it.cybion.socialeyeser.alerting.representations.ErrorMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.List;

public class WebappModule extends JerseyServletModule {

    @Override
    protected void configureServlets() {

        bind(AlertsResource.class);
        bind(NoSuchElementExceptionMapper.class);
        bind(IllegalArgumentExceptionMapper.class);

        //        bindJackson();
        routeRequests();
    }

    private void bindJackson() {

        //        bind(JacksonJaxbJsonProvider.class).asEagerSingleton();
//        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
//        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
    }

    private void routeRequests() {
        //TODO check deployment on tomcat

        // Route all requests through GuiceContainer
        //        final Map<String, String> initParams = new HashMap<String, String>();
        //        initParams.put(ServletContainer.RESOURCE_CONFIG_CLASS,
        //                ClasspathResourceConfig.class.getName());
        serve("/*").with(GuiceContainer.class);
        //        filter("/*").through(GuiceContainer.class, initParams);
    }

    /**
     * Provides the list of classes that the JAXBContext should be aware of.
     *
     * @return the list of classes for the JAXBContext.
     */
    @Provides
    @RepresentationClasses
    List<Class<?>> representationTypes() {

        final Class<?>[] representationTypes = { AlertPage.class, ErrorMessage.class };
        return Arrays.asList(representationTypes);
    }

    /**
     * Customizes the JSONConfiguration for JAXB parsing/rendering of JSON.
     *
     * @return the JSONConfiguration to use
     */
    @Provides
    JSONConfiguration jsonConfiguration() {

        //https://jersey.java.net/nonav/apidocs/1.17/jersey/com/sun/jersey/api/json/JSONConfiguration.Notation.html
        final JSONConfiguration jsonConfiguration = JSONConfiguration.natural().build();

        return jsonConfiguration;
    }

    /**
     * Provides a JAXBContext configured for the specified JSONConfiguration and representation classes.
     *
     * @param jsonConfiguration the JSONConfiguration for the JAXBContext
     * @param types             the representation classes that the JAXBContext will be configured to use
     * @return a JAXBContext with the JSONConfiguration for the provided classes
     */
    @Provides
    @Singleton
    JAXBContext jaxbContext(JSONConfiguration jsonConfiguration,
            @RepresentationClasses List<Class<?>> types) {

        try {
            return new JSONJAXBContext(jsonConfiguration, types.toArray(
                    new Class<?>[types.size()]));
        } catch (JAXBException jaxbe) {
            throw new ProvisionException("Failed to create JAXBContext", jaxbe);
        }
    }

}