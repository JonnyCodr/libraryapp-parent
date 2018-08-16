package category.services;

import category.models.Category;
import category.exceptions.CategoryAlreadyExistsException;
import category.exceptions.CategoryNotFoundException;
import category.exceptions.FieldNotValidException;

import java.util.List;

public interface CategoryService {

    Category add(Category category) throws FieldNotValidException, CategoryAlreadyExistsException;

    void update(Category category) throws FieldNotValidException, CategoryNotFoundException;

    Category findById(final Long id) throws CategoryNotFoundException;

    List<Category> findAll();

}
