package com.epam.rest;

import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.Socket;
import java.util.Date;

public class SocketHandler implements Runnable {


    private Socket sock;
    private OutputStream outStr;
    private InputStream inStr;

    private final static String RESPONSE_STR = "<html><body><h1>Test response</h1></body></html>";
    private static final String DEFAULT_FILES_DIR = "/www";

    public SocketHandler (Socket socket) throws IOException {
        this.sock = socket;
        this.outStr = sock.getOutputStream();
        this.inStr = sock.getInputStream();
    }

    public void run () {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
            RequestHandler request = new RequestHandler(reader);
            ResponseHandler response = new ResponseHandler(request);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String readRequest() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
        RequestHandler request = new RequestHandler(reader);
        
        StringBuilder builder = new StringBuilder();
        String ln = null;
        while (true) {
            ln = reader.readLine();
            if (ln == null || ln.isEmpty()) {
                break;
            }
            builder.append(ln + System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    private String getURIFromHeader(String header) {
        int from = header.indexOf(" ") + 1;
        int to = header.indexOf(" ", from);
        String uri = header.substring(from, to);
        int paramIndex = uri.indexOf("?");
        if (paramIndex != -1) {
            uri = uri.substring(0, paramIndex);
        }
        return DEFAULT_FILES_DIR + uri;
    }

    private int send(String url) throws IOException {
        InputStream strm = HttpServer.class.getResourceAsStream(url);
        int code = (strm != null) ? 200 : 404;
        String header = getHeader(code);
        PrintStream answer = new PrintStream(outStr, true, "UTF-8");
        answer.print(header);
        if (code == 200) {
            int count;
            byte[] buffer = new byte[1024];
            while((count = strm.read(buffer)) != -1) {
                outStr.write(buffer, 0, count);
            }
            strm.close();
        }
        return code;
    }

    private String getHeader(int code) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
        buffer.append("Date: " + new Date().toString() + "\n");
        buffer.append("Accept-Ranges: none\n");
//        buffer.append("Content-Type: " + contentType + "\n");
        buffer.append("\n");
        return buffer.toString();
    }

    private String getAnswer(int code) {
        switch (code) {
            case 200:
                return "OK";
            case 404:
                return "Not Found";
            default:
                return "Internal Server Error";
        }
    }


/*    private void readInputHeaders() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inStr));
        while(true) {
            String s = br.readLine();
            if(s == null || s.trim().length() == 0) {
                break;
            }
        }
    }*/

    private String writeResponse(String s) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        outStr.write(result.getBytes());
        outStr.flush();
        return result;
    }

}
