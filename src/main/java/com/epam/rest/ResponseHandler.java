package com.epam.rest;

import static com.epam.rest.constants.CommonConstants.*;

import com.epam.rest.entity.Book;
import com.epam.rest.helper.DateHelper;
import org.apache.http.ProtocolVersion;
import org.json.simple.JSONArray;

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
        if (this.requestHnd.getMethod().equals(METHOD_GET)) {
            prepareGetPart();
        } else if (this.requestHnd.getMethod().equals(METHOD_POST)) {
            preparePostPart();
        } else if (this.requestHnd.getMethod().equals(METHOD_PUT)) {
            preparePutPart();
        } else if (this.requestHnd.getMethod().equals(METHOD_DELETE)) {
            prepareDeletePart();
        }

    }

    private void SetCommonHeaders() {
        this.response.setDate(DateHelper.getDateStr());
        this.response.setServer(SERVER_STR);
        this.response.setContentEncoding("gzip");
        this.response.setContentLanguage("en");
        this.response.setConnection("Closed");
    }

    private void prepareErrorResponsePart() {
        this.response.setContentType(CONTENT_TYPE_HTML);
        this.response.setBody(String.format(ERROR_RESPONSE_HTML, this.response.getStatusCode()));
        this.response.setContentLength(String.valueOf(response.getBody().length()));
    }

    private void prepareGetPart() {
        this.response.setContentType(CONTENT_TYPE_JSON);
        if (this.requestHnd.getParams() == null) {
            this.response.setBody(JSONArray.toJSONString(selectBooks));
        }
        this.response.setContentLength(String.valueOf(response.getBody().length()));
    }

    private void preparePostPart() {

    }
    private void preparePutPart() {

    }

    private void prepareDeletePart() {


    }

    public void writeResponse() throws IOException {
        StringBuilder responseStr = new StringBuilder();
        responseStr.append(response.getStatusLine());
        responseStr.append(LINE_SEPARATOR);
        for (HashMap.Entry<String, String> record: response.getHeaders().entrySet()) {
            responseStr.append(record.getKey()).append(COLON_SPACE).append(record.getValue()).append(LINE_SEPARATOR);
        }
        responseStr.append(LINE_SEPARATOR).append(response.getBody());
        this.outputStream.write(responseStr.toString().getBytes());
    }


}
