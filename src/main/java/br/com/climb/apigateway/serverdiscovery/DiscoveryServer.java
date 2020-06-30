package br.com.climb.apigateway.serverdiscovery;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;


public class DiscoveryServer {

    private static final int PORT = 3030;

    public static ConfigFile configFile;

    private void loadConfigurations() throws IOException, ConfigFileException {

        configFile = new FactoryConfigFile().getConfigFile("framework.properties");

    }

    public void run() throws IOException, ConfigFileException {
        loadConfigurations();
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger1", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec1", new ProtocolCodecFilter(
                new ObjectSerializationCodecFactory()));
        acceptor.setHandler( new ServerHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.bind(new InetSocketAddress(PORT));
    }

    public static void main( String[] args ) throws Exception {

        new DiscoveryServer().run();

    }

}
