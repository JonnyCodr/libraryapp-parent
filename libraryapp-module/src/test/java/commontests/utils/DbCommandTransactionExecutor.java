package commontests.utils;

import org.junit.Ignore;

import javax.persistence.EntityManager;

import static commontests.category.CategoryForTestsRepository.java;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Ignore
public class DbCommandTransactionExecutor {

    private EntityManager em;

    public DbCommandTransactionExecutor(EntityManager em) {
        this.em = em;
    }

    public <T> T executeCommand(DbCommand<T> dbCommand) {

        try {
            em.getTransaction().begin();
            T toReturn = dbCommand.execute();
            em.getTransaction().commit();
            em.clear();
            return toReturn;
        } catch (final Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new IllegalStateException(e);
        }
    }
}
