package com.epam.rest.tests.helper;

import com.epam.rest.entity.Book;
import com.google.gson.Gson;

public class JsonHelper {

    public static Book[] stringToBookArray (String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, Book[].class);
    }
}
