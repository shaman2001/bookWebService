package com.epam.rest.handler;

//import com.sun.deploy.net.BasicHttpRequest;
//import com.sun.deploy.net.HttpRequest;
import org.apache.http.ProtocolVersion;
import sun.nio.cs.ISO_8859_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

import static com.epam.rest.constants.CommonConstants.*;


public class RequestHandler {

    private enum RequestTypes{GET, PUT, POST, DELETE}

    private Integer parsingCode;
    private String method;
    private String url;
    private int[] httpVer;
    private BufferedReader reader;
    private HashMap<String, String> headers;
    private HashMap<String, String> params;


    public RequestHandler(BufferedReader bufReader) {
        parsingCode = 200;
        reader = bufReader;
        method = "";
        url = "";
        headers = new HashMap<>();
        params = new HashMap<>();
        httpVer = new int[2];
    }

    public void parseRequest() throws IOException {

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

        if (cmd[2].indexOf(PROTOCOL_HTTP) == 0 && cmd[2].indexOf(POINT_SIGN) > 5) {
            String tempArr[] = cmd[2].substring(5).split("\\.");
            try {
                httpVer[0] = Integer.parseInt(tempArr[0]);
                httpVer[1] = Integer.parseInt(tempArr[1]);
            } catch (NumberFormatException nfe) {
                parsingCode = 400;
            }
        }
        else parsingCode = 400;

        method = cmd[0];
        Integer index = cmd[1].indexOf(QUESTION_SIGN);
        if (index < 0) url = cmd[1];
        else {
            url = URLDecoder.decode(cmd[1].substring(0, index), ISO_8859_1);
            parseUrlParams(cmd[1].substring(index+1));
        }
        parseHeaders();
        if (headers == null) {
            parsingCode = 400;
        }
        if (httpVer[0] == 1 && httpVer[1] >= 1 && getHeader(HOST_STR) == null) {
            parsingCode = 400;
        }
    }

    private void parseUrlParams(String prmStr) throws IOException {
        String paramLines[] = URLDecoder.decode(prmStr, ISO_8859_1).split(AMPERSAND_SIGN);
//        this.params = new HashMap<>();
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
//                headers.put(line.substring(0, index).toLowerCase(), line.substring(index+1).trim());
            }
            line = reader.readLine();
            System.out.println(line);//***********************************************
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getHeader(String key) {
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


}
