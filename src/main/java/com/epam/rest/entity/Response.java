package com.epam.rest.entity;

import org.apache.http.ProtocolVersion;
import org.apache.http.impl.EnglishReasonPhraseCatalog;
import org.apache.http.message.BasicStatusLine;

import java.util.*;

import static com.epam.rest.constants.CommonConstants.*;

public class Response {

    private ProtocolVersion httpVer;
    private Integer statusCode;
    private LinkedHashMap<String,String> headers = new LinkedHashMap<>();
    private String body;

    public Response (ProtocolVersion ver, Integer code) {
        this.httpVer = ver;
        this.statusCode = code;
        this.headers = new LinkedHashMap<>();
    }

    public String getStatusLine () {
        String reason = EnglishReasonPhraseCatalog.INSTANCE.getReason(statusCode, Locale.US);
        BasicStatusLine statusLine = new BasicStatusLine(httpVer, statusCode, reason);
        return statusLine.toString();
    }


    public String getHttpVer() {
        return httpVer.toString();
    }

    public void setVersion(ProtocolVersion version) {
        this.httpVer = version;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders () {
        return this.headers;
    }

    public String getServer() {
        return headers.get(SERVER);
    }

    public void setServer(String server) {
        this.headers.put(SERVER, server);
    }

    public String getDate() {
        return headers.get(DATE_STR);
    }

    public void setDate(String date) {
        this.headers.put(DATE_STR, date);
    }

    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }

    public void setContentType(String contentType) {
        this.headers.put(CONTENT_TYPE, contentType);
    }

    public String getContentLength() {
        return headers.get(CONTENT_LENGTH);
    }

    public void setContentLength(String contentLength) {
        this.headers.put(CONTENT_LENGTH, contentLength);
    }

    public String getConnection() {
        return headers.get(CONNECTION);
    }

    public void setConnection(String connection) {
        this.headers.put(CONNECTION, connection);
    }

    public String getContentLanguage() {
        return headers.get(CONTENT_LANGUAGE);
    }

    public void setContentLanguage(String lng) {
        this.headers.put(CONTENT_LANGUAGE, lng);
    }

    public String getContentEncoding() {
        return headers.get(CONTENT_ENCODING);
    }

    public void setContentEncoding(String enc) {
        this.headers.put(CONTENT_ENCODING, enc);
    }

    public void setBooksCountHeader() {
        this.headers.put(BOOKS_COUNT, BookShelf.getBooksCount().toString());
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
