package com.epam.rest.handler;


import com.epam.rest.entity.Book;
import com.epam.rest.entity.BookShelf;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import static com.epam.rest.constants.CommonConstants.*;

public class ServiceHandler {

    private static final Logger LOG = LogManager.getLogger(ServiceHandler.class);

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
            LOG.error(e.getMessage());
        }
    }

    public void processData () {
        if (rqstHnd.getParsingCode() == 200) {
            switch (rqstHnd.getMethod()) {
                case METHOD_GET:
                    processGet();
                    break;
                case METHOD_DELETE:
                    processDelete();
                    break;
                case METHOD_PUT:
                    processPut();
                    break;
                default:
                    processGet();
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

    private void processGet() {
        ArrayList<Book> books = BookShelf.getBook(rqstHnd.getParams());
        if (books != null && !books.isEmpty()) {
            respHnd.setSelectedBooks(books);
            respHnd.setProcResultCode(200);
        } else {
            respHnd.setProcResultCode(404);
        }

    }

    private void processPut() {
        JSONParser jParser = new JSONParser();
        try {
            JSONObject jObj = (JSONObject) jParser.parse(rqstHnd.getBody());
            Book newBook = new Book.BookBuilder(((Number)jObj.get(PARAM_ID)).intValue(),
                    (String)jObj.get(PARAM_NAME))
                    .setAuthor((String)jObj.get(PARAM_AUTHOR))
                    .setGenre((String)jObj.get(PARAM_GENRE))
                    .setYearOfIssue(((Number)jObj.get(PARAM_Y_OF_ISSUE)).intValue())
                    .setLink((String)jObj.get(PARAM_LINK)).build();
            if (BookShelf.addBook(newBook)) {
                respHnd.setProcResultCode(200);
            } else {
                respHnd.setProcResultCode(409); //book already exist
            }
        } catch (ParseException | NullPointerException e) {
            LOG.error(e.getMessage());
            respHnd.setProcResultCode(400); //bad request
        }

    }

    private void processDelete() {
        if (rqstHnd.getParams().size() == 1 && Integer.parseInt(rqstHnd.getParam(PARAM_ID)) > 0) {
            if (BookShelf.delBook(Integer.parseInt(rqstHnd.getParam(PARAM_ID)))) {
                respHnd.setProcResultCode(200);
            } else {
                respHnd.setProcResultCode(404); //book with requested id not found
            }
        } else {
            respHnd.setProcResultCode(400); //bad request
        }

    }

}
