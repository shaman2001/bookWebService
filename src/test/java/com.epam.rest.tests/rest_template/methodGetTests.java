package com.epam.rest.tests.rest_template;

import com.epam.rest.entity.Book;
import com.epam.rest.tests.helper.JsonHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static com.epam.rest.constants.CommonConstants.*;

public class methodGetTests extends BaseTemplateTest {

    private final static String AUTHOR = "Rick Riordan";
    private final static String GENRE = "fantasy";
    private final static Integer YEAR_OF_ISSUE = 2005;


    @Test
    public void getWithoutParamCorrectStatusCodeTest() {
        LOG.info("Running test method: " + getMethodName());
        LOG.info("Requested URL: " + BASE_URL);
        ResponseEntity<String> response = rest.getForEntity(BASE_URL, String.class);
        Assert.assertTrue(response.getStatusCodeValue() == 200);
    }

    @Test
    public void headerEqualsBooksAmountTest() {
        LOG.info("Running test method: " + getMethodName());
        LOG.info("Requested URL: " + BASE_URL);
        Book[] books = rest.getForObject(BASE_URL, Book[].class);
        ResponseEntity<String> response = rest.getForEntity(BASE_URL, String.class);
        int bookAmount = Integer.parseInt(response.getHeaders().get(BOOKS_COUNT).get(0));
        Assert.assertEquals(bookAmount, books.length);
    }

    @Test
    public void getWithIdParamBodyTest() {
        LOG.info("Running test method: " + getMethodName());
        UriComponentsBuilder paramStr = UriComponentsBuilder.newInstance();
        paramStr.queryParam(PARAM_ID, "1");
        String url = BASE_URL + paramStr.build();
        LOG.info("Requested URL: " + url);
        ResponseEntity<String> response = rest.getForEntity(url, String.class);
        Book[] books = JsonHelper.stringToBookArray(response.getBody());
        Assert.assertTrue(books.length == 1);
    }

    @Test
    public void getWithTwoParamsTest() {
        LOG.info("Running test method: " + getMethodName());
        UriComponentsBuilder paramStr = UriComponentsBuilder.newInstance();
        paramStr.queryParam(PARAM_AUTHOR, AUTHOR);
        paramStr.queryParam(PARAM_Y_OF_ISSUE, YEAR_OF_ISSUE);
        String url = BASE_URL + paramStr.build();
        LOG.info("Requested URL: " + url);
        ResponseEntity<String> response = rest.getForEntity(url, String.class);
        Book[] books = JsonHelper.stringToBookArray(response.getBody());
        Assert.assertTrue(books[0].getAuthor().equals(AUTHOR));
        Assert.assertTrue(books[0].getYearOfIssue().equals(YEAR_OF_ISSUE));
    }





}
