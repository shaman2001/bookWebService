package com.epam.rest.tests;

import com.epam.rest.entity.Book;
import com.epam.rest.tests.helper.JsonHelper;
import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assume.assumeNotNull;

public class addExistingBookTest extends BaseTest{


    @Test
    public void addBookExistingBook() {
        assumeNotNull(existingBook);
        given().contentType(ContentType.JSON).body(existingBook).when().put().then().statusCode(500);
    }

}
