package com.epam.rest.tests.helper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static com.epam.rest.constants.CommonConstants.*;


public class StrCodec {

    public static String encode (String str) {
        return str.replaceAll(" ", "%20")
                .replaceAll("/", "%2F")
                .replaceAll(":","%3A");
    }

    public static String encodeWithJS(String str) {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        String result = null;
        try {
            result = (String)engine.eval("encodeURIComponent('" + str + "')");
        } catch (ScriptException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public static String getParamStr (String param, String val) {
        return param + EQUAL_SIGN + val;
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
