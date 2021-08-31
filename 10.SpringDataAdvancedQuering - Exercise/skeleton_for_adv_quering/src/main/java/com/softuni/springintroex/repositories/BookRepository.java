package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.AgeRestriction;
import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.entities.EditionType;
import com.softuni.springintroex.services.models.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.basic.BasicIconFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Set<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Set<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    //@Query("SELECT b FROM Book b WHERE b.price NOT BETWEEN 5 AND 40")
    Set<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerBound, BigDecimal upperBound);

    @Query("SELECT b FROM Book b WHERE substring(b.releaseDate, 0, 4) NOT LIKE :year")
    Set<Book> findAllByReleaseDateNotInYear(String year);

    Set<Book> findAllByReleaseDateLessThan(LocalDate localDate);

    Set<Book> findAllByAuthorLastNameStartingWith(String start);

    @Query("SELECT count(b) FROM Book b WHERE length(b.title) > :length")
    int getNumberOffBooksWithTitleLength(int length);

    Book findByTitle(String title);

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.copies = b.copies + :copies WHERE b.releaseDate > :date")
    int updateCopies(int copies, LocalDate date);

    Set<Book> findAllByTitleContains(String contains);


}
