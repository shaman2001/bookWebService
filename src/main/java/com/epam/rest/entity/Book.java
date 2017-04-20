package com.epam.rest.entity;

import org.json.simple.JSONObject;


public class Book {
    private Integer id;
    private String name;
    private String genre;
    private String author;
    private Integer yearOfIssue;
    private String localLink;

    public Book() {
        this.id = 0;
        this.name = "";
        this.genre = "";
        this.author = "";
        this.yearOfIssue = 0;
        this.localLink = "";
    }

    public Book(Integer p_id, String p_name, String p_genre, String p_author, Integer p_uoissue, String link) {
        this.id = p_id;
        this.name = p_name;
        this.genre = p_genre;
        this.author = p_author;
        this.yearOfIssue = p_uoissue;
        this.localLink = link;
    }

    public Book(BookBuilder bookBuilder) {
        this.id = bookBuilder.id;
        this.name = bookBuilder.name;
        this.genre = bookBuilder.genre;
        this.author = bookBuilder.author;
        this.yearOfIssue = bookBuilder.yearOfIssue;
        this.localLink = bookBuilder.localLink;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYearOfIssue(Integer yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public void setLocalLink(String localLink) {
        this.localLink = localLink;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getYearOfIssue() {
        return yearOfIssue;
    }

    public String getLocalLink() {
        return localLink;
    }

    public static class BookBuilder {
        //required
        private Integer id;
        private String name;
        //optional
        private String genre;
        private String author;
        private Integer yearOfIssue;
        private String localLink;

        public BookBuilder (Integer b_id, String b_name) {
            this.id = b_id;
            this.name = b_name;
            this.genre = "";
            this.author = "";
            this.yearOfIssue = 0;
            this.localLink = "";
        }

        public BookBuilder setGenre (String val) {
            this.genre = val;
            return this;
        }

        public BookBuilder setAuthor(String val) {
            this.author = val;
            return this;
        }

        public BookBuilder setYearOfIssue (Integer val) {
            this.yearOfIssue = val;
            return this;
        }

        public BookBuilder setLocalLink (String val) {
            this.localLink = val;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id", this.id);
        jsonObj.put("name", this.name);
        jsonObj.put("genre", this.genre);
        jsonObj.put("author", this.author);
        jsonObj.put("yearOfIssue", this.yearOfIssue);
        jsonObj.put("localLink", this.localLink);
        return jsonObj;
    }

    public String toString() {
        StringBuffer result = new StringBuffer("Book id:");
        result.append(this.getId().toString());
        result.append("; Name: ");
        result.append(this.getName());
        result.append("; Author: ");
        result.append(this.getAuthor());
        result.append("; Genre: ");
        result.append(this.getGenre());
        result.append("; Year: ");
        result.append(this.getYearOfIssue().toString());
        result.append("; link: ");
        result.append(this.getLocalLink());
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book book = (Book) obj;

        if (!id.equals(book.id)) return false;
        if (!name.equals(book.name)) return false;
        if (!genre.equals(book.genre)) return false;
        if (!author.equals(book.author)) return false;
        if (!yearOfIssue.equals(book.yearOfIssue)) return false;
        return localLink.equals(book.localLink);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + genre.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + yearOfIssue.hashCode();
        result = 31 * result + localLink.hashCode();
        return result;
    }
}
