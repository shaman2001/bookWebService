package com.epam.rest.handler;

import static com.epam.rest.constants.CommonConstants.*;

import com.epam.rest.entity.Book;
import com.epam.rest.entity.BookShelf;
import com.epam.rest.entity.Response;
import com.epam.rest.helper.DateHelper;
import org.apache.http.ProtocolVersion;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ResponseHandler {

    private final static String CONTENT_TYPE_JSON = "application/json";
    private final static String CONTENT_TYPE_HTML = "text/html; charset=utf-8";

    private RequestHandler requestHnd;
    private Response response;
    private OutputStream outputStream;
    private ArrayList<Book> selectBooks;
    private boolean procResult;


    public void setSelectBooks(ArrayList<Book> selectBooks) {
        this.selectBooks = selectBooks;
    }

    public void setProcResult(boolean procResult) {
        this.procResult = procResult;
    }

    public ResponseHandler (RequestHandler rqst, OutputStream outStr) {
        this.requestHnd = rqst;
        this.outputStream = outStr;
        this.selectBooks = new ArrayList<>();

    }

    public Response getResponse() {
        return this.response;
    }

    public void prepareResponse() {
        Integer code = 500;
        if (procResult) {
            code = requestHnd.getParsingCode();
        }
        ProtocolVersion httpVerStr = this.requestHnd.getProtocolVersion();
        this.response = new Response(httpVerStr, code);
        SetCommonHeaders();
        if (this.response.getStatusCode() != 200) {
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
    }

    private void prepareErrorResponsePart() {
        this.response.setContentType(CONTENT_TYPE_HTML);
        this.response.setBody(String.format(ERROR_RESPONSE_HTML, this.response.getStatusCode()));
        this.response.setContentLength(String.valueOf(response.getBody().length()));
    }

    private void prepareGetPart() {
        this.response.setContentType(CONTENT_TYPE_JSON);
        this.response.setBody(JSONArray.toJSONString(selectBooks));
        this.response.setBody(JSONArray.toJSONString(BookShelf.getBook()));
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
        StringBuilder responseStr = new StringBuilder();
        responseStr.append(response.getStatusLine()).append(LINE_SEPARATOR);
        for (HashMap.Entry<String, String> record: response.getHeaders().entrySet()) {
            responseStr.append(record.getKey()).append(COLON_SPACE).append(record.getValue()).append(LINE_SEPARATOR);
        }
        responseStr.append(LINE_SEPARATOR);
        //responseStr.append(response.getBody());
        System.out.println(responseStr);
/*        String lineSep = System.lineSeparator();
        StringBuilder responseStr = new StringBuilder();
        responseStr.append(response.getStatusLine()).append(LINE_SEPARATOR);
        responseStr.append(DATE_STR).append(COLON_SPACE).append(response.getDate()).append(LINE_SEPARATOR);
        responseStr.append(CONTENT_TYPE).append(COLON_SPACE).append(response.getContentType()).append(LINE_SEPARATOR);
        responseStr.append(LINE_SEPARATOR);*/

        responseStr.append(response.getBody());


        this.outputStream.write(responseStr.toString().getBytes()); //check for error this place
        this.outputStream.flush();
//        this.outputStream.close();
    }


}
