package com.epam.rest.tests.helper;

import static com.epam.rest.constants.CommonConstants.*;


public class StrCodec {

    public static String encode (String str) {
        return str.replaceAll(" ", "%20")
                .replaceAll("/", "%2F")
                .replaceAll(":","%3A");
    }

    public static String getIdQueryStr (Integer id) {
        return PARAM_ID + EQUAL_SIGN + id.toString();
    }

    public static String getNameQueryStr (String name) {
        return PARAM_NAME + EQUAL_SIGN + name;
    }

    public static String getGenreQueryStr (String genre) {
        return PARAM_GENRE + EQUAL_SIGN + genre;
    }

    public static String getAuthorQueryStr (String author) {
        return PARAM_AUTHOR + EQUAL_SIGN + author;
    }

    public static String getYearOfIssueQueryStr (Integer year) {
        return PARAM_Y_OF_ISSUE + EQUAL_SIGN + year.toString();
    }

    public static String getLinkQueryStr (String link) {
        return PARAM_LINK + EQUAL_SIGN + link;
    }
}
