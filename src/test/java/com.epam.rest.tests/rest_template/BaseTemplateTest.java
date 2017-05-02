package com.epam.rest.tests.rest_template;

import org.junit.BeforeClass;
import org.springframework.web.client.RestTemplate;


public class BaseTemplateTest {

    final static String BASE_URL = "http://localhost:8080/book";
    public RestTemplate rest = new RestTemplate();

    @BeforeClass
    public static void setUp() {


    }




}
