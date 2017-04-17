package com.epam.rest;


import com.epam.rest.entity.Book;
import org.json.simple.JSONArray;
import com.epam.rest.BookShelf;

import java.io.IOException;
import java.util.ArrayList;

import static com.epam.rest.constants.CommonConstants.*;

public class Service {


    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;

    public Service(RequestHandler requestHandler, ResponseHandler responseHandler) {
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
    }

    public void getRequest () {
        try {
            this.requestHandler.parseRequest();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void processData () {
        if (requestHandler.getParsingCode() == 200) {
            if (requestHandler.getMethod().equals(METHOD_GET)) {
                if (requestHandler.getParams() == null) {
                    ArrayList<Book> books = BookShelf.getBook();
                    String jsonStr = JSONArray.toJSONString(books);
                    responseHandler.getResponse().setBody(jsonStr);
                } else if (requestHandler.getParam(PARAM_ID)!= null) {
                    Integer id = Integer.parseInt(requestHandler.getParam(PARAM_ID));
                    responseHandler.getResponse().setBody(JSONArray.toJSONString(BookShelf.getBook(id)));
                }
            } else if (requestHandler.getMethod().equals(METHOD_DELETE)) {

            }
        }

    }

    public void sendResponse () {
        try {
            responseHandler.prepareResponse();
            responseHandler.writeResponse();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
