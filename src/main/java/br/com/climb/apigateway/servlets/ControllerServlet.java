package br.com.climb.apigateway.servlets;

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
import br.com.climb.commons.url.NormalizedUrl;
import br.com.climb.commons.url.NormalizedUrlManager;
import org.apache.mina.core.RuntimeIoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControllerServlet extends HttpServlet {

    private static final String TEXT_PLAIN = "text/plain";
    private static final Logger logger = LoggerFactory.getLogger(ControllerServlet.class);

    private Request getLocalRequest(HttpServletRequest request) throws IOException {

        return new ObjectRequest(request.getMethod(),
                request.getPathInfo(),
                request.getContentType(),
                request.getParameterMap(),
                request.getReader().lines().collect(Collectors.joining()).getBytes(),
                request.getSession().getId());
    }

    private synchronized void responseForClient(HttpServletResponse response, HttpServletRequest request) throws IOException, NotFoundException {

        final Request localRequest = getLocalRequest(request);

        System.out.println(request.getPathInfo());
        System.out.println(request.getMethod());

        Discovery discoveryManager = new DiscoveryManager();
        Optional discoveryRequest = discoveryManager.getDiscoveryRequest(localRequest);

        if (!discoveryRequest.isPresent()) {
            throw new NotFoundException("Not found url: " + request.getPathInfo());
        }

        DiscoveryRequest discovery = (DiscoveryRequest) discoveryRequest.get();

        System.out.println("Discovery: " + discovery);

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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

                try {

                    responseForClient(res, req);

                } catch (Exception e) {

                    logger.error("DoPut {}", e);

                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                } finally {
                    asyncContext.complete();
                }

            }).start();
        } catch (Exception e) {
            logger.error("THREAD doPut {}", e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

                try {

                    responseForClient(res, req);

                } catch (Exception e) {

                    logger.error("DoDelete {}", e);

                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                } finally {
                    asyncContext.complete();
                }

            }).start();
        } catch (Exception e) {
            logger.error("THREAD doDelete {}", e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

                try {

                    responseForClient(res, req);

                } catch (Exception e) {

                    logger.error("DoPost {}", e);

                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                } finally {
                    asyncContext.complete();
                }

            }).start();

        } catch (Exception e) {
            logger.error("THREAD doPost {}", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            final AsyncContext asyncContext = request.startAsync();

            new Thread(() -> {

                System.out.println("Criou thread: " + Thread.currentThread().getId());

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

                try {

                    final Request localRequest = getLocalRequest(request);

                    System.out.println(request.getPathInfo());
                    System.out.println(request.getMethod());

                    Discovery discoveryManager = new DiscoveryManager();
                    Optional discoveryRequest = discoveryManager.getDiscoveryRequest(localRequest);

                    if (!discoveryRequest.isPresent()) {
                        throw new NotFoundException("Not found url: " + request.getPathInfo());
                    }

                    DiscoveryRequest discovery = (DiscoveryRequest) discoveryRequest.get();

                    System.out.println("Discovery: " + discovery);

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

                } catch (NotFoundException e) {
                    logger.error("Not fount {}", e);
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                } catch (Exception e) {
                    logger.error("DoGet {}", e);
                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                } finally {
                    asyncContext.complete();
                }

            }).start();

        } catch (Exception e) {
            logger.error("THREAD DoGet {}", e);
        }

    }
}