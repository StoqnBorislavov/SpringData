package com.softuni.springintroexercise.services;

import com.softuni.springintroexercise.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;
    int getAllAuthorsCount();
    Author findAuthorById(Long id);
    List<Author> findAllAuthorsByCountOfBooks();
    List<Author> findAllAuthorsWithAtLeastOneBookReleasedBefore1990();
    Author findAuthorByName(String firstName, String lastName);
}
