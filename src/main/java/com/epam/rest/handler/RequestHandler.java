package com.epam.rest.handler;

import org.apache.http.ProtocolVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

import static com.epam.rest.constants.CommonConstants.*;


public class RequestHandler {

    public enum Method {
                GET("GET"),
                PUT("PUT"),
                POST("POST"),
                DELETE("DELETE"),
                HEAD("HEAD");

        private String name;

        Method(String name) {
            this.name = name;
        }

        public String sName() {
            return this.name;
        }


    }

    private static final Logger LOG = LogManager.getLogger(ServiceHandler.class);

    private BufferedReader reader;
    private Integer parsingCode;
    private Method method;
    private String url;
    private int[] httpVer;
    private HashMap<String, String> headers;
    private HashMap<String, String> params;
    private String body;


    RequestHandler(BufferedReader bufReader) {
        reader = bufReader;
        parsingCode = 200;
        method = null;
        url = null;
        headers = new HashMap<>();
        params = new HashMap<>();
        httpVer = new int[2];
        body = null;
    }

    void parseRequest() throws IOException {

        parsingCode = 200; // return OK by default
        String initialLine = reader.readLine();
        System.out.println(initialLine); //************************************
        if (initialLine == null || initialLine.length() == 0) {
            parsingCode = 0;
            return;
        }
        if (initialLine.startsWith(SPACE)) {
            parsingCode = 400;
            return; //return bad request
        }
        String cmd[] = initialLine.split(SPACE);
        if (cmd.length != 3) {
            parsingCode = 400;
            return;
        }
        try {
            method = Method.valueOf(cmd[0]);
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            parsingCode = 405;
            return;
        }

        if (cmd[2].indexOf(PROTOCOL_HTTP) == 0 && cmd[2].indexOf(POINT_SIGN) > 5) {
            String tempArr[] = cmd[2].substring(5).split("\\.");
            try {
                httpVer[0] = Integer.parseInt(tempArr[0]);
                httpVer[1] = Integer.parseInt(tempArr[1]);
            } catch (NumberFormatException nfe) {
                parsingCode = 400;
            }
        }  else parsingCode = 400;

        Integer index = cmd[1].indexOf(QUESTION_SIGN);
        if (index < 0) url = cmd[1];
        else {
            url = URLDecoder.decode(cmd[1].substring(0, index), ISO_8859_1);
        }
        if (!url.equals(URL_STR) && !url.equals("/book/")) {
            parsingCode = 400;
        }
        parseUrlParams(cmd[1].substring(index+1));
        parseHeaders();
        if (headers == null) {
            parsingCode = 400;
        }
        if (httpVer[0] == 1 && httpVer[1] >= 1 && getHeader(HOST_STR) == null) {
            parsingCode = 400;
        }
        if (method.equals(Method.PUT) && getHeader(CONTENT_LENGTH) != null) {
            if (params.size() != 0) {
                parsingCode = 400;
            } else {
                parseBody();
            }
        }

    }

    private void parseUrlParams(String prmStr) throws IOException {
        String paramLines[] = URLDecoder.decode(prmStr, ISO_8859_1).split(AMPERSAND_SIGN);
        for (String str: paramLines) {
            String tempArr[] = str.split(EQUAL_SIGN);
            if (tempArr.length == 2) {
                this.params.put(tempArr[0], tempArr[1]);
            }
            else if(tempArr.length == 1 && str.indexOf(EQUAL_SIGN) == str.length()-1) {
                params.put(tempArr[0], "");
            }
        }

    }

    private void parseHeaders() throws IOException {
        String line;
        int index;
        line = reader.readLine();
        System.out.println(line);//*************************************
        while (!line.isEmpty()) {
            index = line.indexOf(COLON_SIGN);
            if (index < 0) {
                headers = null;
                break;
            }
            else {
                String tempArr[] = line.split(COLON_SPACE);
                headers.put(tempArr[0], tempArr[1]);
            }
            line = reader.readLine();
            System.out.println(line);//***********************************************
        }

    }

    private void parseBody() throws IOException {
        if (getHeader(CONTENT_LENGTH) != null && method != Method.HEAD) {
            Integer len = Integer.parseInt(this.getHeader(CONTENT_LENGTH));
            char[] buffer = new char[len];
            reader.read(buffer);
            body = new String(buffer);
            System.out.println(body);//***********************************************
        }

    }

    public Method getMethod() {
        return this.method;
    }

    private String getHeader(String key) {
        if (headers != null)
            return this.headers.get(key);
        else return null;
    }

    public HashMap getHeaders() {
        return this.headers;
    }

    public String getRequestURL() {
        return this.url;
    }

    public String getParam(String key) {
        return this.params.get(key);
    }

    public HashMap getParams() {
        return this.params;
    }

    public Integer getParsingCode() {
        return this.parsingCode;
    }

    public String getVersionStr() {
        return this.httpVer[0] + "." + this.httpVer[1];
    }

    public ProtocolVersion getProtocolVersion() {
        return new ProtocolVersion(PROTOCOL_HTTP, this.httpVer[0], this.httpVer[1]);
    }

    public String getBody() {
        return body;
    }
}
