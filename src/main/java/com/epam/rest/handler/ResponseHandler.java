package com.epam.rest.handler;

import static com.epam.rest.constants.CommonConstants.*;

import com.epam.rest.entity.Book;
import com.epam.rest.entity.Response;
import com.epam.rest.helper.DateHelper;
import com.google.gson.Gson;
import org.apache.http.ProtocolVersion;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class ResponseHandler {

    private final static String CONTENT_TYPE_JSON = "application/json";
    private final static String CONTENT_TYPE_HTML = "text/html; charset=utf-8";

    private RequestHandler requestHnd;
    private Response response;
    private OutputStream outputStream;
    private ArrayList<Book> selectedBooks;
    private int procResultCode;


    public void setSelectedBooks(ArrayList<Book> selectedBooks) {
        this.selectedBooks = selectedBooks;
    }

    public void setProcResultCode(int procResultCode) {
        this.procResultCode = procResultCode;
    }

    public ResponseHandler (RequestHandler rqst, OutputStream outStr) {
        this.requestHnd = rqst;
        this.outputStream = outStr;
        this.selectedBooks = new ArrayList<>();

    }

    public Response getResponse() {
        return this.response;
    }

    public void prepareResponse() {
        int parsCode = requestHnd.getParsingCode();
        int code;
        if (parsCode>=200 && parsCode<=299) {
            code = procResultCode;
        } else {
            code = parsCode;
        }
        ProtocolVersion httpVerStr = this.requestHnd.getProtocolVersion();
        this.response = new Response(httpVerStr, code);
        SetCommonHeaders();
        if (code >= 400) {
            prepareErrorResponsePart();
            return;
        }
        switch (this.requestHnd.getMethod()) {
            case METHOD_GET:
                prepareGetPart();
                break;
            case METHOD_POST:
                preparePostPart();
                break;
            case METHOD_PUT:
                preparePutPart();
                break;
            case METHOD_DELETE:
                prepareDeletePart();
                break;
            default:
                prepareGetPart();
        }
        this.response.setConnection("Closed");

    }

    private void SetCommonHeaders() {
        this.response.setDate(DateHelper.getDateStr());
        this.response.setServer(SERVER_STR);
        //this.response.setContentEncoding("gzip"); //falls all the responses. for checking
        this.response.setContentLanguage("en");
        this.response.setBooksCountHeader();

    }

    private void prepareErrorResponsePart() {
        this.response.setContentType(CONTENT_TYPE_HTML);
        this.response.setBody(String.format(ERROR_RESPONSE_HTML, this.response.getStatusCode()));
        this.response.setContentLength(String.valueOf(response.getBody().length()));
    }

    private void prepareGetPart() {
        this.response.setContentType(CONTENT_TYPE_JSON);
        this.response.setBody(new Gson().toJson(selectedBooks));
        this.response.setContentLength(String.valueOf(response.getBody().length()));
    }

    private void preparePostPart() {

    }
    private void preparePutPart() {
        this.response.setContentType(CONTENT_TYPE_HTML);
        this.response.setBody(PUT_RESPONSE_HTML);
        this.response.setContentLength(String.valueOf(response.getBody().length()));

    }

    private void prepareDeletePart() {
        this.response.setContentType(CONTENT_TYPE_HTML);
        this.response.setBody(DELETE_RESPONSE_HTML);
        this.response.setContentLength(String.valueOf(response.getBody().length()));
    }

    public void writeResponse() throws IOException {
        StringBuffer responseStr = new StringBuffer();
        responseStr.append(response.getStatusLine()).append(LINE_SEPARATOR);
        for (HashMap.Entry<String, String> record: response.getHeaders().entrySet()) {
            responseStr.append(record.getKey()).append(COLON_SPACE).append(record.getValue()).append(LINE_SEPARATOR);
        }
        responseStr.append(LINE_SEPARATOR).append(response.getBody());
        System.out.println(responseStr);

        System.out.println();
        System.out.println();

        this.outputStream.write(responseStr.toString().getBytes());
        this.outputStream.flush();
//        this.outputStream.close();
    }


}
