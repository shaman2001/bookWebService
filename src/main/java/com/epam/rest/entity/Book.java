package com.epam.rest.entity;

import org.json.simple.JSONObject;

import java.util.Comparator;
import java.util.HashMap;


public class Book {
    private Integer id;
    private String name;
    private String genre;
    private String author;
    private Integer year_of_issue;
    private String link;

    public Book() {
        this.id = 0;
        this.name = "";
        this.genre = "";
        this.author = "";
        this.year_of_issue = 0;
        this.link = "";
    }

    public Book(Integer p_id, String p_name, String p_genre, String p_author, Integer p_uoissue, String link) {
        this.id = p_id;
        this.name = p_name;
        this.genre = p_genre;
        this.author = p_author;
        this.year_of_issue = p_uoissue;
        this.link = link;
    }

    public Book(BookBuilder bookBuilder) {
        this.id = bookBuilder.id;
        this.name = bookBuilder.name;
        this.genre = bookBuilder.genre;
        this.author = bookBuilder.author;
        this.year_of_issue = bookBuilder.year_of_issue;
        this.link = bookBuilder.link;
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

    public void setYearOfIssue(Integer year_of_issue) {
        this.year_of_issue = year_of_issue;
    }

    public void setLocalLink(String localLink) {
        this.link = localLink;
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
        return year_of_issue;
    }

    public String getLink() {
        return link;
    }

    public static class BookBuilder {
        //required
        private Integer id;
        private String name;
        //optional
        private String genre;
        private String author;
        private Integer year_of_issue;
        private String link;

        public BookBuilder (Integer b_id, String b_name) {
            this.id = b_id;
            this.name = b_name;
            this.genre = "";
            this.author = "";
            this.year_of_issue = 0;
            this.link = "";
        }

        public BookBuilder setGenre (String val) {
            this.genre = val;
            return this;
        }

        public BookBuilder setAuthor(String val) {
            this.author = val;
            return this;
        }

        public BookBuilder setYearOfIssue(Integer val) {
            this.year_of_issue = val;
            return this;
        }

        public BookBuilder setLink(String val) {
            this.link = val;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    public boolean matches (HashMap query) {
        if (!this.id.equals(query.get("id")) && query.get("id") != null)  return false;
        if (!this.name.equals(query.get("name")) && query.get("name") != null) return false;
        if (!this.genre.equals(query.get("genre")) && query.get("genre") != null) return false;
        if (!this.author.equals(query.get("author")) && query.get("author") != null) return false;
        if (!this.year_of_issue.equals(Integer.parseInt((String) query.get("year_of_issue"))) && query.get("year_of_issue") != null) return false;
        if (!this.link.equals(query.get("link")) && query.get("link") != null ) return false;
        return true;
    }

    public static class compareById implements Comparator<Book> {
        @Override
        public int compare(Book b1, Book b2) {
            return b1.id - b2.id;
        }
    }

    public static class compareByName implements Comparator<Book> {
        @Override
        public int compare(Book b1, Book b2) {
            return b1.name.compareToIgnoreCase(b2.name);
        }
    }

    public static class compareByAuthor implements Comparator<Book> {
        @Override
        public int compare(Book b1, Book b2) {
            return b1.author.compareToIgnoreCase(b2.author);
        }
    }

    public static class compareByYear implements Comparator<Book> {
        @Override
        public int compare(Book b1, Book b2) {
            return b1.year_of_issue - b2.year_of_issue;
        }
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
        result.append(this.getLink());
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
        if (!year_of_issue.equals(book.year_of_issue)) return false;
        return link.equals(book.link);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + genre.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + year_of_issue.hashCode();
        result = 31 * result + link.hashCode();
        return result;
    }
}
