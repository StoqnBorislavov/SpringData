package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.services.models.BookInfo;

import java.io.IOException;

public interface AuthorService {
    void seedAuthorsInDB() throws IOException;

    int getAllAuthorsCount();

    Author findAuthorById(Long id);

    void printAllAuthorsWithEndingString(String start);

    void printAllAuthorByBookCopies();


}
