package com.epam.rest.tests.rest_assured;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class OptionMethodTest extends BaseTest {

    @Test
    public void OptionTest() {
        given().when().options().then().statusCode(405);
    }
}
