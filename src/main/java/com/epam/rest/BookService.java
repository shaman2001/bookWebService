package com.epam.rest;

import com.epam.rest.entity.Book;
import com.epam.rest.helper.PropertyLoader;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class BookService {

    private static final Logger LOG = LogManager.getLogger(BookService.class);

    long currentId = 123;
    Map<Long, Book> customers = new HashMap<>();

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
