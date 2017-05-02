package com.epam.rest.tests.rest_template;

import com.epam.rest.entity.Book;
import org.junit.Assert;
import org.junit.Test;

public class getAllBookTest extends BaseTemplateTest {


    @Test
    public void getAllBooks() {
        Book[] books = rest.getForObject(BASE_URL,Book[].class);
        Assert.assertEquals(books.length, 5);
    }
}
