package com.softuni.springintroexercise.controllers;

import com.softuni.springintroexercise.entities.Book;
import com.softuni.springintroexercise.services.AuthorService;
import com.softuni.springintroexercise.services.BookService;
import com.softuni.springintroexercise.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
        //seed data
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();

        //Ex 1
        List<Book> books = this.bookService.getAllBooksAfter2000();

        //Ex 3
        this.authorService.findAllAuthorsByCountOfBooks()
                .forEach(a ->{
                    System.out.printf("%s %s %d%n", a.getFirstName(),
                            a.getLastName(), a.getBooks().size());
                });
        //Ex 2
        this.authorService.findAllAuthorsWithAtLeastOneBookReleasedBefore1990()
                .forEach(a ->{
                    System.out.printf("%s %s%n", a.getFirstName(),
                            a.getLastName());
                });

        //Ex 4
        this.authorService.findAuthorByName("George", "Powell").getBooks()
                .stream()
                .sorted(Comparator.comparing(Book::getReleaseDate).reversed().thenComparing(Book::getTitle))
                .forEach(book -> {
                    System.out.printf("%s %s %d%n", book.getTitle(), book.getReleaseDate(), book.getCopies());
                });
    }
}
