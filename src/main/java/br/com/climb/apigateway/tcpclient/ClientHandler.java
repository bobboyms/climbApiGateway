package br.com.climb.apigateway.tcpclient;

import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.reqrespmodel.ObjectResponse;
import br.com.climb.commons.reqrespmodel.Response;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends GenericTcpClientHandler<Response> {

}