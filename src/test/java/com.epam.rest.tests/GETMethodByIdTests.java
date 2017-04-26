package com.epam.rest.tests;

import com.epam.rest.tests.helper.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class GETMethodByIdTests extends BaseTest {

    public Integer p_id;
    public String p_name;

    public GETMethodByIdTests(Integer id, String name) {
        this.p_id = id;
        this.p_name = name;
    }

    @Test
    public void basicResponseTest() {
        given().when().get().then().statusCode(200);
    }

    @Test
    public void getBookById() {
        String newGetQuery = new QueryBuilder().setId(p_id).build();
        given().when().get(newGetQuery).then().statusCode(200).
                body(containsString(p_name));

    }

    @Parameterized.Parameters(name = "{index}: Id: {0}, Book name: {1}")
    public static List<Object[]> IdData() {
        return Arrays.asList(new Object[][] {
                { 1, "Burning daylight" },
                { 2, "For whom the bell tolls" },
                { 3, "The Master and Margarita" },
                { 4, "Borodino" },
                { 5, "Morals" }
        });
    }

}
