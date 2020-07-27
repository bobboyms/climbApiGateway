package br.com.climb.apigateway.servlets;

import br.com.climb.apigateway.servlets.operations.HttpServletMethodManager;
import br.com.climb.apigateway.servlets.operations.MethodManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {

        final MethodManager methodManager = new HttpServletMethodManager();
        methodManager.processMethod(request, response);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {

        final MethodManager methodManager = new HttpServletMethodManager();
        methodManager.processMethod(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        final MethodManager methodManager = new HttpServletMethodManager();
        methodManager.processMethod(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        final MethodManager methodManager = new HttpServletMethodManager();
        methodManager.processMethod(request, response);

    }
}