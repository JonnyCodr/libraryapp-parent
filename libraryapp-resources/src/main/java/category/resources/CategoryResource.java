package category.resources;

import category.models.Category;
import category.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class CategoryResource {
    private Logger log = LoggerFactory.getLogger(getClass());

    CategoryService categoryService;
    CategoryJsonConverter categoryJsonConverter;


    public Response add(String body) {
        log.info("Adding a new category with body {}", body);
        Category category = categoryJsonConverter.convertFrom(body);
        category = categoryService.add(category);

        return null;
    }
}
