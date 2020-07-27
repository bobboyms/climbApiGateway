package br.com.climb.apigateway.reponse;

import br.com.climb.commons.execptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ResponseForClient {
    void responseForClient(HttpServletResponse response, HttpServletRequest request) throws IOException, NotFoundException;
}
