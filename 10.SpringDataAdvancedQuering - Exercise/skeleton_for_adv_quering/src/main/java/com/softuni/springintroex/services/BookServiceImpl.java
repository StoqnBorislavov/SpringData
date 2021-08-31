package com.softuni.springintroex.services;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.*;
import com.softuni.springintroex.repositories.BookRepository;
import com.softuni.springintroex.services.models.BookInfo;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.fileUtil = fileUtil;
    }


    @Override
    @Transactional
    public void seedBooksInDB() throws IOException {
        String[] lines = this.fileUtil.readFileContent(GlobalConstants.BOOKS_FILE_PATH);

        Arrays.stream(lines)
                .forEach(r -> {
                    String[] params = r.split("\\s+");
                    Author author = this.getRandomAuthor();

                    EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];

                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate releaseDate = LocalDate.parse(params[1], dateTimeFormatter);

                    int copies = Integer.parseInt(params[2]);

                    BigDecimal price = new BigDecimal(params[3]);

                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];

                    String title = this.getTitle(params);

                    Set<Category> categories = this.getRandomCategories();


                    Book book = new Book();
                    book.setAuthor(author);
                    book.setEditionType(editionType);
                    book.setReleaseDate(releaseDate);
                    book.setCopies(copies);
                    book.setPrice(price);
                    book.setAgeRestriction(ageRestriction);
                    book.setTitle(title);
                    book.setCategories(categories);
                    this.bookRepository.saveAndFlush(book);
                });
    }

    @Override
    public void printAllBooksByAgeRestriction(String ageRest) {
        this.bookRepository.findAllByAgeRestriction(AgeRestriction.valueOf(ageRest.toUpperCase(Locale.ROOT)))
                .forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public void printAllBooksByEditionTypeAndCopies() {
        this.bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public void printAllBooksByPriceInBounds() {
        this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));
    }

    @Override
    public void printAllBooksByNotInYear(String year) {
        this.bookRepository.findAllByReleaseDateNotInYear(year)
                .forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public void printAllBooksWithReleaseDateLessThan(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, dtf);
        this.bookRepository.findAllByReleaseDateLessThan(localDate)
                .forEach(b -> System.out.printf("%s %s %.2f%n", b.getTitle(), b.getEditionType(), b.getPrice()));
    }

    @Override
    public void printAllBooksWithAuthorLastNameStartingWith(String start) {
        this.bookRepository.findAllByAuthorLastNameStartingWith(start)
                .forEach(b -> System.out.printf("%s (%s %s)%n", b.getTitle(), b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()));
    }

    @Override
    public void printBooksCountWithTittleNameLengthBiggerThan(int length) {
        System.out.println(this.bookRepository.getNumberOffBooksWithTitleLength(length));
    }

    @Override
    public BookInfo findBookByTitle(String title) {
        Book book = this.bookRepository.findByTitle(title);
        BookInfo bookInfo = new BookInfo(book.getTitle(), book.getPrice(), book.getCopies());
        return bookInfo;
    }

    @Override
    public void printUpdatedCopiesCount(String date, int copies) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("06-06-2013", dtf);
        int updateCopies = this.bookRepository.updateCopies(copies, localDate);
        System.out.println(updateCopies * copies);

    }

    @Override
    public void printAllBooksWithContainingStringInTitle(String text) {
        this.bookRepository.findAllByTitleContains(text)
                .forEach(b -> System.out.println(b.getTitle()));
    }


    private Set<Category> getRandomCategories() {
        Random random = new Random();
        int bound = random.nextInt(3) + 1;
        Set<Category> result = new HashSet<>();
        for (int i = 1; i <= bound; i++) {
            int categoryId = random.nextInt(8) + 1;
            result.add(this.categoryService
                    .getCategoryById((long) categoryId));
        }
        return result;
    }

    private String getTitle(String[] params) {
        StringBuilder builder = new StringBuilder();
        for (int i = 5; i < params.length; i++) {
            builder.append(params[i]).append(" ");
        }
        return builder.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt(this.authorService.getAllAuthorsCount()) + 1;
        return this.authorService.findAuthorById((long) randomId);
    }
}


