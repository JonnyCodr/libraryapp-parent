package category.services;

import category.models.Category;
import exceptions.FieldNotValidException;

public interface CategoryService {

    Category add(Category category) throws FieldNotValidException;
}
