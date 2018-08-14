package commontests.category;

import category.models.Category;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.List;

@Ignore
public class CategoryForTestsRepository {

    public static Category java() {
        return new Category("Java");
    }
    public static Category machinelearning() {
        return new Category("Machine Learning");
    }
    public static Category scala() {
        return new Category("Scala");
    }
    public static Category kafka() {
        return new Category("Kafka");
    }
    public static Category security() {
        return new Category("security");
    }
    public static Category spring() {
        return new Category("Spring");
    }

    public static Category cleancode() {
        return new Category("Clean Code");
    }

    public static Category networks() {
        return new Category("networks");
    }

    public static List<Category> allCategories() {
        return Arrays.asList(
                java(),
                machinelearning(),
                scala(),
                kafka(),
                security(),
                spring(),
                cleancode()
        );
    }
}
