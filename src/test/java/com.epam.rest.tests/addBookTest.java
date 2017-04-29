package com.epam.rest.tests;

import com.epam.rest.entity.Book;
import com.epam.rest.tests.helper.JsonHelper;
import com.epam.rest.tests.helper.QueryBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.containsString;


@RunWith(Parameterized.class)
public class addBookTest extends BaseTest {

    private final static String FTP_BASE = "ftp://127.0.0.1:25/pub/";

    private Integer p_id;
    private String p_name;
    private String p_genre;
    private String p_author;
    private Integer p_yearOfIssue;
    private String p_link;

    public addBookTest(Integer id, String name, String genre, String author,
                       Integer year_of_issue, String link) {
        this.p_id = id;
        this.p_name = name;
        this.p_genre = genre;
        this.p_author = author;
        this.p_yearOfIssue = year_of_issue;
        this.p_link = link;
    }


    @Test
    public void addBookTest() {
        Book newBook = new Book.BookBuilder(p_id, p_name).setGenre(p_genre)
                                .setAuthor(p_author).setYearOfIssue(p_yearOfIssue)
                                .setLink(p_link).build();
        given().contentType(ContentType.JSON).body(newBook).when().put().then().statusCode(200);
        String getStr = new QueryBuilder().setId(p_id).buildEnc();
        String respStr = given().when().get(getStr).asString();
        Book[] storedBook = JsonHelper.stringToBookArray(respStr);
        Assert.assertEquals(storedBook[0], newBook);
    }


    @Parameterized.Parameters(name = "{index}: Id:{0}, Book name:{1}, Genre:{2}, Author:{3}, Year:{4}, Link:{5}")
    public static List<Object[]> IdData() {
        return Arrays.asList(new Object[][] {
                { 20, "The oldman and the sea", "novel", "Ernest Hamingway", 1952, FTP_BASE + "the_oldman_and_the_sea.txt"},
                { 21, "Three Comrades", "novel", "Erich Maria Remarque", 1938, FTP_BASE + "three_comrades.txt"},
                { 22, "Eugene Onegin", "poem", "Alexander Pushkin", 1825, FTP_BASE + "eugene_onegin.txt" }
        });
    }
}
