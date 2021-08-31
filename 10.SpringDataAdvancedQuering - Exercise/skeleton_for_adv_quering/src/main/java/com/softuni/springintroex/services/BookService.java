package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.services.models.BookInfo;

import java.io.IOException;

public interface BookService {

    void seedBooksInDB() throws IOException;

    void printAllBooksByAgeRestriction(String ageRest);

    void printAllBooksByEditionTypeAndCopies();

    void printAllBooksByPriceInBounds();

    void printAllBooksByNotInYear(String year);

    void printAllBooksWithReleaseDateLessThan(String date);

    void printAllBooksWithAuthorLastNameStartingWith(String start);

    void printBooksCountWithTittleNameLengthBiggerThan(int length);

    BookInfo findBookByTitle(String title);

    void printUpdatedCopiesCount(String date, int copies);

    void printAllBooksWithContainingStringInTitle(String text);
}
