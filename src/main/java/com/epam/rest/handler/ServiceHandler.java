package com.epam.rest.handler;


import com.epam.rest.entity.Book;
import com.epam.rest.entity.BookShelf;

import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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
                    respHnd.setSelectBooks(BookShelf.getBook(rqstHnd.getParams()));
                    respHnd.setProcResult(true);
                    break;
                case METHOD_DELETE:
                    if (rqstHnd.getParams()!= null && Integer.parseInt(rqstHnd.getParam(PARAM_ID)) > 0) {
                        respHnd.setProcResult(BookShelf.delBook(Integer.parseInt(rqstHnd.getParam(PARAM_ID))));
                    } else {
                        respHnd.setProcResult(false);
                    }
                    break;
                case METHOD_PUT:
                    JSONParser jParser = new JSONParser();
                    try {
                        JSONObject jObj = (JSONObject) jParser.parse(rqstHnd.getBody());
                        Book newBook = new Book.BookBuilder(((Number)jObj.get(PARAM_ID)).intValue(),
                                (String)jObj.get(PARAM_NAME))
                                .setAuthor((String)jObj.get(PARAM_AUTHOR))
                                .setGenre((String)jObj.get(PARAM_GENRE))
                                .setYearOfIssue(((Number)jObj.get(PARAM_Y_OF_ISSUE)).intValue())
                                .setLink((String)jObj.get(PARAM_LINK)).build();
                        respHnd.setProcResult(BookShelf.addBook(newBook));
                    } catch (ParseException e) {
                        System.err.println(e.getMessage());
                        respHnd.setProcResult(false);
                    }
                    break;
                default:
                    respHnd.setSelectBooks(BookShelf.getBook(rqstHnd.getParams()));
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
