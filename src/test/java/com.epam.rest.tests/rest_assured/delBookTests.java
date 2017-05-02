package com.epam.rest.tests.rest_assured;

import com.epam.rest.tests.helper.QueryBuilder;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assume.assumeNotNull;

public class delBookTests extends BaseTest {


    @Test
    public void delExistingBook() {
        assumeNotNull(existingBook);
        LOG.info(existingBook);
        String delId = new QueryBuilder().setId(existingBook.getId()).buildEnc();
        given().when().delete(delId).then().statusCode(200);
    }


    @Test
    public void delNotExistingBook() {
        int notExistedId = 999;
        String delId = new QueryBuilder().setId(notExistedId).buildEnc();
        given().when().delete(delId).then().statusCode(500);
    }
}
