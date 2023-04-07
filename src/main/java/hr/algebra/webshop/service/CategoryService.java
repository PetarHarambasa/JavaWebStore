package hr.algebra.webshop.service;

import hr.algebra.webshop.model.Category;
import hr.algebra.webshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryByIdCategory(Long id){
        return categoryRepository.findCategoryByIdCategory(id);
    }

}
