package category.repositories;

import category.models.Category;
import commontests.utils.DbCommandTransactionExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static commontests.category.CategoryForTestsRepository.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryRepositoryUTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private CategoryRepository categoryRepository;
    private DbCommandTransactionExecutor commandTransactionExecutor;

    @Before
    public void setUp() throws Exception {

        // the "libraryPU" is defined in the /test/resources/META-INF/persistance.xml
        emf = Persistence.createEntityManagerFactory("libraryPU");
        em = emf.createEntityManager();

        categoryRepository = new CategoryRepository();
        categoryRepository.em = em;
        commandTransactionExecutor = new DbCommandTransactionExecutor(em);
    }

    @After
    public void tearDown() throws Exception {
        em.close();
        emf.close();
    }

    @Test
    public void addCategoryAndFindIt() {

        Long categoryAddedId = commandTransactionExecutor.executeCommand(() -> categoryRepository.add(java()).getId());
        assertThat(categoryAddedId, is(notNullValue()));

        Category category = categoryRepository.findById(categoryAddedId);
        assertThat(category, is(notNullValue()));
        assertThat(category.getName(), is(equalTo(java().getName())));
    }

    @Test
    public void findCategoryByIdNotFound() {
        final Category category = categoryRepository.findById(999L);
        assertThat(category, is(nullValue()));
    }

    @Test
    public void findCategoryByIdWithNullValue() {
        final Category category = categoryRepository.findById(null);
        assertThat(category, is(nullValue()));
    }

    @Test
    public void updateCategory() {
        Long categoryAddedId = commandTransactionExecutor.executeCommand(() -> categoryRepository.add(java()).getId());
        assertThat(categoryAddedId, is(notNullValue()));

        Category categoryAfterAdd = categoryRepository.findById(categoryAddedId);
        assertThat(categoryAfterAdd.getName(), is(java().getName()));

        categoryAfterAdd.setName(spring().getName());
        commandTransactionExecutor.executeCommand(() -> {
            categoryRepository.update(categoryAfterAdd);
            return null;
        });

        Category categoryAfterUpdate = categoryRepository.findById(categoryAddedId);
        assertThat(categoryAfterUpdate.getName(), is(spring().getName()));
    }

    @Test
    public void findAllCategories() {
        commandTransactionExecutor.executeCommand(() -> {
            allCategories().forEach(categoryRepository::add);
            return null;
        });
        List<Category> categories = categoryRepository.findAll("name");
        assertThat(categories.size(), is(equalTo(7)));
//        assertThat(categories.get(0).getName(), is(equalTo("Java")));
//        assertThat(categories.get(1).getName(), is(equalTo("Machine Learning")));
//        assertThat(categories.get(2).getName(), is(equalTo("Scala")));
//        assertThat(categories.get(3).getName(), is(equalTo("Kafka")));
//        assertThat(categories.get(4).getName(), is(equalTo("security")));
//        assertThat(categories.get(5).getName(), is(equalTo("Spring")));
    }

    @Test
    public void alreadyExistsForAdd() {
        commandTransactionExecutor.executeCommand(() -> categoryRepository.add(java()));

        assertThat(categoryRepository.alreadyExists(java()), is(equalTo(true)));
    }

    @Test
    public void alreadyExistsCategoryWithId() {
        final Category java = commandTransactionExecutor.executeCommand(() -> {
            categoryRepository.add(cleancode());
            return categoryRepository.add(java());
        });

        assertThat(categoryRepository.alreadyExists(java), is(equalTo(false)));

        java.setName(cleancode().getName());
        assertThat(categoryRepository.alreadyExists(java), is(equalTo(true)));

        java.setName(networks().getName());
        assertThat(categoryRepository.alreadyExists(java), is(equalTo(false)));
    }

    @Test
    public void existsById() {
        final Long categoryAddedId = commandTransactionExecutor.executeCommand(() -> {
            return categoryRepository.add(java()).getId();
        });

        assertThat(categoryRepository.existsById(categoryAddedId), is(equalTo(true)));
        assertThat(categoryRepository.existsById(999L), is(equalTo(false)));
    }

}
