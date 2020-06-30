package br.com.climb.apigateway.serverdiscovery;

import br.com.climb.commons.discovery.model.DiscoveryRequest;
import br.com.climb.commons.discovery.model.DiscoveryResponseObject;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends IoHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    private final Discovery discovery = new DiscoveryManager();

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        System.out.println("Adicionou Discovery: " + message);
        discovery.addDiscoveryRequest((DiscoveryRequest) message);

        DiscoveryResponseObject discoveryResponseObject = new DiscoveryResponseObject();
        discoveryResponseObject.setStatusCode(200);
        session.write(discoveryResponseObject);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

        System.out.println("---- deu erro ----");
        System.out.println(cause);
        DiscoveryResponseObject discoveryResponseObject = new DiscoveryResponseObject();
        discoveryResponseObject.setStatusCode(500);
        session.write(discoveryResponseObject);

    }

    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }
}
