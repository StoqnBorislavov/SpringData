package com.softuni.springintroex.services;


import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.Category;
import com.softuni.springintroex.repositories.CategoryRepository;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CategoriesServiceImpl implements CategoryService{

    private final FileUtil fileUtil;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoriesServiceImpl(FileUtil fileUtil, CategoryRepository categoryRepository) {
        this.fileUtil = fileUtil;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void seedCategoriesInDB() throws IOException {

        String[] lines = fileUtil.readFileContent(GlobalConstants.CATEGORIES_FILE_PATH);

        for (String line : lines) {
            Category category = new Category(line);

            this.categoryRepository.saveAndFlush(category);
        }
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.getById(id);
    }
}
