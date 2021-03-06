package br.com.climb.apigateway;

import br.com.climb.apigateway.servlets.ControllerServlet;
import br.com.climb.apigateway.servlets.filters.CorsFilter;
import br.com.climb.apigateway.servlets.filters.JwtFilter;
import br.com.climb.commons.configuration.ConfigFile;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.util.EnumSet;

public class JettyServer implements WebServer {

    private final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    private final ConfigFile configFile;

    public JettyServer(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public void start() throws Exception {

        final Server server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath("/");

        servletContextHandler.addServlet(ControllerServlet.class, "/*");
        servletContextHandler.addEventListener(new MyContextListener());

        servletContextHandler.addFilter(JwtFilter.class,configFile.getSecurityUrl(),
                EnumSet.of(DispatcherType.REQUEST));

        servletContextHandler.addFilter(CorsFilter.class,"/*",
                EnumSet.of(DispatcherType.REQUEST));

        HandlerList handlers = new HandlerList();
        handlers.addHandler(servletContextHandler);

        server.setHandler(handlers);
        server.start();

    }

    public static class MyContextListener implements ServletContextListener
    {
        @Override
        public void contextInitialized(ServletContextEvent sce)
        {
            System.err.printf("MyContextListener.contextInitialized(%s)%n", sce);
            sce.getServletContext().addListener(new MyRequestListener());
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce)
        {
            System.err.printf("MyContextListener.contextDestroyed(%s)%n", sce);
        }
    }

    public static class MyRequestListener implements ServletRequestListener
    {
        @Override
        public void requestDestroyed(ServletRequestEvent sre)
        {
            System.err.printf("MyRequestListener.requestDestroyed(%s)%n", sre);
        }

        @Override
        public void requestInitialized(ServletRequestEvent sre)
        {
            System.err.printf("MyRequestListener.requestInitialized(%s)%n", sre);
        }
    }

}