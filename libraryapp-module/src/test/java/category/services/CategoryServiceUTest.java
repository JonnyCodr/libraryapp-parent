package category.services;

import category.models.Category;
import category.services.impl.CategoryServiceImpl;
import exceptions.FieldNotValidException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CategoryServiceUTest {

    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        categoryService = new CategoryServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addCategoryWithNullName() {

        try {
            categoryService.add(new Category());
            fail("An Error should be thrown");
        } catch (FieldNotValidException e) {
            assertThat(e.getFieldName(), is(equalTo("name")));
        }
    }
}