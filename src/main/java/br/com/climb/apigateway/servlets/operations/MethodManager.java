package br.com.climb.apigateway.servlets.operations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MethodManager {
    void processMethod(HttpServletRequest request, HttpServletResponse response);
}
