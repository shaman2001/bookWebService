package com.epam.rest;


import com.epam.rest.entity.Book;

import java.io.IOException;
import java.util.ArrayList;

import static com.epam.rest.constants.CommonConstants.*;

public class Service {


    private RequestHandler rqstHnd;
    private ResponseHandler respHnd;


    public Service(RequestHandler rqstHnd, ResponseHandler respHnd) {
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
        Integer id;
        if (rqstHnd.getParsingCode() == 200) {
            switch (rqstHnd.getMethod()) {
                case METHOD_GET:
                    respHnd.setSelectBooks(BookShelf.getBook());
                    respHnd.setProcResult(true);
                    break;
                case METHOD_DELETE:
                    if (rqstHnd.getParams()!= null)
                        respHnd.setProcResult(BookShelf.delBook(Integer.parseInt(rqstHnd.getParam(PARAM_ID))));
                    break;
                case METHOD_POST:
                    if (rqstHnd.getParams() != null) {
                        Book newBook = new Book.BookBuilder(Integer.parseInt(rqstHnd.getParam(PARAM_ID)),
                                rqstHnd.getParam(PARAM_NAME))
                                .setAuthor(rqstHnd.getParam(PARAM_AUTHOR))
                                .setGenre(rqstHnd.getParam(PARAM_GENRE))
                                .setYearOfIssue(Integer.parseInt(rqstHnd.getParam(PARAM_Y_OF_ISSUE)))
                                .setLocalLink(rqstHnd.getParam(PARAM_LINK)).build();
                        respHnd.setProcResult(BookShelf.addBook(newBook));
                    }
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
