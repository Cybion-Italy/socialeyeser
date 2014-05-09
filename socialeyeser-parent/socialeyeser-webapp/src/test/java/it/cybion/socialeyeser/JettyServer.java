package it.cybion.socialeyeser;

import java.util.EnumSet;
import java.util.EventListener;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.servlet.GuiceFilter;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class JettyServer {
    
    private final Server server;
    
    private final ServletContextHandler rootContextHandler;
    
    protected final String baseUri;
    
    public JettyServer(int port) {
    
        this.server = new Server(port);
        this.rootContextHandler = new ServletContextHandler(this.server, "/",
                ServletContextHandler.SESSIONS);
        this.rootContextHandler.addFilter(GuiceFilter.class, "/*",
                EnumSet.of(DispatcherType.REQUEST));
        this.rootContextHandler.addServlet(EmptyServlet.class, "/*");
        this.baseUri = "http://localhost:" + port + "/";
    }
    
    public void addEventListener(EventListener configuration) {
    
        this.rootContextHandler.addEventListener(configuration);
    }
    
    public void start() {
    
        try {
            this.server.start();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    public void stop() {
    
        try {
            this.server.stop();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
