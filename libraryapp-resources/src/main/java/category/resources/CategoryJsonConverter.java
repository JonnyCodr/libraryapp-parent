package category.resources;

import category.models.Category;
import com.google.gson.JsonObject;

public class CategoryJsonConverter {

    public Category convertFrom(final String json) {

        final JsonObject jsonObject = JsonReader.readAsJsonObject(json);

        final Category category = new Category();
        category.setName(JsonReader.getStringOrNull(jsonObject, "name"));

        return category;
    }
}
