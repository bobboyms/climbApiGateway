package br.com.climb.apigateway;

import br.com.climb.commons.configuration.ConfigFile;

public class ServerFactory {

    public static WebServer createWebServer(ConfigFile configFile) {
        return new JettyServer(configFile);
    }

}
