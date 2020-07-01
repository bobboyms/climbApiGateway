package br.com.climb.apigateway.serverdiscovery;


import br.com.climb.commons.model.DiscoveryRequest;
import br.com.climb.commons.reqrespmodel.Request;
import br.com.climb.commons.url.NormalizedUrlManager;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DiscoveryManager implements Discovery {

    private static final Set<DiscoveryRequest> discoveryRequests = new HashSet<>();

    @Override
    public Optional getDiscoveryRequest(Request request) {

        return discoveryRequests.parallelStream().filter(discoveryRequestObject -> discoveryRequestObject.getUrls().get(request.getMethod())
                .parallelStream().anyMatch(u -> u.equals(new NormalizedUrlManager()
                        .getNormalizedUrl(request, discoveryRequestObject.getReservedWords())))).findAny();
    }

    @Override
    public void addDiscoveryRequest(DiscoveryRequest request) {
        discoveryRequests.add(request);
    }

    @Override
    public void removeDiscoveryRequest(DiscoveryRequest request) {
        discoveryRequests.remove(request);
    }

}
