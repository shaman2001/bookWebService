package com.epam.rest.tests.rest_template;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;


public class BaseTemplateTest {

    final static String BASE_URL = "http://localhost:8080/book";
    public RestTemplate rest = new RestTemplate();
    public static final Logger LOG = LogManager.getLogger(BaseTemplateTest.class);


    public static String getMethodName() {
        return new Throwable().getStackTrace()[1].getMethodName();
    }


}
