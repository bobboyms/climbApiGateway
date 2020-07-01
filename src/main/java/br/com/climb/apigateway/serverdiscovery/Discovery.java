package br.com.climb.apigateway.serverdiscovery;


import br.com.climb.commons.model.DiscoveryRequest;
import br.com.climb.commons.reqrespmodel.Request;

import java.util.Optional;

public interface Discovery {

    Optional getDiscoveryRequest(Request request);

    void addDiscoveryRequest(DiscoveryRequest response);

    void removeDiscoveryRequest(DiscoveryRequest request);
}
