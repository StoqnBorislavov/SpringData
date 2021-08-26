package com.softuni.springintroexercise.repositories;

import com.softuni.springintroexercise.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author AS a ORDER BY a.books.size DESC")
    List<Author> findAuthorByCountOfBooks();

    Author getAuthorByFirstNameAndLastName(String firstName, String lastName);
}
