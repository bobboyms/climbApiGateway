package br.com.climb.apigateway.serverdiscovery;

import br.com.climb.commons.discovery.model.DiscoveryRequest;

import java.util.HashSet;
import java.util.Set;

public class DiscoveryManager implements Discovery {

    private static final Set<DiscoveryRequest> discoveryRequests = new HashSet<>();

    @Override
    public DiscoveryRequest getDiscoveryRequest(String method, String url) {
        return null;
    }

    @Override
    public void addDiscoveryRequest(DiscoveryRequest request) {
        discoveryRequests.add(request);
    }

}
