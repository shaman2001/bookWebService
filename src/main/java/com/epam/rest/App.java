package com.epam.rest;

import com.epam.rest.helper.PropertyLoader;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

    private static final Logger LOG = LogManager.getLogger(App.class);

    public static void main(String args[] ) throws IOException {
        final Integer PORT = Integer.parseInt(PropertyLoader.getProperty("server.port"));
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
            LOG.info("Listening for connection on port " + server.getLocalPort());
        } catch (IOException e) {
            LOG.error("Port" + PORT + "is blocked");
            System.exit(-1);
        }
        while (true) {
            try {
                Socket clientSocket = server.accept();
                SocketHandler sockHnd = new SocketHandler(clientSocket);
                new Thread(sockHnd).start();
            } catch (IOException e) {
                LOG.error("Failed to establish connection on port" + PORT);
                LOG.error(e);
                System.exit(-1);
            }
        }
    }

}
