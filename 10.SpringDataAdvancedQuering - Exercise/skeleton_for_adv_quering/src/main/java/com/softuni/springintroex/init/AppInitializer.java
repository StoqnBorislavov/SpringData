package com.softuni.springintroex.init;

import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.services.models.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;


@Component
public class AppInitializer implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AppInitializer(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
//        this.categoryService.seedCategoriesInDB();
//        this.authorService.seedAuthorsInDB();
//        this.bookService.seedBooksInDB();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Ex 1
        //this.bookService.printAllBooksByAgeRestriction(reader.readLine());

        //Ex 2
        //this.bookService.printAllBooksByEditionTypeAndCopies();

        //Ex 3
        //this.bookService.printAllBooksByPriceInBounds();

        //Ex 4
        //this.bookService.printAllBooksByNotInYear(reader.readLine());

        //Ex 5
        //this.bookService.printAllBooksWithReleaseDateLessThan(reader.readLine());

        //Ex 6
        //this.authorService.printAllAuthorsWithEndingString(reader.readLine());

        //Ex 7
        //this.bookService.printAllBooksWithContainingStringInTitle(reader.readLine());

        //Ex 8
        //this.bookService.printAllBooksWithAuthorLastNameStartingWith(reader.readLine());

        //Ex 9
        //this.bookService.printBooksCountWithTittleNameLengthBiggerThan(Integer.parseInt(reader.readLine()));

        //Ex 10
        //this.authorService.printAllAuthorByBookCopies();

        //Ex 11
        //BookInfo bookByTitle = this.bookService.findBookByTitle(reader.readLine());
        //System.out.println(bookByTitle.getTitle());
        //System.out.println(bookByTitle.getPrice());
        //System.out.println(bookByTitle.getCopies());



    }
}
