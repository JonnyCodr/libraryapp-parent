package category.services;

import category.models.Category;
import category.repositories.CategoryRepository;
import category.services.impl.CategoryServiceImpl;
import exceptions.CategoryAlreadyExistsException;
import exceptions.CategoryNotFoundException;
import exceptions.FieldNotValidException;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static commontests.category.CategoryForTestsRepository.categoryWithId;
import static commontests.category.CategoryForTestsRepository.java;
import static commontests.category.CategoryForTestsRepository.kafka;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceUTest {

    private CategoryService categoryService;
    private Validator validator;
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        categoryRepository = mock(CategoryRepository.class);

        categoryService = new CategoryServiceImpl();
        ((CategoryServiceImpl) categoryService).validator = validator;
        ((CategoryServiceImpl) categoryService).categoryRepository = categoryRepository;
    }

    @Test
    public void addCategoryWithNullName() {
        addCategoryWithInvalidName(null);
    }

    @Test
    public void addCategoryWithShortName() {
        addCategoryWithInvalidName("A");
    }

    @Test
    public void addCategoryWithLongName() {
        addCategoryWithInvalidName("This is a really, really, really long name that will throw an error...");
    }

    @Test(expected = CategoryAlreadyExistsException.class)
    public void addCategoryWithExistentName() {

        //given
        when(categoryRepository.alreadyExists(java())).thenReturn(true);

        //when
        categoryService.add(java());

        //then

    }

    @Test
    public void addValidCategory() {

        //given
        when(categoryRepository.alreadyExists(java())).thenReturn(false);
        when(categoryRepository.add(java())).thenReturn(categoryWithId(java(), 1L));

        //when
        Category categoryAdded = categoryService.add(java());

        //then
        assertThat(categoryAdded.getId(), is(equalTo(1L)));
    }



    @Test
    public void updateCategoryWithNullName() {
        updateCategoryWithInvalidName(null);
    }

    @Test
    public void updateCategoryWithShortName() {
        updateCategoryWithInvalidName("A");
    }

    @Test
    public void updateCategoryWithLongName() {
        updateCategoryWithInvalidName("This is a really, really, really long name that will throw an error...");
    }

    @Test(expected = CategoryAlreadyExistsException.class)
    public void updateCategoryWithExistentName() {

        //given
        when(categoryRepository.alreadyExists(categoryWithId(java(), 1L))).thenReturn(true);

        //when
        categoryService.update(categoryWithId(java(), 1L));

    }

    @Test(expected = CategoryNotFoundException.class)
    public void updateCategoryNotFound() {

        //given
        when(categoryRepository.alreadyExists(categoryWithId(java(), 1L))).thenReturn(false);
        when(categoryRepository.existsById(1L)).thenReturn(false);

        //when
        categoryService.update(categoryWithId(java(), 1L));
    }

    @Test
    public void updateValidCategory() {
        //given
        when(categoryRepository.alreadyExists(categoryWithId(java(), 1L))).thenReturn(false);
        when(categoryRepository.existsById(1L)).thenReturn(true);

        //when
        categoryService.update(categoryWithId(java(), 1L));

        //then
        verify(categoryRepository).update(categoryWithId(java(), 1L));


    }

    @Test
    public void findCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(categoryWithId(java(), 1L));

        final Category category = categoryService.findById(1L);
        assertThat(category, is(notNullValue()));
        assertThat(category.getId(), is(equalTo(1L)));
        assertThat(category.getName(), is(equalTo(java().getName())));
    }

    @Test(expected = CategoryNotFoundException.class)
    public void findCategoryByIdNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(null);

        categoryService.findById(1L);
    }

    @Test
    public void findAllCategories() {
        when(categoryRepository.findAll("name")).thenReturn(Arrays.asList(
            categoryWithId(java(), 1L),
            categoryWithId(kafka(), 2L)
        ));

        final List<Category> categories = categoryService.findAll();
        assertThat(categories.size(), is(equalTo(2)));
        assertThat(categories.get(0).getName(), is(equalTo(java().getName())));
        assertThat(categories.get(1).getName(), is(equalTo(kafka().getName())));
    }

    @Test
    public void findAllNoCategories() {
        when(categoryRepository.findAll("name")).thenReturn(new ArrayList<>());

        List<Category> categories = categoryService.findAll();
        assertThat(categories.isEmpty(), is(equalTo(true)));
    }

    private void addCategoryWithInvalidName(final String name) {
        try {
            categoryService.add(new Category(name));
            fail("An Error should be thrown");
        } catch (FieldNotValidException e) {
            assertThat(e.getFieldName(), is(equalTo("name")));
        }
    }

    private void updateCategoryWithInvalidName(final String name) {
        try {
            categoryService.update(new Category(name));
            fail("An Error should be thrown");
        } catch (FieldNotValidException e) {
            assertThat(e.getFieldName(), is(equalTo("name")));
        }
    }
}
