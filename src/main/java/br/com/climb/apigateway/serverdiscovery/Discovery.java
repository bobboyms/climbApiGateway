package br.com.climb.apigateway.serverdiscovery;

import br.com.climb.commons.discovery.model.DiscoveryRequest;

public interface Discovery {
    DiscoveryRequest getDiscoveryRequest(String method, String url);
    void addDiscoveryRequest(DiscoveryRequest response);
}
