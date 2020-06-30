package br.com.climb.apigateway.tcpclient;

import br.com.climb.commons.generictcpclient.GenericTcpClient;
import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.reqrespmodel.ObjectRequest;
import br.com.climb.commons.reqrespmodel.Request;
import br.com.climb.commons.reqrespmodel.Response;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class TcpClientApiGateway extends GenericTcpClient<Response> {

    public TcpClientApiGateway(GenericTcpClientHandler clientHandler, String hostname, int port) {
        super(clientHandler, hostname, port);
    }

    public static void main(String[] args) {
        try {

            String data = "{\n" +
                    "    \"id\": 13065,\n" +
                    "    \"nome\": \"thiago\",\n" +
                    "    \"idade\": 33,\n" +
                    "    \"altura\": 177.0,\n" +
                    "    \"peso\": 36.0,\n" +
                    "    \"casado\": true\n" +
                    "}";

            Request request = new ObjectRequest(
                    "GET","/get/id/30/",
                    "application/json",new HashMap<>(),
                    data.getBytes(), "");

//            TcpClientApiGateway client = new TcpClientApiGateway(request);
//            client.initialize();
//            Response objectResponse = client.getResponse();
//            client.closeConnection();
//            System.out.println(objectResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}