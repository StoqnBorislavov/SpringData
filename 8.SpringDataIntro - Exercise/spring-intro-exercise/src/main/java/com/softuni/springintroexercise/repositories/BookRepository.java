package com.softuni.springintroexercise.repositories;

import com.softuni.springintroexercise.entities.Author;
import com.softuni.springintroexercise.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);

    Book findBookByReleaseDateBefore(LocalDate localDate);
}
