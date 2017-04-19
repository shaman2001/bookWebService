package com.epam.rest.handler;

import java.io.*;
import java.net.Socket;

public class SocketHandler implements Runnable {


    private Socket sock;
    private OutputStream outStr;
    private InputStream inStr;

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
            ResponseHandler response = new ResponseHandler(request, this.outStr);
            ServiceHandler serviceHandler = new ServiceHandler(request, response);
            serviceHandler.getRequest();
            serviceHandler.processData();
            serviceHandler.sendResponse();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
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
            builder.append(ln).append(System.getProperty("line.separator"));
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


}
