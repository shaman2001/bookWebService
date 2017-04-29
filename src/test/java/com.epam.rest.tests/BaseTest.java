package com.epam.rest.tests;

import com.epam.rest.entity.Book;
import com.epam.rest.helper.PropertyLoader;
import com.epam.rest.tests.helper.JsonHelper;
import com.jayway.restassured.RestAssured;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;

import static com.jayway.restassured.RestAssured.given;


public class BaseTest {

    public static final Logger LOG = LogManager.getLogger(BaseTest.class);

    public static Book existingBook;

    @BeforeClass
    public static void setUp() {
        String port = PropertyLoader.getProperty("server.port");
        String host = PropertyLoader.getProperty("server.address");
        String base = PropertyLoader.getProperty("server.base");
        if (port == null) {
            port = "8080";
        }
        RestAssured.port = Integer.valueOf(port);

        if (host == null) {
            host = "http://localhost";
        }
        RestAssured.baseURI = host;

        if (base == null) {
            base = "/book";
        }
        RestAssured.basePath = base;

        //set existingBook field;
        String respStr = given().when().get().asString();
        Book[] storedBook = JsonHelper.stringToBookArray(respStr);
        if (storedBook.length > 0) {
            existingBook = storedBook[0];
        } else {
            LOG.error("There is not a any book on the bookshelf");
        }
    }


}
