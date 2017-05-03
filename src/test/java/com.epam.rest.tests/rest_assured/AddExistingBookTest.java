package com.epam.rest.tests.rest_assured;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assume.assumeNotNull;

public class AddExistingBookTest extends BaseTest{


    @Test
    public void addBookExistingBook() {
        assumeNotNull(existingBook);
        given().contentType(ContentType.JSON).body(existingBook).when().put().then().statusCode(409);
    }

}
