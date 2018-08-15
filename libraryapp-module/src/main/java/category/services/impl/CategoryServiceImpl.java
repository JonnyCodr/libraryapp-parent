package category.services.impl;

import category.models.Category;
import category.repositories.CategoryRepository;
import category.services.CategoryService;
import exceptions.CategoryAlreadyExistsException;
import exceptions.CategoryNotFoundException;
import exceptions.FieldNotValidException;


import javax.validation.Validator;
import javax.validation.ConstraintViolation;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    public Validator validator;
    public CategoryRepository categoryRepository;

    @Override
    public Category add(Category category) throws FieldNotValidException {

        validateCategory(category);
        return categoryRepository.add(category);
    }

    @Override
    public void update(final Category category) throws FieldNotValidException {
        validateCategory(category);

        if (!categoryRepository.existsById(category.getId())) {
            throw new CategoryNotFoundException();
        }

        categoryRepository.update(category);
    }

    @Override
    public Category findById(Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(id);
        if (category == null) throw new CategoryNotFoundException();
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll("name");
    }

    private void validateCategory(Category category) {
        validateCategoryFields(category);

        if (categoryRepository.alreadyExists(category)) {
            throw new CategoryAlreadyExistsException();
        }
    }

    private void validateCategoryFields(Category category) {
        final Set<ConstraintViolation<Category>> errors = validator.validate(category);
        final Iterator<ConstraintViolation<Category>> itErrors = errors.iterator();
        if (itErrors.hasNext()) {
            ConstraintViolation<Category> violation = itErrors.next();
            throw new FieldNotValidException(violation.getPropertyPath().toString(), violation.getMessage());
        }
    }
}
