package com.epam.rest.tests;

import com.epam.rest.helper.PropertyLoader;
import com.jayway.restassured.RestAssured;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;


public class BaseTest {

    private static final Logger LOG = LogManager.getLogger(BaseTest.class);

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
    }


}
