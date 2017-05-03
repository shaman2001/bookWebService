package com.epam.rest.entity;

import java.util.*;
import static com.epam.rest.constants.CommonConstants.*;

public class BookShelf {

    private final static String FTP_BASE_ADDRESS = "ftp://127.0.0.1:25/pub/";
    private static TreeSet<Book> bookList = new TreeSet<>(new Book.compareById());

    static {
        bookList.add(new Book.BookBuilder(1, "Burning daylight").
                setGenre("novel").setAuthor("Jack London").setYearOfIssue(1990)
                .setLink(FTP_BASE_ADDRESS + "burning_daylight.txt").build());
        bookList.add(new Book.BookBuilder(2, "For whom the bell tolls").
                setGenre("novel").setAuthor("Ernest Hemingway").setYearOfIssue(2003)
                .setLink(FTP_BASE_ADDRESS + "for_whom_the_bell_tolls.txt").build());
        bookList.add(new Book.BookBuilder(3, "The Master and Margarita").
                setGenre("novel").setAuthor("Mikhail Bulgakov").setYearOfIssue(2003)
                .setLink(FTP_BASE_ADDRESS + "the_master_and_margarita.txt").build());
        bookList.add(new Book.BookBuilder(4, "Borodino").
                setGenre("poem").setAuthor("Mikhail Lermontov").setYearOfIssue(1960)
                .setLink(FTP_BASE_ADDRESS + "borodino.txt").build());
        bookList.add(new Book.BookBuilder(5, "Morals").
                setGenre("esseys").setAuthor("Plutarch").setYearOfIssue(1531)
                .setLink(FTP_BASE_ADDRESS + "morals.txt").build());

    }

    private static boolean isBookExists(Integer id) {
        if (getBooksCount() == 0) return false;
        for (Book book: bookList) {
            if (book.getId().equals(id)) return true;
        }
        return false;
    }

    public static Integer getBooksCount() {
        return bookList.size();
    }

    public static ArrayList<Book> getBook(HashMap query) {
        if (query.size() == 0) {
            return new ArrayList<>(bookList);
        } else if (query.get(PARAM_ID)!= null) {
            Integer id = Integer.parseInt((String)query.get(PARAM_ID));
            for(Book book: bookList) {
                if (book.getId().equals(id)) {
                    return new ArrayList<>(Collections.singletonList(book));
                }
            }
        } else {
            ArrayList<Book> result = new ArrayList<>();
            for (Book book: bookList) {
                if (book.matches(query)) {
                    result.add(book);
                }
            }
            return result.size()!=0 ? result : null;
        }
        return null;

    }

    public synchronized static boolean addBook(Book book) {
        return !isBookExists(book.getId()) && bookList.add(book);
    }

    public synchronized static boolean delBook(Integer id)  {
        for(Book book: bookList) {
            if (book.getId().equals(id)) {
                return bookList.remove(book);
            }
        }
        return false;
    }

    //newBookData should contain an id of updating book
    public synchronized static boolean updateBookData(Book newBookData) {
        if (newBookData.getId() > 0) {
            for(Book book: bookList) {
                if (book.getId().equals(newBookData.getId())) {
                    book.setName(newBookData.getName());
                    book.setAuthor(newBookData.getAuthor());
                    book.setGenre(newBookData.getGenre());
                    book.setYearOfIssue(newBookData.getYearOfIssue());
                    book.setLocalLink(newBookData.getLink());
                    return true;
                }
            }
        }
        return false;
    }


}
