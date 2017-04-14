package com.epam.rest;

import com.sun.deploy.net.BasicHttpRequest;
import com.sun.deploy.net.HttpRequest;
import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class RequestHandler {

    private enum RequestTypes{GET, PUT, POST, DELETE}

    private String method;
    private String url;
    private BufferedReader reader;
    private HashMap<String, String> headers;
    private HashMap<String, String> params;
    private int[] httpVer;

    public RequestHandler (String header) {
        String[] headersArray = header.split("\r\n");
        HttpRequest request = new BasicHttpRequest();
//        HttpGet req = new HttpGet();
//        HttpServletRequest

            for (String str: headersArray) {
                headers.put(str.split(": ")[0], str.split(": ")[1]);
            }
    }

    public RequestHandler(BufferedReader bufReader) {
        reader = bufReader;
        method = "";
        url = "";
        headers = new HashMap<>();
        params = null;
        httpVer = new int[2];
    }

    public int parseRequest() throws IOException {
        String  tempArr[];
        int result, index;

        result = 200; // return OK by default
        String initialLine = reader.readLine();
        if (initialLine == null || initialLine.length() == 0) return 0;
        if (initialLine.startsWith(" ")) {
            return 400; //return bad request
        }

        String cmd[] = initialLine.split("\\s");
        if (cmd.length != 3) {
            return 400;
        }

        if (cmd[2].indexOf("HTTP/") == 0 && cmd[2].indexOf('.') > 5) {
            tempArr = cmd[2].substring(5).split("\\.");
            try {
                httpVer[0] = Integer.parseInt(tempArr[0]);
                httpVer[1] = Integer.parseInt(tempArr[1]);
            } catch (NumberFormatException nfe) {
                result = 400;
            }
        }
        else result = 400;

        if (cmd[0].equals("GET") || cmd[0].equals("HEAD")) {
            method = cmd[0];
            index = cmd[1].indexOf('?');
            if (index < 0) url = cmd[1];
            else {
                url = URLDecoder.decode(cmd[1].substring(0, index), "ISO-8859-1");
                parseUrlParams(cmd[1].substring(index+1));
            }
            parseHeaders();
            if (headers == null) result = 400;
        }
        else if (cmd[0].equals("POST")) {
            result = 501; // not implemented
        }
        else if (httpVer[0] == 1 && httpVer[1] >= 1) {
            if (cmd[0].equals("OPTIONS") ||
                    cmd[0].equals("PUT") ||
                    cmd[0].equals("DELETE") ||
                    cmd[0].equals("TRACE") ||
                    cmd[0].equals("CONNECT")) {
                result = 501; // not implemented
            }
        }
        else {
            // meh not understand, bad request
            result = 400;
        }

        if (httpVer[0] == 1 && httpVer[1] >= 1 && getHeader("Host") == null) {
            result = 400;
        }

        return result;
    }

    private void parseUrlParams(String prmStr) throws IOException {
        String paramLines[] = prmStr.split("&");
        this.params = new HashMap<>();
        for (int i=0; i < paramLines.length; i++) {
            String tempArr[] = paramLines[i].split("=");
            if (tempArr.length == 2) {
                this.params.put(URLDecoder.decode(tempArr[0], "ISO-8859-1"),
                        URLDecoder.decode(tempArr[1], "ISO-8859-1"));
            }
            else if(tempArr.length == 1 && paramLines[i].indexOf('=') == paramLines[i].length()-1) {
                params.put(URLDecoder.decode(tempArr[0], "ISO-8859-1"), "");
            }
        }
    }

    private void parseHeaders() throws IOException {
        String line;
        int index;
        line = reader.readLine();
        while (!line.equals("")) {
            index = line.indexOf(':');
            if (index < 0) {
                headers = null;
                break;
            }
            else {
                String tempArr[] = line.split(": ");
                headers.put(tempArr[0], tempArr[1]);
//                headers.put(line.substring(0, index).toLowerCase(), line.substring(index+1).trim());
            }
            line = reader.readLine();
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getHeader(String key) {
        if (headers != null)
            return (String) this.headers.get(key);
        else return null;
    }

    public HashMap getHeaders() {
        return this.headers;
    }

    public String getRequestURL() {
        return this.url;
    }

    public String getParam(String key) {
        return (String) this.params.get(key);
    }

    public HashMap getParams() {
        return this.params;
    }

    public String getVersionStr() {
        return this.httpVer[0] + "." + this.httpVer[1];
    }

    public ProtocolVersion getProtocolVersion() {
        return new ProtocolVersion("HTTP/", this.httpVer[0], this.httpVer[1]);
    }

    public static Header getDateHeader() {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return new BasicHeader("Date",  format.format(new Date()) + " GMT");
    }





}
