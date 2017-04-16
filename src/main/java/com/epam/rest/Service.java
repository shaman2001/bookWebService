package com.epam.rest;


public class Service {

    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;
    private BookShelf bookShelf;

    public Service(RequestHandler requestHandler, ResponseHandler responseHandler, BookShelf bookShelf) {
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
        this.bookShelf = bookShelf;
    }

    public void getRequest () {
        RequestHandler request = new RequestHandler(reader);

    }

    public void updateData () {

    }

    public void sendResponse () {

    }
}
