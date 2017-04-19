package com.epam.rest.handler;


import com.epam.rest.entity.Book;
import com.epam.rest.entity.BookShelf;
import com.epam.rest.handler.RequestHandler;
import com.epam.rest.handler.ResponseHandler;

import java.io.IOException;
import java.util.HashMap;

import static com.epam.rest.constants.CommonConstants.*;

public class ServiceHandler {

    private RequestHandler rqstHnd;
    private ResponseHandler respHnd;

    public ServiceHandler(RequestHandler rqstHnd, ResponseHandler respHnd) {
        this.rqstHnd = rqstHnd;
        this.respHnd = respHnd;
    }

    public void getRequest () {
        try {
            this.rqstHnd.parseRequest();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void processData () {
        if (rqstHnd.getParsingCode() == 200) {
            switch (rqstHnd.getMethod()) {
                case METHOD_GET:
                    HashMap query = rqstHnd.getParams();
                    respHnd.setSelectBooks(BookShelf.getBook(query));
                    respHnd.setProcResult(true);
                    break;
                case METHOD_DELETE:
                    if (rqstHnd.getParams()!= null && Integer.parseInt(rqstHnd.getParam(PARAM_ID)) > 0) {
                        respHnd.setProcResult(BookShelf.delBook(Integer.parseInt(rqstHnd.getParam(PARAM_ID))));
                    } else {
                        respHnd.setProcResult(false);
                    }
                case METHOD_PUT:
                    if (rqstHnd.getParams() != null) {
                        Book newBook = new Book.BookBuilder(Integer.parseInt(rqstHnd.getParam(PARAM_ID)),
                                rqstHnd.getParam(PARAM_NAME))
                                .setAuthor(rqstHnd.getParam(PARAM_AUTHOR))
                                .setGenre(rqstHnd.getParam(PARAM_GENRE))
                                .setYearOfIssue(Integer.parseInt(rqstHnd.getParam(PARAM_Y_OF_ISSUE)))
                                .setLocalLink(rqstHnd.getParam(PARAM_LINK)).build();
                        respHnd.setProcResult(BookShelf.addBook(newBook));
                    } else {
                        respHnd.setProcResult(false);
                    }
                    break;
                default:
                    respHnd.setSelectBooks(BookShelf.getBook());
                    respHnd.setProcResult(true);
            }
         }

    }

    public void sendResponse () {
        try {
            respHnd.prepareResponse();
            respHnd.writeResponse();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}