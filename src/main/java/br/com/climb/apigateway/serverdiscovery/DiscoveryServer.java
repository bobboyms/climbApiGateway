package br.com.climb.apigateway.serverdiscovery;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;


public class DiscoveryServer {

    public final ConfigFile configFile;

    public DiscoveryServer(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public void run() throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger1", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec1", new ProtocolCodecFilter(
                new ObjectSerializationCodecFactory()));
        acceptor.setHandler( new ServerHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.bind(new InetSocketAddress(new Integer(configFile.getLocalPort())));
    }

    public static void main( String[] args ) throws Exception {

//        new DiscoveryServer(configFile).run();

    }

}
