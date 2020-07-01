package br.com.climb.apigateway;

import br.com.climb.apigateway.serverdiscovery.DiscoveryServer;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;

import java.io.IOException;

public class ClimbApplication {

    public static ConfigFile configFile;

    private static void loadConfigurations(Class<?> mainclass) throws IOException, ConfigFileException {

        configFile = new FactoryConfigFile().getConfigFile("framework.properties");
    }

    private static void startWebServer() throws Exception {
        WebServer webServer = ServerFactory.createWebServer(configFile);
        webServer.start();
    }

    public static void startServerDiscovery() throws IOException, ConfigFileException {
        new DiscoveryServer(configFile).run();
    }

    public static void run(Class<?> mainclass) throws Exception {
        loadConfigurations(mainclass);
        startServerDiscovery();
        startWebServer();
    }

}
