package com.epam.rest.tests;

import com.epam.rest.entity.Book;
import com.epam.rest.tests.helper.JsonHelper;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static com.epam.rest.constants.CommonConstants.BOOKS_COUNT;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assume.assumeNotNull;

public class getAllBooksTest extends BaseTest {

    @Test
    public void getAllBooks() {
        assumeNotNull(existingBook);
        Response getResp = given().when().get();
        String jsonRespStr = getResp.asString();
        Book[] storedBook = JsonHelper.stringToBookArray(jsonRespStr);
        getResp.then().statusCode(200);
        int booksCount = Integer.parseInt(getResp.then().extract().header(BOOKS_COUNT));
        Assert.assertTrue(storedBook.length == booksCount);
    }


}
