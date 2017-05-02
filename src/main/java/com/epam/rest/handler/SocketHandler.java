package com.epam.rest.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class SocketHandler implements Runnable {

    private static final Logger LOG = LogManager.getLogger(SocketHandler.class);

    private Socket sock;
    private OutputStream outStr;
    private InputStream inStr;


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
            LOG.error(e.getMessage());
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }

    }


}
