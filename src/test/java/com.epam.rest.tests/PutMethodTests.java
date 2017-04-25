package com.epam.rest.tests;

import com.epam.rest.tests.helper.StrCodec;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by shaman on 25.04.17.
 */
public class PutMethodTests extends BaseTest {

    @Test
    public void addOneBookByURLParams() throws UnsupportedEncodingException {
        String newBookParams = "?id=6&name=The oldman and the sea&genre=novel&author=Ernest Hamingway&" +
                "year_of_issue=1960&link=ftp://127.0.0.1:25/pub/the_oldman_and_the_sea.txt";
        String newEncBook = StrCodec.encode(newBookParams);
        given().when().get(newEncBook).then().statusCode(200).
                body(containsString("The oldman and the sea"));

    }
}
