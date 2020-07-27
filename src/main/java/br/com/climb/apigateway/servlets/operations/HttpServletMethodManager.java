package br.com.climb.apigateway.servlets.operations;

import br.com.climb.apigateway.reponse.ResponseForClient;
import br.com.climb.apigateway.reponse.ResponseForClientManager;
import br.com.climb.apigateway.servlets.ControllerServlet;
import br.com.climb.commons.execptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServletMethodManager implements MethodManager {

    private final String TEXT_PLAIN = "text/plain";
    private final Logger logger = LoggerFactory.getLogger(HttpServletMethodManager.class);

    @Override
    public void processMethod(HttpServletRequest request, HttpServletResponse response) {

        final AsyncContext asyncContext = request.startAsync();

        new Thread(() -> {

            final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
            final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

            try {

                ResponseForClient responseForClient = new ResponseForClientManager();
                responseForClient.responseForClient(res, req);

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

    }


}
