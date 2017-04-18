package com.epam.rest;

import com.epam.rest.entity.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

public class BookShelf {

    private final static String FTP_BASE_ADDRESS = "ftp://127.0.0.1:25/pub/";
    private static HashSet<Book> bookList = new HashSet<>();

    static {
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

        /*bookList.add(new Book(1, "Burning daylight", "novel", "Jack London",
                            1990, FTP_BASE_ADDRESS + "burning_daylight.txt"));
        bookList.add(new Book(2, "For whom the bell tolls", "novel", "Ernest Hemingway",
                            2003, FTP_BASE_ADDRESS + "for_whom_the_bell_tolls.txt"));
        bookList.add(new Book(3, "The Master and Margarita", "novel", "Mikhail Bulgakov",
                            1995, FTP_BASE_ADDRESS + "the_master_and_margarita.txt"));*/

    }

    public static boolean loadBooksFromJson() {
        return true;
    }

    public static ArrayList<Book> getBook() {
        return new ArrayList<>(bookList);
    }

    public static ArrayList<Book> getBook(Integer id) {
        for(Book book: bookList) {
            if (book.getId().equals(id)) {
                return new ArrayList<>(Collections.singletonList(book));
            }
        }
        return null;
    }

    public static ArrayList<Book> getBook(Book prms) {
        if (prms.getId()!=0) {
            for(Book book: bookList) {
                if (book.getId().equals(prms.getId())) {
                    return new ArrayList<>(Collections.singletonList(book));
                }
            }
        }
        ArrayList<Book> result = new ArrayList<>();
        for(Book book: bookList) {
            if ((!prms.getName().equals("") && book.getName().equals(prms.getName()))
                    || (!prms.getGenre().equals("") && book.getGenre().equals(prms.getGenre()))
                    || (!prms.getAuthor().equals("") && book.getAuthor().equals(prms.getAuthor()))
                    || (prms.getYearOfIssue()!= 0 && book.getYearOfIssue().equals(prms.getYearOfIssue()))
                    || (!prms.getLocalLink().equals("") && book.getLocalLink().equals(prms.getLocalLink()))) {
                result.add(book);
            }

        }
        return result.size()!=0 ? result : null;
    }

    public static boolean addBook(Book book) {
        if (book.getId() > 0) {
            return bookList.add(book);
        }
        return false;
    }

    public static boolean delBook(Integer id) {
        for(Book book: bookList) {
            if (book.getId().equals(id)) {
                return bookList.remove(book);
            }
        }
        return false;
    }

    //newBookData should contain an id of updating book
    public static boolean updateBookData(Book newBookData) {
        if (newBookData.getId() > 0) {
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
        }
        return false;
    }


}
