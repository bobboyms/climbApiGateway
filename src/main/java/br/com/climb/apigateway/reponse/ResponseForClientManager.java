package br.com.climb.apigateway.reponse;

import br.com.climb.apigateway.serverdiscovery.Discovery;
import br.com.climb.apigateway.serverdiscovery.DiscoveryManager;
import br.com.climb.apigateway.tcpclient.ClientHandler;
import br.com.climb.apigateway.tcpclient.TcpClientApiGateway;
import br.com.climb.commons.execptions.NotFoundException;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.DiscoveryRequest;
import br.com.climb.commons.reqrespmodel.ObjectRequest;
import br.com.climb.commons.reqrespmodel.Request;
import br.com.climb.commons.reqrespmodel.Response;
import org.apache.mina.core.RuntimeIoException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResponseForClientManager implements ResponseForClient {

    private Request getLocalRequest(HttpServletRequest request) throws IOException {

        return new ObjectRequest(request.getMethod(),
                request.getPathInfo(),
                request.getContentType(),
                request.getParameterMap(),
                request.getReader().lines().collect(Collectors.joining()).getBytes(),
                request.getSession().getId());
    }

    @Override
    public void responseForClient(HttpServletResponse response, HttpServletRequest request) throws IOException, NotFoundException {

        final Request localRequest = getLocalRequest(request);

        final Discovery discoveryManager = new DiscoveryManager();
        final Optional discoveryRequest = discoveryManager.getDiscoveryRequest(localRequest);

        if (!discoveryRequest.isPresent()) {
            throw new NotFoundException("Not found url: " + request.getPathInfo());
        }

        final DiscoveryRequest discovery = (DiscoveryRequest) discoveryRequest.get();

        try {

            final TcpClient client = new TcpClientApiGateway(new ClientHandler(), discovery.getIpAddress(), new Integer(discovery.getPort()));
            client.sendRequest(localRequest);
            Response objectResponse = (Response) client.getResponse();
            client.closeConnection();

            response.setContentType(objectResponse.getContentType());
            response.setCharacterEncoding(objectResponse.getCharacterEncoding());
            response.setStatus(objectResponse.getStatus());

            if (objectResponse.getBody() != null) {
                final ServletOutputStream out = response.getOutputStream();
                out.write(objectResponse.getBody());
                out.flush();
                out.close();
            }

        } catch (RuntimeIoException e) {
            discoveryManager.removeDiscoveryRequest(discovery);
            throw new NotFoundException("Not access client: " + discovery.getIpAddress() + "/" + discovery.getPort());
        }


    }

}
