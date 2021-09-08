package springdatajsonprocesing.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdatajsonprocesing.domain.dtos.CategorySeedDto;
import springdatajsonprocesing.domain.dtos.ProductSeedDto;
import springdatajsonprocesing.domain.entities.Category;
import springdatajsonprocesing.domain.entities.Product;
import springdatajsonprocesing.domain.repositories.CategoryRepository;
import springdatajsonprocesing.utils.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_PATH = "src/main/resources/json/categories.json";
    private final CategoryRepository productRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository productRepository, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
    }


    @Override
    public String seedCategories() throws IOException {
        String content = String.join("", Files.readAllLines(Path.of(CATEGORIES_PATH)));

        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(content, CategorySeedDto[].class);

        StringBuilder sb = new StringBuilder();

        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if(this.validatorUtil.isValid(categorySeedDto)){
                Category category = this.modelMapper.map(categorySeedDto, Category.class);
                this.productRepository.saveAndFlush(category);
                sb.append(String.format("Correctly added category with name %s%n",category.getName()));
            }else {
                this.validatorUtil.violations(categorySeedDto)
                        .forEach(v -> sb.append(v.getMessage()).append(System.lineSeparator()));
            }
        }
        return sb.toString();
    }
}
