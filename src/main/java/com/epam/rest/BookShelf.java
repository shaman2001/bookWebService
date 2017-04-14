package com.epam.rest;

import com.epam.rest.entity.Book;

import java.util.TreeSet;
import java.util.Set;

public class BookShelf {

    private Set<Book> bookList;

    public BookShelf() {
        bookList = new TreeSet<>();
        bookList.add(new Book())
    }

    public boolean loadBooksFromJson() {

    }

    public Book getBookById(Integer id){
        for(Book book: bookList) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public boolean addBook(Book book) {
        return bookList.add(book);
    }

    public boolean delBook(Book book) {
        return bookList.remove(book);
    }

    public boolean updateBookData(Integer id, Book newBookData ) {

    }
}
