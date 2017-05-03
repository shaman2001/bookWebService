package com.epam.rest.tests.rest_assured;

import com.epam.rest.tests.helper.QueryBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assume.assumeNotNull;

public class DelBookTests extends BaseTest {

    private static boolean isBookDeleted;

    @Test
    public void delExistingBook() {
        isBookDeleted = false;
        assumeNotNull(existingBook);
        LOG.info(existingBook);
        String delId = new QueryBuilder().setId(existingBook.getId()).buildEnc();
        given().when().delete(delId).then().statusCode(200);
        String getStr = new QueryBuilder().setId(existingBook.getId()).buildEnc();
        Response getResp = given().when().get(getStr);
        if (getResp.then().extract().statusCode()==404) {
            isBookDeleted = true;
        }
    }

    @Test
    public void delNotExistingBook() {
        int notExistedId = 999;
        String delId = new QueryBuilder().setId(notExistedId).buildEnc();
        given().when().delete(delId).then().statusCode(404);
    }

    @AfterClass
    public static void addDeletedBook() {
        if (isBookDeleted) {
            given().contentType(ContentType.JSON).body(existingBook).when().put().then().statusCode(201);
        }
    }

}
