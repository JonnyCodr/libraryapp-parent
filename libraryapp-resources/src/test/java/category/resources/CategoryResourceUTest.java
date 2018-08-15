package category.resources;

import category.models.Category;
import category.services.CategoryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static commontests.category.CategoryForTestsRepository.categoryWithId;
import static commontests.category.CategoryForTestsRepository.java;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryResourceUTest {

    private CategoryResource categoryResource;

    @Mock
    private CategoryService categoryService;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryResource = new CategoryResource();
        categoryResource.categoryService = categoryService;
    }

    @Test
    public void addValidCategory() {
        when(categoryService.add(java())).thenReturn(categoryWithId(java(), 1L));

//        Category response = categoryService.add("readfile");

    }
}