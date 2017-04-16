package com.epam.rest;

import com.epam.rest.entity.Book;
import org.apache.http.ProtocolVersion;
import org.apache.http.impl.EnglishReasonPhraseCatalog;
import org.apache.http.message.BasicHttpResponse;

import java.io.IOException;
import java.util.Locale;


public class ResponseHandler {

    private final static String CT_JSON = "application/json";
    private final static String CT_HTML = "text/html; charset=utf-8";

    private RequestHandler request;
    private BasicHttpResponse response;

    public ResponseHandler (RequestHandler rqst) {
        this.request = rqst;
        this.response = null;
    }

    public BasicHttpResponse getResponse() {
        return this.response;
    }

    public void prepareResponse() {
        int code = 500;
        try {
            code = this.request.parseRequest();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        ProtocolVersion httpVerStr = this.request.getProtocolVersion();
        String reason = EnglishReasonPhraseCatalog.INSTANCE.getReason(code, Locale.US);
        this.response = new BasicHttpResponse(httpVerStr, code, reason);
        SetCommonHeaders();
        if (this.request.getMethod().equals("GET")) {
            prepareGet();
        } else if (this.request.getMethod().equals("POST")) {
            preparePost();
        } else if (this.request.getMethod().equals("PUT")) {
            preparePut();
        } else if (this.request.getMethod().equals("DELETE")) {
            prepareDelete();
        }

    }

    private void SetCommonHeaders() {
        this.response.addHeader(RequestHandler.getDateHeader());
        this.response.addHeader("Content-Encoding","gzip");
        this.response.addHeader("Content-Language","en");
        this.response.addHeader("Connection","Closed");
    }

    private void prepareGet() {
        this.response.addHeader("Content-Type","application/json");
        if (this.request.getParams().size() == 0) {

        }

        this.response.addHeader("Content-Length", );
    }

    private void preparePost() {

    }
    private void preparePut() {

    }

    private void prepareDelete() {

    }
}
