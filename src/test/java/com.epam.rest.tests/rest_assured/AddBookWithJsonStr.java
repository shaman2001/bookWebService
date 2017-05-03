package com.epam.rest.tests.rest_assured;

import com.epam.rest.tests.helper.QueryBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.epam.rest.constants.CommonConstants.*;


public class AddBookWithJsonStr extends BaseTest{

    private final static String WRONG_JSON_OBJECT_STR = "{\"id\": 6, \"name\": \"The oldman and the sea\", " +
                "\"genre\": \"novel\", \"author\": \"Ernest Hamingway\", \"year_of_writing\": 1955, " +
                "\"locallink\": \"ftp://localhost:25/pub/the_oldman_and_the_sea.txt\"}";

    private final static String PROPER_JSON_OBJECT_STR = "{\"id\": 6, \"name\": \"The oldman and the sea\", " +
            "\"genre\": \"novel\", \"author\": \"Ernest Hamingway\", \"year_of_issue\": 1955, " +
            "\"link\": \"ftp://localhost:25/pub/the_oldman_and_the_sea.txt\"}";

    private final static int ADDED_BOOK_ID = 6;

    private static boolean addBookResult;

    @Test
    public void addWrongBookStr () {
        int bookCountBefore = Integer.parseInt(given().when().get().then().extract().header(BOOKS_COUNT));
        given().contentType(ContentType.JSON).body(WRONG_JSON_OBJECT_STR).when().put().then().statusCode(400);
        int bookCountAfter = Integer.parseInt(given().when().get().then().extract().header(BOOKS_COUNT));
        Assert.assertTrue(bookCountBefore == bookCountAfter);
    }

    @Test
    public void addProperBookStr () {
        int bookCountBefore = Integer.parseInt(given().when().get().then().extract().header(BOOKS_COUNT));
        given().contentType(ContentType.JSON).body(PROPER_JSON_OBJECT_STR).when().put().then().statusCode(201);
        Response getResp = given().when().get();
        int bookCountAfter = Integer.parseInt(getResp.then().extract().header(BOOKS_COUNT));
        addBookResult = bookCountBefore != bookCountAfter;
        Assert.assertTrue(addBookResult);
    }

    @AfterClass
    public static void deleteChanges() {
        if (addBookResult) {
            String delId = new QueryBuilder().setId(ADDED_BOOK_ID).buildEnc();
            given().when().delete(delId);
        }
    }


}
