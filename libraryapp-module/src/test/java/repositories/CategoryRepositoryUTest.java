package repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class CategoryRepositoryUTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {

        // the "libraryPU" is defined in the /test/resources/META-INF/persistance.xml
        emf = Persistence.createEntityManagerFactory("libraryPU");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        em.close();
        emf.close();
    }

    @Test
    public void addCategoryAndFindIt() {

        try {
            em.getTransaction().begin();


            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
}