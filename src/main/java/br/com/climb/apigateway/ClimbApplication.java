package br.com.climb.apigateway;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.ConfigFileBean;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;
import com.google.common.base.Strings;

import java.io.IOException;

public class ClimbApplication {

    public static ConfigFile configFile;

    private static void loadConfigurations(Class<?> mainclass) throws IOException, ConfigFileException {

        configFile = new FactoryConfigFile().getConfigFile("framework.properties");

        if (Strings.isNullOrEmpty(configFile.getPackage())) {
            System.out.println("caiu aki: " + mainclass.getPackage().getName());
            ((ConfigFileBean)configFile).setPackge(mainclass.getPackage().getName());
        }
    }

    private static void startWebServer() throws Exception {
        WebServer webServer = ServerFactory.createWebServer(configFile);
        webServer.start();
    }

    public static void run(Class<?> mainclass) throws Exception {
        loadConfigurations(mainclass);
        startWebServer();
    }

}
