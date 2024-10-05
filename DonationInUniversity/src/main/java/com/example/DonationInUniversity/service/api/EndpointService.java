package com.example.DonationInUniversity.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EndpointService {

    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public EndpointService(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public List<String> getAllEndpoints() {
        return handlerMapping.getHandlerMethods()
                .keySet()
                .stream()
                .map(RequestMappingInfo::toString)
                .collect(Collectors.toList());
    }
}
