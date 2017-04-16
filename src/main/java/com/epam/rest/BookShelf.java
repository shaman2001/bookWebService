package com.epam.rest;

import com.epam.rest.entity.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class BookShelf {

    private final static String FTP_BASE_ADDRESS = "ftp://127.0.0.1:25/pub/";
    private TreeSet<Book> bookList;

    public BookShelf() {
        bookList = new TreeSet<>();
        bookList.add(new Book.BookBuilder(1, "Burning daylight").
                setGenre("novel").setAuthor("Jack London").setYearOfIssue(1990)
                .setLocalLink(FTP_BASE_ADDRESS + "burning_daylight.txt").build());
        bookList.add(new Book.BookBuilder(2, "For whom the bell tolls").
                setGenre("novel").setAuthor("Ernest Hemingway").setYearOfIssue(2003)
                .setLocalLink(FTP_BASE_ADDRESS + "for_whom_the_bell_tolls.txt").build());
        bookList.add(new Book.BookBuilder(3, "The Master and Margarita").
                setGenre("novel").setAuthor("Mikhail Bulgakov").setYearOfIssue(1995)
                .setLocalLink(FTP_BASE_ADDRESS + "the_master_and_margarita.txt").build());
        bookList.add(new Book.BookBuilder(4, "Borodino").
                setGenre("poem").setAuthor("Mikhail Lermontov").setYearOfIssue(1960)
                .setLocalLink(FTP_BASE_ADDRESS + "borodino.txt").build());
        bookList.add(new Book.BookBuilder(4, "Morals").
                setGenre("esseys").setAuthor("Plutarch").setYearOfIssue(1531)
                .setLocalLink(FTP_BASE_ADDRESS + "morals.txt").build());

    }

    public boolean loadBooksFromJson() {
        return true;
    }

    public ArrayList<Book> getBook() {
        return new ArrayList<>(bookList);
    }

    public ArrayList<Book> getBook(Integer id) {
        for(Book book: bookList) {
            if (book.getId().equals(id)) {
                return new ArrayList<>(Collections.singletonList(book));
            }
        }
        return null;
    }

    public ArrayList<Book> getBook(Book prm) {
        if (prm.getId()!=0) {
            for(Book book: bookList) {
                if (book.getId().equals(prm.getId())) {
                    return new ArrayList<>(Collections.singletonList(book));
                }
            }
        }
        ArrayList<Book> result = new ArrayList<>();
        for(Book book: bookList) {
            if ((!prm.getName().equals("") && book.getName().equals(prm.getName()))
                    || (!prm.getGenre().equals("") && book.getGenre().equals(prm.getGenre()))
                    || (!prm.getAuthor().equals("") && book.getAuthor().equals(prm.getAuthor()))
                    || (prm.getYearOfIssue()!= 0 && book.getYearOfIssue().equals(prm.getYearOfIssue()))
                    || (!prm.getLocalLink().equals("") && book.getLocalLink().equals(prm.getLocalLink()))) {
                result.add(book);
            }

        }
        return result.size()!=0 ? result : null;
    }

    public boolean addBook(Book book) {
        return bookList.add(book);
    }

    public boolean delBook(Book book) {
        return bookList.remove(book);
    }

    public boolean updateBookData(Book newBookData) {
        for(Book book: bookList) {
            if (book.getId().equals(newBookData.getId())) {
                 book.setName(newBookData.getName());
                 book.setAuthor(newBookData.getAuthor());
                 book.setGenre(newBookData.getGenre());
                 book.setYearOfIssue(newBookData.getYearOfIssue());
                 book.setLocalLink(newBookData.getLocalLink());
                 return true;
            }
        }
        return false;
    }


}
