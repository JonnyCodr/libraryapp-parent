package category.resources;

import category.models.Category;
import category.services.CategoryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import static commontests.category.CategoryForTestsRepository.categoryWithId;
import static commontests.category.CategoryForTestsRepository.java;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static utils.FileTestNameUtils.getPathFileRequest;
import static utils.JsonTestUtils.asserJsonMatchesExpectedJson;
import static utils.JsonTestUtils.readJsonFile;

public class CategoryResourceUTest {

    private CategoryResource categoryResource;
    private static final String PATH_RESOURCE = "categories";

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

        final Response response = categoryResource.add(readJsonFile(getPathFileRequest(PATH_RESOURCE, "newCategory.json")));

        assertThat(response.getStatus(), is(equalTo(201)));
        asserJsonMatchesExpectedJson(response.getEntity().toString(), "{\"id\": 1}");

    }
}