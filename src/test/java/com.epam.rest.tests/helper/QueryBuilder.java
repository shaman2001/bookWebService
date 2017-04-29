package com.epam.rest.tests.helper;

import java.lang.reflect.Field;

import static com.epam.rest.constants.CommonConstants.*;
import static com.epam.rest.tests.helper.StrCodec.*;

public class QueryBuilder {

    private Integer id;
    private String name;
    private String genre;
    private String author;
    private Integer year_of_issue;
    private String link;


    public QueryBuilder() {
        this.id = 0;
        this.name = null;
        this.genre = null;
        this.author = null;
        this. year_of_issue = 0;
        this.link = null;
    }

    public QueryBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public QueryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public QueryBuilder setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public QueryBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public QueryBuilder setYear_of_issue(Integer year_of_issue) {
        this.year_of_issue = year_of_issue;
        return this;
    }

    public QueryBuilder setLink(String link) {
        this.link = link;
        return this;
    }

    public String buildEnc() {
        StringBuffer resBuff = new StringBuffer();
        resBuff.append(QUESTION_SIGN);
        if (this.id != 0) {
            resBuff.append(getParamStr(PARAM_ID, this.id.toString())).append(AMPERSAND_SIGN);
        }
        if (this.name != null) {
            resBuff.append(getParamStr(PARAM_NAME, encodeWithJS(this.name))).append(AMPERSAND_SIGN);
        }
        if (this.genre != null) {
            resBuff.append(getParamStr(PARAM_GENRE, encodeWithJS(this.genre))).append(AMPERSAND_SIGN);
        }
        if (this.author != null) {
            resBuff.append(getParamStr(PARAM_AUTHOR, encodeWithJS(this.author))).append(AMPERSAND_SIGN);
        }
        if (this.year_of_issue != 0) {
            resBuff.append(getParamStr(PARAM_Y_OF_ISSUE, this.year_of_issue.toString())).append(AMPERSAND_SIGN);
        }
        if (this.link != null ) {
            resBuff.append(getParamStr(PARAM_LINK, encodeWithJS(this.link))).append(AMPERSAND_SIGN);
        }
        if (resBuff.length()>0) {
            resBuff.setLength(resBuff.length() - 1); //remove ampersand in the end;
        }

        /*for (Field field: this.getClass().getDeclaredFields()) {
            if (field.get() != 0 && field != null) {

            }
        }*/
        return resBuff.toString();
    }
}
