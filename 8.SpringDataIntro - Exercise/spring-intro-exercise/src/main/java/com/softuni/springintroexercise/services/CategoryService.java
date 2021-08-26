package com.softuni.springintroexercise.services;

import com.softuni.springintroexercise.entities.Category;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;
    Category getCategoryById(Long id);
}
