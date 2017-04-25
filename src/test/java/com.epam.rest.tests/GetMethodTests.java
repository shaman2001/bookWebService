package com.epam.rest.tests;

import com.epam.rest.tests.helper.StrCodec;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import java.io.UnsupportedEncodingException;
import static com.epam.rest.constants.CommonConstants.*;


/**
 * Created by shaman on 23.04.17.
 */
public class GetMethodTests extends BaseTest {


    @Test
    public void basicResponseTest() {
        given().when().get().then().statusCode(200);
    }

    @Test
    public void getBookById() throws UnsupportedEncodingException {
        String newBookParams = QUESTION_SIGN + PARAM_ID;
        given().when().get(newBookParams).then().statusCode(200).
                body(containsString("The oldman and the sea"));

    }



}
