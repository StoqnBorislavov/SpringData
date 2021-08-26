package com.softuni.springintroexercise.services.impl;

import com.softuni.springintroexercise.constants.GlobalConstants;
import com.softuni.springintroexercise.entities.Author;
import com.softuni.springintroexercise.repositories.AuthorsRepository;
import com.softuni.springintroexercise.services.AuthorService;
import com.softuni.springintroexercise.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthorsServiceImpl implements AuthorService {

    private final AuthorsRepository authorsRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorsServiceImpl(AuthorsRepository authorsRepository, FileUtil fileUtil) {
        this.authorsRepository = authorsRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if(authorsRepository.count() != 0){
            return;
        }
        String[] fileContent = this.fileUtil.readFileContent(GlobalConstants.AUTHORS_FILE_PATH);
        Arrays.stream(fileContent).forEach(r ->{
                String[] params = r.split("\\s+");
                Author author = new Author(params[0], params[1]);
                this.authorsRepository.saveAndFlush(author);
                }
        );

    }

    @Override
    public int getAllAuthorsCount() {
        return (int) this.authorsRepository.count();
    }

    @Override
    public Author findAuthorById(Long id) {
        return this.authorsRepository.getById(id);
    }

    @Override
    public List<Author> findAllAuthorsByCountOfBooks() {
        return this.authorsRepository.findAuthorByCountOfBooks();
    }

    @Override
    public List<Author> findAllAuthorsWithAtLeastOneBookReleasedBefore1990() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate localDate = LocalDate.parse("01/01/1990", dateTimeFormatter);
        List<Author> all = this.authorsRepository.findAll();
        List<Author> result = new ArrayList<>();
        for (Author author : all) {
            boolean b = author.getBooks().stream().anyMatch(book -> book.getReleaseDate().isBefore(localDate));
            if(b){
                result.add(author);
            }
        }
        return result;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return this.authorsRepository.getAuthorByFirstNameAndLastName(firstName, lastName);
    }
}
