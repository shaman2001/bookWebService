package com.epam.rest;


import com.epam.rest.entity.Book;

import java.io.IOException;
import java.util.ArrayList;

import static com.epam.rest.constants.CommonConstants.*;

public class Service {


    private RequestHandler rqstHnd;
    private ResponseHandler respHnd;
    private ArrayList<Book> selectBooks;
    private boolean procResult;


    public Service(RequestHandler rqstHnd, ResponseHandler respHnd) {
        this.rqstHnd = rqstHnd;
        this.respHnd = respHnd;
        this.selectBooks = new ArrayList<>();
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
                    selectBooks = BookShelf.getBook();
                    break;
                case METHOD_DELETE:
                    if (rqstHnd.getParams()!= null)
                        procResult = BookShelf.delBook(Integer.parseInt(rqstHnd.getParam(PARAM_ID)));
                    break;
                case METHOD_POST:
                    if (rqstHnd.getParams() != null) {
                        Book newBook = new Book.BookBuilder(Integer.parseInt(rqstHnd.getParam(PARAM_ID)),
                                rqstHnd.getParam(PARAM_NAME))
                                .setAuthor(rqstHnd.getParam(PARAM_AUTHOR))
                                .setGenre(rqstHnd.getParam(PARAM_GENRE))
                                .setYearOfIssue(Integer.parseInt(rqstHnd.getParam(PARAM_Y_OF_ISSUE)))
                                .setLocalLink(rqstHnd.getParam(PARAM_LINK)).build();
                        procResult = BookShelf.addBook(newBook);
                    }
                default:
                    selectBooks = BookShelf.getBook();
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
